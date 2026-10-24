package com.example.studentregister.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class JsonDeserializerString extends JsonDeserializer<String> {

    @Override
    public String deserialize(
            JsonParser parser,
            DeserializationContext context) throws IOException {

        return parser.getValueAsString();
    }
}