package com.example.studentregister.config;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class JsonSerializerNullValue extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator gen,SerializerProvider serializers) throws IOException {

        gen.writeNull();
    }
}
