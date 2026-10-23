package com.example.studentregister.enumRegister;

public enum PassStatusEnum {

    PENDING("PENDING"), // در انتظار تعیین نتیجه
    PASSED("PASSED"),   // قبول
    FAILED("FAILED");   // مردود

    private final String value;

    PassStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
