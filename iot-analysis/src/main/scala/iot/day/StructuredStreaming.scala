package iot.day

import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType

object StructuredStreaming {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder
          .appName("Structured Streaming")
          .getOrCreate()

    import spark.implicits._

    val rawTemp = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "kafka:9092")
      .option("subscribe", "temperature")
      .load()


    val schema = new StructType()
      .add($"deviceId".string)
      .add($"temperature".double)
      .add($"timestamp".long)

    /*
          TEMPERATURE TABLE

          | DEVICE      | TEMPERATURE       | TIMESTAMP       |
          -----------------------------------------------------
          | device-0    | 23.3              | 1511270667156   |
          | device-1    | 24.5              | 1511270667180   |
          | device-0    | 23.5              | 1511270667195   |
          -----------------------------------------------------
     */
    rawTemp.select($"value" cast "string" as "data")
      .select(from_json($"data", schema) as "data")
      .select($"data.deviceId" as "device", $"data.temperature" as "temperature", $"data.timestamp" as "timestamp")
      .createTempView("temperature")


    /*
          AVG_TEMPERATURE TABLE

          | DEVICE      | AVG_TEMPERATURE   |
          -----------------------------------
          | device-0    | 23.4              |
          | device-1    | 24.5              |
          -----------------------------------
     */
    spark.sql("SELECT device, avg(temperature) avg_temperature FROM temperature group by device")
      .createTempView("avg_temperature")


    val query = spark.sql("select * from avg_temperature")
      .writeStream
      .outputMode("complete")
      .format("console")
      .start()

    query.awaitTermination()
  }

}
