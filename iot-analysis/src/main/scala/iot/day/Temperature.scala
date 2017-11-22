package iot.day

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

case class Temperature(deviceId: String, temperature: Double, timestamp: Long) {

  def toJson: String = Temperature.toJson(this)

}

object Temperature {

  private lazy val objectMapper = new ObjectMapper().registerModule(DefaultScalaModule)

  def fromJson(json: String): Temperature = objectMapper.readValue(json, classOf[Temperature])

  def toJson(event: Temperature): String = objectMapper.writeValueAsString(event)

}
