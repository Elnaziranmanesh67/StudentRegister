package com.example.studentregister.search.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFilterBox {

    private List<SearchFilterRestrictionModel> restrictionList = new ArrayList<>();
    private SearchFilterNextConditionOperatorEnum nextConditionOperator= SearchFilterNextConditionOperatorEnum.AND;
}
