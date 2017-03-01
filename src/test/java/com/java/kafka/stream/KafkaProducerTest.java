package com.java.kafka.stream;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KafkaProducerTest {
    MockProducer<String, String> producer;

    @Before
    public void setUp() {
        producer = new MockProducer<>(
                true, new StringSerializer(), new StringSerializer());
    }

    @Test
    public void test() {
        for (int i = 0; i < 5; i++)
            producer.send(
                    new ProducerRecord<>("my_topic", "mykey", String.format("myvalue%s", i)));
        List<ProducerRecord<String, String>> history = producer.history();

        List<ProducerRecord<String, String>> expected = Arrays.asList(
                new ProducerRecord<>("my_topic", "mykey", "myvalue0"),
                new ProducerRecord<>("my_topic", "mykey", "myvalue1"),
                new ProducerRecord<>("my_topic", "mykey", "myvalue2"),
                new ProducerRecord<>("my_topic", "mykey", "myvalue3"),
                new ProducerRecord<>("my_topic", "mykey", "myvalue4"));

        assertEquals("Sent didn't match expected", expected, history);
    }
}
