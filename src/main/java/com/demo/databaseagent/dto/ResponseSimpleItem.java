package com.demo.databaseagent.dto;

import java.util.List;

public class ResponseSimpleItem extends SimpleItem {

    private String fieldCnName;

    public ResponseSimpleItem() {
    }

    public ResponseSimpleItem(String table, String fieldName, String fieldCnName, List<String> fieldValue) {
        super(table, fieldName, fieldValue);
        this.fieldCnName = fieldCnName;
    }

    public String getFieldCnName() {
        return fieldCnName;
    }

    public void setFieldCnName(String fieldCnName) {
        this.fieldCnName = fieldCnName;
    }
}
