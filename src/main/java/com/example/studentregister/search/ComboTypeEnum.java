package com.example.studentregister.search;

public enum ComboTypeEnum {
    COMBO("COMBO"),
    AUTOCOMPLETE("AUTOCOMPLETE");

    private final String value;

    ComboTypeEnum(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }
}
