package org.bonn.ia.kafka.stream;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.bonn.ia.kafka.pojo.Employee;
import org.bonn.ia.kafka.stream.properties.StreamProperties;

import java.util.HashMap;
import java.util.Properties;

public class EmployeeBranch {

    public static void main(String[] args) {
        EmployeeBranch ex  = new EmployeeBranch();
        ex.start();
    }

    public void start() {

        Properties props = StreamProperties.getPropertiesForEmployeeDemo();

        // As of Kafka 1.1.0, the Builder class is depreceated.
        // However, this is the most frequently documented version.
        KStreamBuilder builder = new KStreamBuilder();


        // Abstraction of the input stream
        KStream<Integer, Employee> sourceFilter = builder.stream("employee");

        // Definition of a Message Router for branching employees according to their departments
        // Pattern Message Router
        // Creation of a new output stream
        KStream<Integer,Employee>[] filter = sourceFilter.branch(
                (key , value) -> value.getDepartment().contains("Marketing"),
                (key , value) -> value.getDepartment().contains("Sales")
        );
        // optional: doing some filtering or mapping....

        filter[0].to("employee_marketing");
        filter[1].to("employee_sales");

        System.out.println("Generated Streams for branching employees!");

        // Start up the stream processor
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
