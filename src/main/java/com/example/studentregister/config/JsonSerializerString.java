package com.example.studentregister.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

public class JsonSerializerString extends JsonSerializer<String> {

    public MessageSource messageSource;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public JsonSerializerString(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void serialize(String objString, JsonGenerator jsonGen, SerializerProvider provider) throws IOException {
        if (ObjectUtils.isEmpty(objString) ) {
            objString = "";
        } else {
            if (objString.length() > 7) {
                if ((!ObjectUtils.isEmpty(messageSource)) && ("etcItem.".equals(objString.substring(0, 8)))) {
                    objString = messageSource.getMessage(objString, new Object[]{}, LocaleContextHolder.getLocale());
                }
            }
        }
        jsonGen.writeString(objString);
    }
}
