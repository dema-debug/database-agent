package com.demo.databaseagent.request;

import com.demo.databaseagent.dto.ConditionItem;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * @Description
 * @Author xr
 * @Date 2025/7/22 16:19
 */
public class CustomQueryRequest extends BasePageRequest {

    @NotEmpty
    private List<ConditionItem> conditions;

    public List<ConditionItem> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionItem> conditions) {
        this.conditions = conditions;
    }
}
