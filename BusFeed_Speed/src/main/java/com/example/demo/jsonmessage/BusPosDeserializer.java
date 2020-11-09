package com.example.demo.jsonmessage;

import java.io.IOException;
import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

public class BusPosDeserializer<T> implements Deserializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Class<T> tClass;

    public BusPosDeserializer() {
    }

    public BusPosDeserializer(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {
        // nothing to do
    }

    @Override
    public T deserialize(String topic, byte[] bytes) {
        if (bytes == null)
            return null;
        System.out.print(bytes.toString());

        T data;
        try {
        	
        	DeserializationProblemHandler dph = new UnMarshallingErrorHandler();
        	objectMapper.addHandler(dph);
            data = objectMapper.readValue(bytes, tClass);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return data;
    }

    @Override
    public void close() {
        // nothing to do
    }
    
    class UnMarshallingErrorHandler extends DeserializationProblemHandler {
    	
	   @Override
	   public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser jp, @SuppressWarnings("rawtypes") JsonDeserializer deserializer, Object beanOrClass, String propertyName) throws IOException, JsonProcessingException {
	      //boolean result = false;
	      super.handleUnknownProperty(ctxt, jp, deserializer, beanOrClass, propertyName);
	      System.out.println("Property with name '" + propertyName + "' doesn't exist in Class of type '" + beanOrClass.getClass().getName() + "'");
	      return true; // returns true to inform the deserialization process that we can handle the error and it can continue deserializing and returns false, if we want to stop the deserialization immediately.
	   }
    }
}