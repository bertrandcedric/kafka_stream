package com.scala.test.serializer

import java.util

import org.apache.kafka.common.serialization.{Deserializer, StringDeserializer}
import play.api.libs.json.{JsNull, Json, Reads}

class JsonDeserializer[T: Reads] extends Deserializer[T] {

  private val stringDeserializer = new StringDeserializer

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit =
    stringDeserializer.configure(configs, isKey)

  override def deserialize(topic: String, data: Array[Byte]): T = {
    stringDeserializer.deserialize(topic, data) match {
      case null => {
        JsNull.as[T]
      }
      case value => {
        Json.parse(value).as[T]
      }
    }
  }

  override def close(): Unit =
    stringDeserializer.close()
}
