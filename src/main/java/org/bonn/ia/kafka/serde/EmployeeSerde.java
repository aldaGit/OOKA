package org.bonn.ia.kafka.serde;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.bonn.ia.kafka.pojo.Employee;

import java.util.Map;

public class EmployeeSerde implements Serde<Employee> {

    private EmployeeSerializer serializer = new EmployeeSerializer();
    private EmployeeDeserializer deserializer = new EmployeeDeserializer();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public void close() {
        this.deserializer.close();
        this.serializer.close();

    }

    @Override
    public Serializer<Employee> serializer() {
        return this.serializer;
    }

    @Override
    public Deserializer<Employee> deserializer() {
        return this.deserializer;
    }
}
