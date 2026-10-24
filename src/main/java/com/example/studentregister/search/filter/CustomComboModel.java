package com.example.studentregister.search.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomComboModel {
    private List<CustomComboDataRowModel> dataList = new ArrayList<>();

    private Integer totalCount;

    private Integer page;

    private Integer rows;

}
