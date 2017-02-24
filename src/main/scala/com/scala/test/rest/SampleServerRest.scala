package com.scala.test.rest

import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp

object SampleServerRest extends App with SimpleRoutingApp {

  implicit val system = ActorSystem("kafka-rest")

  startServer(interface = "localhost", port = 7070) {
    path("hello").&(get.&(complete {
      "hello world"
    }))
  }
}
