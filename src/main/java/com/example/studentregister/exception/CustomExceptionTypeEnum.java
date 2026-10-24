package com.example.studentregister.exception;
public enum CustomExceptionTypeEnum {
    BUSINESS_EXCEPTION("BUSINESS_EXCEPTION");
    private final String value;

    CustomExceptionTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return super.toString();
    }
}
