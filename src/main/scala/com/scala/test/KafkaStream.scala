package com.scala.test

import java.util.Properties

import com.scala.test.model.{ProduitBrut, ProduitEnrichi, Referentiel}
import com.scala.test.serializer.{JsonDeserializer, JsonSerializer}
import org.apache.kafka.common.serialization.{Serde, Serdes}
import org.apache.kafka.streams.kstream._
import org.apache.kafka.streams.{KafkaStreams, KeyValue, StreamsConfig}
import org.slf4j.LoggerFactory

class KafkaStream {
}

object KafkaStream {

  val REFERENTIEL_STORE = "referentiel_store"
  val BOOTSTRAP_SERVER = "localhost:9092"
  val TOPIC_ACHATS = "achats"
  val TOPIC_REFERENTIEL = "referentiel"
  val TOPIC_ACHATS_BY_PRODUCT_ID: String = "achats-by-product-id"
  val TOPIC_ACHATS_ENRICHIS = "achats-enrichis"
  val APPLICATION_ID = "enrichissement-achats"

  val logger = LoggerFactory.getLogger(classOf[KafkaStream])

  def main(args: Array[String]) {

    val streamsConfiguration = new Properties()
    streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID)
    streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER)

    val builder = new KStreamBuilder()

    val achatBrutSerde: Serde[ProduitBrut] = Serdes.serdeFrom(new JsonSerializer(), new JsonDeserializer[ProduitBrut])
    val achatEnrichiSerde: Serde[ProduitEnrichi] = Serdes.serdeFrom(new JsonSerializer(), new JsonDeserializer[ProduitEnrichi])
    val referentielSerde: Serde[Referentiel] = Serdes.serdeFrom(new JsonSerializer(), new JsonDeserializer[Referentiel])

    val achats: KStream[String, ProduitBrut] = builder.stream(Serdes.String(), achatBrutSerde, TOPIC_ACHATS)

    val referentiel: KTable[String, Referentiel] = builder.table(Serdes.String(), referentielSerde, TOPIC_REFERENTIEL, REFERENTIEL_STORE)

    val enriched = achats
      .filter((k, v) => v != null)
      .map((k, v) => new KeyValue(v.id.toString, v))
      .through(Serdes.String(), achatBrutSerde, TOPIC_ACHATS_BY_PRODUCT_ID)
      .leftJoin(referentiel, (achat, ref: Referentiel) => {
        if (ref == null) {
          val produitEnrichi = ProduitEnrichi(achat.id, "REF INCONNUE", achat.price)
          logger.info(produitEnrichi.toString)
          produitEnrichi
        } else {
          val produitEnrichi = ProduitEnrichi(achat.id, ref.name, achat.price)
          logger.info(produitEnrichi.toString)
          produitEnrichi
        }
      })

    //    enriched.to(Serdes.String(), achatEnrichiSerde, TOPIC_ACHATS_ENRICHIS)

    val streams: KafkaStreams = new KafkaStreams(builder, streamsConfiguration)
    streams.start()

    //    new KafkaRestService(streams).start
  }
}

