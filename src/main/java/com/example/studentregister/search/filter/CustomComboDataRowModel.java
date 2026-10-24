package com.example.studentregister.search.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomComboDataRowModel {
    private String value;
    private String caption;
    private String elementStr;
    private HashMap<String, Object> extMap = new HashMap<>();

    public CustomComboDataRowModel(String value) {
        this.value = value;
        this.caption = value;
        this.elementStr = value;
    }
}
