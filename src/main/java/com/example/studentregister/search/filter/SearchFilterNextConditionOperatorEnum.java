package com.example.studentregister.search.filter;

public enum SearchFilterNextConditionOperatorEnum {
    AND("AND"),
    OR("OR"),
    IS_NOT_NULL("IS_NOT_NULL"),
    IS_NULL("IS_NULL")

    ;

    private final String value;

    private SearchFilterNextConditionOperatorEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return super.toString();
    }
}
