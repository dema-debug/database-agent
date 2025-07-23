package com.demo.databaseagent.dto;

import java.util.List;

/**
 * @Description
 * @Author xr
 * @Date 2025/7/22 16:30
 */
public class SimpleItem {

    private String fieldName;

    private String table;

    private List<String> fieldValue;

    public SimpleItem() {
    }

    public SimpleItem(String table, String fieldName, List<String> fieldValue) {
        this.table = table;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<String> getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(List<String> fieldValue) {
        this.fieldValue = fieldValue;
    }
}
