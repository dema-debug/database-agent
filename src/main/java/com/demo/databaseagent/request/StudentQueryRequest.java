package com.demo.databaseagent.request;

/**
 * @Description
 * @Author xr
 * @Date 2025/7/22 16:14
 */
public class StudentQueryRequest extends BasePageRequest{

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
