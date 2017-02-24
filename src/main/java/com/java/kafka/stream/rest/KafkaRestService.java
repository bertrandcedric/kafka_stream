package com.java.kafka.stream.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.kafka.stream.KafkaStream;
import com.java.kafka.stream.model.Referentiel;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class KafkaRestService extends ServerRest {

    private final KafkaStreams streams;
    private ObjectMapper objectMapper = new ObjectMapper();

    public KafkaRestService(final KafkaStreams streams) {
        this.streams = streams;
    }

    @GET
    @Path("/referentiels")
    @Produces(MediaType.APPLICATION_JSON)
    public String referentiels() throws JsonProcessingException {
        ReadOnlyKeyValueStore<String, Referentiel> referentiel_store = streams.store(KafkaStream.REFERENTIEL_STORE, QueryableStoreTypes.<String, Referentiel>keyValueStore());
        KeyValueIterator<String, Referentiel> all = referentiel_store.all();
        ArrayList<Referentiel> referentiels = new ArrayList<>();
        while (all.hasNext()) {
            referentiels.add(all.next().value);
        }
        return objectMapper.writeValueAsString(referentiels);
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() {
        return "Hello world";
    }
}
