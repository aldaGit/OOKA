package org.bonn.ia.kafka.stream;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

public class SimpleFilterExample {

    public static void main(String[] args) {
        SimpleFilterExample ex  = new SimpleFilterExample();
        ex.start();
    }

    public void SimpleFilterExample() {
    }

    public void start() {

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "sepp-kafka.inf.h-brs.de:9092");
        props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName());
        props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KStreamBuilder builder = new KStreamBuilder();

        KStream<Integer, String> sourceFilter = builder.stream("demo");
        KStream filter = sourceFilter
                .filter( ( key,value ) -> ( key > 3 ) )
                .map( (key, value ) ->  KeyValue.pair(key, value + " being filtered!") );
        filter.to("demo-filtered");

        System.out.println("Generated Streams for filtering keys!");

        KafkaStreams streams = new KafkaStreams(builder, props);

        streams.cleanUp();

        streams.start();

        // usually the stream application would be running forever,
        // in this example we just let it run for some time and stop since the input data is finite.
        try {
            Thread.sleep(50000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        streams.close();

    }
}
