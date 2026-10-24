package com.example.studentregister.search;

public enum EntityParametersModeEnum {
    USER_REGISTER("USER_REGISTER");
    private final String value;

    EntityParametersModeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
