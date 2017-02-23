package com.java.test;

import com.java.test.model.ProduitBrut;
import com.java.test.model.ProduitEnrichi;
import com.java.test.model.Referentiel;
import com.java.test.rest.KafkaRestService;
import com.java.test.serializer.JsonDeserializer;
import com.java.test.serializer.JsonSerializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaStream {

    public static final String REFERENTIEL_STORE = "referentiel_store";
    private static final String BOOTSTRAP_SERVER = "localhost:9092";
    private static final String TOPIC_ACHATS = "achats";
    private static final String TOPIC_REFERENTIEL = "referentiel";
    private static final String TOPIC_ACHATS_BY_PRODUCT_ID = "achats-by-product-id";
    private static final String TOPIC_ACHATS_ENRICHIS = "achats-enrichis";
    private static final String APPLICATION_ID = "enrichissement-achats";

    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaStream.class);

    public static void main(String[] args) throws Exception {

        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);

        KStreamBuilder builder = new KStreamBuilder();

        Serde<ProduitBrut> achatBrutSerde = Serdes.serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(ProduitBrut.class));
        Serde<ProduitEnrichi> achatEnrichiSerde = Serdes.serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(ProduitEnrichi.class));
        Serde<Referentiel> referentielSerde = Serdes.serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(Referentiel.class));

        KStream<String, ProduitBrut> achats = builder.stream(Serdes.String(), achatBrutSerde, TOPIC_ACHATS);

        KTable<String, Referentiel> referentiel = builder.table(Serdes.String(), referentielSerde, TOPIC_REFERENTIEL, REFERENTIEL_STORE);

        KStream<String, ProduitEnrichi> enriched = achats
                .filter((k, v) -> v != null)
                .map((k, v) -> new KeyValue<>(v.getId().toString(), v))
                .through(Serdes.String(), achatBrutSerde, TOPIC_ACHATS_BY_PRODUCT_ID)
                .leftJoin(referentiel, (achat, ref) -> {
                    if (ref == null) {
                        ProduitEnrichi produitEnrichi = new ProduitEnrichi(achat.getId(), "REF INCONNUE", achat.getPrice());
                        LOGGER.info(produitEnrichi.toString());
                        return produitEnrichi;
                    } else {
                        ProduitEnrichi produitEnrichi = new ProduitEnrichi(achat.getId(), ref.getName(), achat.getPrice());
                        LOGGER.info(produitEnrichi.toString());
                        return produitEnrichi;
                    }
                });

        enriched.to(Serdes.String(), achatEnrichiSerde, TOPIC_ACHATS_ENRICHIS);

        KafkaStreams streams = new KafkaStreams(builder, streamsConfiguration);
        streams.start();

        new KafkaRestService(streams).start();
    }
}
