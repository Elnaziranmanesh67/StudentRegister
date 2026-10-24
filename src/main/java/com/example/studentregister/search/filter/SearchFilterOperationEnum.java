package com.example.studentregister.search.filter;

public enum SearchFilterOperationEnum {

    GREATER_THAN("GREATER_THAN"),
    LESS_THAN("LESS_THAN"),
    GREATER_THAN_EQUAL("GREATER_THAN_EQUAL"),
    LESS_THAN_EQUAL("LESS_THAN_EQUAL"),
    EQUAL("EQUAL"),
    NOT_EQUAL("NOT_EQUAL"),
    MATCH("MATCH"),
    MATCH_START("MATCH_START"),
    MATCH_END("MATCH_END"),
    IN("IN"),
    NOT_IN("NOT_IN"),
    MEMBER_OF("MEMBER_OF"),
    NOT_MEMBER_OF("NOT_MEMBER_OF"),
    IS_NULL("IS_NULL"),
    IS_NOT_NULL("IS_NOT_NULL");

    private final String value;

    private SearchFilterOperationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return super.toString();
    }
}
