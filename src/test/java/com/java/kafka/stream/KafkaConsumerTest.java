package com.java.kafka.stream;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class KafkaConsumerTest {
    MockConsumer<String, String> consumer;

    @Before
    public void setUp() {
        consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
    }

    @Test
    public void testConsumer() throws IOException {
        consumer.assign(Arrays.asList(new TopicPartition("my_topic", 0)));

        Map<TopicPartition, Long> beginningOffsets = new HashMap<>();
        beginningOffsets.put(new TopicPartition("my_topic", 0), 0L);
        consumer.updateBeginningOffsets(beginningOffsets);

        consumer.addRecord(new ConsumerRecord<>("my_topic", 0, 0L, "mykey", "myvalue0"));
        consumer.addRecord(new ConsumerRecord<>("my_topic", 0, 1L, "mykey", "myvalue1"));
        consumer.addRecord(new ConsumerRecord<>("my_topic", 0, 2L, "mykey", "myvalue2"));
        consumer.addRecord(new ConsumerRecord<>("my_topic", 0, 3L, "mykey", "myvalue3"));
        consumer.addRecord(new ConsumerRecord<>("my_topic", 0, 4L, "mykey", "myvalue4"));

        assertEquals(5, consumer.poll(1000).count());
    }
}
