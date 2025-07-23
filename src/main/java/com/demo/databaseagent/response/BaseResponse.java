package com.demo.databaseagent.response;


import com.demo.databaseagent.exception.BaseException;

/**
 * @Description 通用响应
 * @Author xr
 * @Date 2025/7/13 15:16
 */
public class BaseResponse {

    private int code = 0;

    private String message = "success";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 很多接口可以直接返回OK
     */
    public BaseResponse() {
        this(0, "OK");
    }

    /**
     * 错误响应时的构造器，出现error时重新指定code和message
     *
     * @param e 错误信息
     */
    public BaseResponse(BaseException e) {
        this.code = e.getCode();
        this.message = e.getMessage();
    }
}
