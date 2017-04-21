package com.scala.test.rest

import akka.actor.ActorSystem
import com.scala.test.KafkaStream
import com.scala.test.model.Referentiel
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.state.QueryableStoreTypes
import play.api.libs.json.Json
import spray.routing.SimpleRoutingApp

import scala.collection.mutable.ListBuffer

class KafkaRestService(streams: KafkaStreams) extends SimpleRoutingApp {

  def start() {
    implicit val system = ActorSystem("kafka-rest")

    startServer(interface = "localhost", port = 7070) {
      path("hello") {
        get(complete("hello world"))
      } ~
        path("referentiels") {
          get(complete {
            val referentiel_store = streams.store(KafkaStream.REFERENTIEL_STORE, QueryableStoreTypes.keyValueStore[String, Referentiel])
            val all = referentiel_store.all()
            val referentiels = ListBuffer[Referentiel]()
            while (all.hasNext) {
              referentiels += all.next().value
            }
            Json.toJson(referentiels.toList).toString()
          })
        }
    }
  }
}
