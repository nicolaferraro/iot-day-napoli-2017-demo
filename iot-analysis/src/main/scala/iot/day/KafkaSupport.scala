package iot.day

import kafka.admin.AdminUtils
import kafka.utils.ZkUtils
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

import scala.collection.JavaConversions._
import scala.util.Random

object KafkaSupport {

  val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "kafka:9092",
      "key.serializer" -> classOf[StringSerializer],
      "key.deserializer" -> classOf[StringDeserializer],
      "value.serializer" -> classOf[StringSerializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> ("spark-" + Random.nextLong()),
      // Use a random group id at each restart: this is a demo configuration
      // In production you may want to use a fixed group and checkpoints
      "auto.offset.reset" -> "earliest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

  def kafkaParamsWithServer(servers: String): Map[String, Object] = kafkaParams.updated("bootstrap.servers", servers)


  private lazy val producer = {
    val producer = new KafkaProducer[String, String](kafkaParams)
    sys.addShutdownHook {
      producer.close()
    }
    producer
  }

  def createTopicIfNotPresent(name: String): Unit = {
    // This is a demo, so it's ok to configure it here...
    val zkUtils = ZkUtils("zookeeper:2181", 10000, 10000, isZkSecurityEnabled = false)
    try {
      AdminUtils.createTopic(zkUtils, name, 1, 1)
    } catch {case e: Exception =>
        e.printStackTrace()
        // ignore
    }
  }

  def createJsonStream(topic: String)(implicit ssc: StreamingContext): DStream[String] = {
    KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](Seq(topic), KafkaSupport.kafkaParams)
    ).map(record => record.value())
  }

  def send(topic: String, key: String, value: String): Unit = producer.send(new ProducerRecord[String, String](topic, key, value))

}
