package com.example.studentregister.config;

import com.example.studentregister.calendar.CalendarTools;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.util.Date;

public class JsonSerializerDate extends JsonSerializer<Date> {

    @Override
    public void serialize(
            Date date,
            JsonGenerator jsonGen,
            SerializerProvider provider) throws IOException {

        String formattedDate = CalendarTools.fixToLocaleDate(
                date,
                "/",
                LocaleContextHolder.getLocale()
        );

        jsonGen.writeString(formattedDate);
    }
}