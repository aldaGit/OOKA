package org.bonn.ia.kafka.stream.properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.bonn.ia.kafka.serde.EmployeeSerde;

import java.util.Properties;

public class StreamProperties {

    public static Properties getPropertiesForEmployeeDemo() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "sepp-kafka.inf.h-brs.de:9092");
        props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName());
        props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, EmployeeSerde.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }


}
