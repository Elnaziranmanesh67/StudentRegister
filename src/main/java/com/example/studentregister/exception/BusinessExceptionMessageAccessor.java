package com.example.studentregister.exception;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class BusinessExceptionMessageAccessor {

    private final MessageSource messageSource;

    BusinessExceptionMessageAccessor(@Qualifier("businessExceptionMessageSource") MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    public String getMessage(Locale locale, String key, Object... parameter) {
        String message;
        try {
            message = messageSource.getMessage(key, parameter, locale);
        } catch (Exception e) {
            message = key;
        }
        return message;
    }

}
