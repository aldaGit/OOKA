package org.bonn.ia.kafka.serde;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.Charset;
import java.util.Map;

public class EmployeeSerializer implements Serializer {

    private static final Charset CHARSET = Charset.forName("UTF-8");
    static private Gson gson = new Gson();


    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Object o) {
        String line = gson.toJson(o);
        // Return the bytes from the String 'line'
        return line.getBytes(CHARSET);
    }

    @Override
    public void close() {

    }
}
