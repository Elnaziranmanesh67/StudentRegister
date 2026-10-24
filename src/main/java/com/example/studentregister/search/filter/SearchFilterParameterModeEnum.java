package com.example.studentregister.search.filter;

public enum SearchFilterParameterModeEnum {
    GENERAL("GENERAL"),
    ;

    private final String value;

     SearchFilterParameterModeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return super.toString();
    }
}
