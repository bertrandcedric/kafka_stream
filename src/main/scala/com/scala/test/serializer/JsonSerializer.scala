package com.scala.test.serializer

import java.util

import org.apache.kafka.common.serialization.{Serializer, StringSerializer}
import play.api.libs.json.{Json, Writes}

class JsonSerializer[T: Writes] extends Serializer[T] {
  private val stringSerializer = new StringSerializer

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit =
    stringSerializer.configure(configs, isKey)

  override def serialize(topic: String, data: T): Array[Byte] =
    stringSerializer.serialize(topic, Json.stringify(Json.toJson(data)))

  override def close(): Unit =
    stringSerializer.close()
}
