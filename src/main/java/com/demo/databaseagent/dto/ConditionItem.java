package com.demo.databaseagent.dto;

import java.util.List;

/**
 * @Description
 * @Author xr
 * @Date 2025/7/22 16:32
 */
public class ConditionItem {

    private String conditionType;

    private List<RequestSimpleItem> conditionItems;

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public List<RequestSimpleItem> getConditionItems() {
        return conditionItems;
    }

    public void setConditionItems(List<RequestSimpleItem> conditionItems) {
        this.conditionItems = conditionItems;
    }
}
