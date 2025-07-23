package com.demo.databaseagent.dto;

import java.util.List;

public class RequestSimpleItem extends SimpleItem {

    private String operator;

    public RequestSimpleItem() {
    }

    public RequestSimpleItem(String table, String fieldName, String operator, List<String> fieldValue) {
        super(table, fieldName, fieldValue);
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
