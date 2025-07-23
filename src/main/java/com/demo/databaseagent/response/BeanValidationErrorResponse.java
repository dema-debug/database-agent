package com.demo.databaseagent.response;

import com.demo.databaseagent.exception.BaseException;

import java.util.List;

public class BeanValidationErrorResponse extends BaseResponse {

    /**
     * 错误细节信息
     */
    private List<String> details;

    public BeanValidationErrorResponse() {
        super(BaseException.REQUEST_ERROR, "Request error");
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
