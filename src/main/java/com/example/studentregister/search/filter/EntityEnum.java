package com.example.studentregister.search.filter;

public enum EntityEnum {

    USER_REGISTER("USER_REGISTER");

    private final String value;

    EntityEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
