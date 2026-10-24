package com.example.studentregister.search.filter;

import java.io.Serializable;
public class SearchFilterRestrictionModel implements Serializable {

    private String fieldName;
    private SearchFilterOperationEnum fieldOperation;
    private Object fieldValue;

    SearchFilterNextConditionOperatorEnum nextConditionOperator= SearchFilterNextConditionOperatorEnum.AND;

    public SearchFilterRestrictionModel() {
    }

    public SearchFilterRestrictionModel(String fieldName, SearchFilterOperationEnum fieldOperation, Object fieldValue, SearchFilterNextConditionOperatorEnum nextConditionOperator) {
        this.fieldName = fieldName;
        this.fieldOperation = fieldOperation;
        this.fieldValue = fieldValue;
        this.nextConditionOperator=nextConditionOperator;
    }

    @Override
    public String toString() {
        return "SearchFilterRestrictionModel{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldOperation=" + fieldOperation +
                ", fieldValue=" + fieldValue +
                ", nextConditionOperator=" + nextConditionOperator +
                '}';
    }

    //getter-setter:
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public SearchFilterOperationEnum getFieldOperation() {
        return fieldOperation;
    }

    public void setFieldOperation(SearchFilterOperationEnum fieldOperation) {
        this.fieldOperation = fieldOperation;
    }

    public SearchFilterNextConditionOperatorEnum getNextConditionOperator() {
        return nextConditionOperator;
    }

    public void setNextConditionOperator(SearchFilterNextConditionOperatorEnum nextConditionOperator) {
        this.nextConditionOperator = nextConditionOperator;
    }
}
