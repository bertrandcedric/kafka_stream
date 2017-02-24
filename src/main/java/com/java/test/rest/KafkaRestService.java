package com.java.test.rest;

import com.java.test.model.Referentiel;
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

import static com.java.test.KafkaStream.REFERENTIEL_STORE;

@Path("/")
public class KafkaRestService extends com.java.test.rest.ServerRest {

    private final KafkaStreams streams;

    public KafkaRestService(final KafkaStreams streams) {
        this.streams = streams;
    }

    @GET
    @Path("/referentiels")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Referentiel> referentiels() {
        ReadOnlyKeyValueStore<String, Referentiel> referentiel_store = streams.store(REFERENTIEL_STORE, QueryableStoreTypes.<String, Referentiel>keyValueStore());
        KeyValueIterator<String, Referentiel> all = referentiel_store.all();
        ArrayList<Referentiel> referentiels = new ArrayList<>();
        while (all.hasNext()) {
            referentiels.add(all.next().value);
        }
        return referentiels;
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() {
        return "Hello world";
    }
}
