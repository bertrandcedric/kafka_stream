package com.scala.test.rest

import javax.ws.rs.core.MediaType
import javax.ws.rs.{GET, Path, Produces}

import com.scala.test.KafkaStream
import com.scala.test.model.Referentiel
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.state.QueryableStoreTypes

import scala.collection.mutable.ListBuffer

@Path("/kafka")
class KafkaRestService(streams: KafkaStreams) extends ServerRest {

//  @GET
//  @Path("/referentiels")
//  @Produces(MediaType.APPLICATION_JSON)
//  def referentiels(): List[Referentiel] = {
//    val referentiel_store = streams.store(KafkaStream.REFERENTIEL_STORE, QueryableStoreTypes.keyValueStore[String, Referentiel])
//    val all = referentiel_store.all()
//    val referentiels = ListBuffer[Referentiel]()
//    while (all.hasNext) {
//      referentiels += all.next().value
//    }
//    referentiels.toList
//  }
//
//  @GET
//  @Path("/hello")
//  @Produces(MediaType.APPLICATION_JSON)
//  def hello(): String = {
//    "Hello world"
//  }
}
