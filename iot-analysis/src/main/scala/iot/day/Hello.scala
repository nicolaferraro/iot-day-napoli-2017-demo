package iot.day

import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object Hello {

  def main(args: Array[String]): Unit = {

    KafkaSupport.createTopicIfNotPresent("temperature")
    KafkaSupport.createTopicIfNotPresent("temperature.avg")

    val conf = new SparkConf()
      .setAppName("App2")

    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(5))

    val source = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](Seq("temperature"), KafkaSupport.kafkaParams)
    ).map(record => Temperature.fromJson(record.value()))


    source.window(Seconds(60))
      .foreachRDD(rdd => {
        val avg = rdd.map(t => t.temperature).mean()
        KafkaSupport.send("temperature.avg", "avg", Temperature("avg", avg).toJson)
      })

    ssc.start()
    ssc.awaitTermination()
  }

}
