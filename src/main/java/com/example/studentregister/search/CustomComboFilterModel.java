package com.example.studentregister.search;

import com.example.studentregister.search.filter.EntityEnum;
import lombok.Data;

import java.util.HashMap;

@Data
public class CustomComboFilterModel {

    private EntityParametersModeEnum mode;
    private EntityEnum entity;
    private String input;
    private HashMap<String, Object> extMap = new HashMap<>();
    private Integer page = 0;
    private Integer rows = 1;

    private ComboTypeEnum type;

    public EntityParametersModeEnum getMode() {
        return mode;
    }

}
