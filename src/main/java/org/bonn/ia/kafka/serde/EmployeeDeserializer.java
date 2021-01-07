package org.bonn.ia.kafka.serde;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Deserializer;
import org.bonn.ia.kafka.pojo.Employee;

import java.nio.charset.Charset;
import java.util.Map;

public class EmployeeDeserializer implements Deserializer {
    private static final Charset CHARSET = Charset.forName("UTF-8");
    static private Gson gson;

    static {
        gson = new Gson();
    }

    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public Object deserialize(String s, byte[] bytes) {
        try {
            // Transform the bytes to String
            String person = new String(bytes, CHARSET);
            // Return the Person object created from the String 'person'
            return gson.fromJson(person, Employee.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading bytes!", e);
        }
    }

    @Override
    public void close() {

    }
}
