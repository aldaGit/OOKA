package org.bonn.ia.kafka.stream;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.bonn.ia.kafka.pojo.Employee;
import org.bonn.ia.kafka.serde.EmployeeSerde;
import org.bonn.ia.kafka.stream.properties.StreamProperties;

import java.util.HashMap;
import java.util.Properties;

public class EmployeeFilter {

    public static void main(String[] args) {
        EmployeeFilter ex  = new EmployeeFilter();
        ex.start();
    }

    public void start() {

        Properties props = StreamProperties.getPropertiesForEmployeeDemo();

        // As of Kafka 1.1.0, the Builder class is depreceated.
        // However, this is the most frequently documented version.
        KStreamBuilder builder = new KStreamBuilder();

        // Abstraction of the input stream, that receives messages from topic 'employee'
        KStream<Integer, Employee> sourceFilter = builder.stream("employee");

        // Definition of Filter for selecting employees with a big competence > 3
        // (Pattern Content Filter)
        // Creation of a new output stream, which pushes filtered messages to topic 'employee_filtered'
        KStream filter = sourceFilter
                .filter( ( key, value ) -> ( value.getLeaderShipCompetence() > 3 ) )
                .map( ( key, value ) ->  KeyValue.pair( key, transform(value) ) );
        filter.to("employee_filtered");

        System.out.println("Generated Streams for filtering employees!");

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

    private Employee transform(Employee value) {
        Employee employeeNEW = new Employee();

        // Define ID mapping (prototype) and transform IDs:
        HashMap<Integer,Integer> mapID = new HashMap<Integer, Integer>();
        mapID.put(new Integer(22) , new Integer(90123));
        mapID.put(new Integer(23) , new Integer(90456));

        // Value-based Data transformation on employee ID (Pattern Message Translator)
        employeeNEW.setEmployeeID( mapID.getOrDefault(value.getEmployeeID() , 123) );

        // Enrich department data (Pattern Content Enricher)
        employeeNEW.setDepartment(  value.getDepartment() + " (INTERNAL)" );

        // Simple mappings:
        employeeNEW.setFirstName(value.getFirstName());
        employeeNEW.setLastName(value.getLastName());
        employeeNEW.setLeaderShipCompetence(value.getLeaderShipCompetence());

        return employeeNEW;
    }
}
