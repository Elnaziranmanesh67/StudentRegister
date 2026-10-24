
package com.example.studentregister.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import org.springframework.context.MessageSource;

import java.util.Date;

public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper(MessageSource messageSource) {
        super();
        configureMapper(messageSource);
    }

    public CustomObjectMapper() {

    }

    private void configureMapper(MessageSource messageSource) {
        DefaultSerializerProvider.Impl serializerProvider =
                new DefaultSerializerProvider.Impl();

        serializerProvider.setNullValueSerializer(
                new JsonSerializerNullValue()
        );

        setSerializerProvider(serializerProvider);

        SimpleModule module = new SimpleModule();

        module.addSerializer(Date.class, new JsonSerializerDate());

        if (messageSource != null) {
            module.addSerializer(
                    String.class,
                    new JsonSerializerString(messageSource)
            );
        }

        registerModule(module);
    }
}
