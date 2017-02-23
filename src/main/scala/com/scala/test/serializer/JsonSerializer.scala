package com.scala.test.serializer

import java.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import org.apache.kafka.common.serialization.Serializer

class JsonSerializer[T] extends Serializer[T] {

  val objectMapper = new ObjectMapper() with ScalaObjectMapper

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {
  }

  override def serialize(topic: String, data: T): Array[Byte] = {
    objectMapper.writeValueAsBytes(data)
  }

  override def close(): Unit = {
  }
}
