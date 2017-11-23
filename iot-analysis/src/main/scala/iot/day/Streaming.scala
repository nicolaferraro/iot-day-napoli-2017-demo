package iot.day

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object Streaming {

  def main(args: Array[String]): Unit = {

    KafkaSupport.createTopicIfNotPresent("temperature")
    KafkaSupport.createTopicIfNotPresent("temperature.avg")
    KafkaSupport.createTopicIfNotPresent("temperature.favg")
    KafkaSupport.createTopicIfNotPresent("action")

    val conf = new SparkConf()
      .setAppName("Streaming")

    val sc = new SparkContext(conf)
    implicit val ssc: StreamingContext = new StreamingContext(sc, Seconds(5))

    val source = KafkaSupport.createJsonStream("temperature")
      .map(Temperature.fromJson)


    source.window(Seconds(60))
      .foreachRDD(measurements => {

        // Compute the average and send it periodically to the avg topic
        val avg = measurements.map(t => t.temperature).mean()
        KafkaSupport.send("temperature.avg", "avg", Temperature("avg", avg, System.currentTimeMillis()).toJson)

        // There's a device that behaves incorrectly, we should exclude it and send the result to favg
        val defective = measurements.groupBy(t => t.deviceId)
          .mapValues(temps => temps.map(t => t.temperature).sum / temps.size)
          .sortBy({case (device, temp) => -Math.abs(temp - avg)})
          .take(1)
          .map({case (device, temp) => device})
          .head

        val fixedAvg = measurements
          .filter(t => t.deviceId != defective)
          .map(t => t.temperature)
          .mean()

        KafkaSupport.send("temperature.favg", "favg", Temperature("favg", fixedAvg, System.currentTimeMillis()).toJson)

        // When the average temperature goes behind a threshold, we trigger a action to the action topic
        val action = fixedAvg < 10 && fixedAvg > 0
        KafkaSupport.send("action", "action", action.toString)

      })

    ssc.start()
    ssc.awaitTermination()
  }

}
