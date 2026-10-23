package com.example.studentregister.enumRegister;

public enum GenderEnum {

    FEMALE("FEMALE"),
    MALE("MALE");

    private final String value;

    GenderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
