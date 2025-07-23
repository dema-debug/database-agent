package com.demo.databaseagent.request;

/**
 * @Description
 * @Author xr
 * @Date 2025/7/22 16:17
 */
public class CourseQueryRequest extends BasePageRequest{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
