package com.example.studentregister.exception;

import lombok.Getter;
@Getter
public class BusinessException extends RuntimeException {

    private final CustomExceptionKey errorMessage;

    private final String extraData;

    public BusinessException(CustomExceptionKey errorMessage) {
        this(errorMessage, "");
    }

    public BusinessException(CustomExceptionKey errorMessage , String extraData) {
        this.errorMessage = errorMessage;
        this.extraData = extraData;
    }

}
