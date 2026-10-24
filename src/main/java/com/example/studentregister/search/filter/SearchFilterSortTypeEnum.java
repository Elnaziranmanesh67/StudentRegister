package com.example.studentregister.search.filter;
public enum SearchFilterSortTypeEnum {
    ASC("ASC"),
    DSC("DSC");

    //نوع مرتب سازی
    private String value;
    public String getValue() {
        return this.value;
    }
    SearchFilterSortTypeEnum(String value) {
        this.value = value;
    }

}
