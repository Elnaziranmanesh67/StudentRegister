package com.example.studentregister.enumRegister;

public enum AcademicTermEnum {

    SPRING("SPRING"), // بهار
    SUMMER("SUMMER"), // تابستان
    FALL("FALL"),     // پاییز
    WINTER("WINTER"); // زمستان

    private final String value;

    AcademicTermEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
