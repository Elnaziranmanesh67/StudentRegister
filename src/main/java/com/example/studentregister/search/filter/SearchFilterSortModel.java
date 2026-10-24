package com.example.studentregister.search.filter;

import java.io.Serializable;
public class SearchFilterSortModel implements Serializable {
    private String fieldName;
    private SearchFilterSortTypeEnum type;
    public SearchFilterSortModel() {
    }

    public SearchFilterSortModel(String fieldName, SearchFilterSortTypeEnum type) {
        this.fieldName = fieldName;
        this.type = type;
    }

    @Override
    public String toString() {
        return "SearchFilterSortModel{" +
                "fieldName='" + fieldName + '\'' +
                ", type=" + type +
                '}';
    }

    //getter-setter
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public SearchFilterSortTypeEnum getType() {
        return type;
    }

    public void setType(SearchFilterSortTypeEnum type) {
        this.type = type;
    }
}
