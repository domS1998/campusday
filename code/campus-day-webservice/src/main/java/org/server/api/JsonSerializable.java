package org.server.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public abstract class JsonSerializable {

    protected static ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);


    // Objektorientiertes Serialisieren
    @Override
    public String toString() {
        try {
            return mapper.writeValueAsString(this);
        }
        catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    // Statisches Serialisieren
    public static String serialize(Object object) {
        try {
            return mapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    // Statisches Deserialisieren
    public static Object deserialize(String json, Class targetClass) {
        try {
            return mapper.readValue(json, targetClass);
        }
        catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
