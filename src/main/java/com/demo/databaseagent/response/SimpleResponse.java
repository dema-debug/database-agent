package com.demo.databaseagent.response;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author xr
 * @Date 2025/7/22 17:11
 */
public class SimpleResponse<T> extends BaseResponse {

    public static BaseResponse OK() {
        return new BaseResponse();
    }

    /**
     * 输出的任意字段类型
     */
    private T data;

    /**
     * 任意对象类型
     *
     * @param data data
     * @param <T>  T
     * @return 对象
     */
    public static <T> SimpleResponse<T> of(T data) {
        SimpleResponse<T> response = new SimpleResponse<>();
        response.setData(data);
        return response;
    }

    /**
     * 分页类型
     *
     * @param dataList 分页数据
     * @param pages    总页数
     * @param total    总条数
     * @param <T>      T
     * @return 分页model
     */
    public static <T> SimpleResponse<PageData<T>> ofPage(List<T> dataList, Integer pages, Integer total) {
        SimpleResponse<PageData<T>> response = new SimpleResponse<>();
        PageData<T> pageData = new PageData<>();
        pageData.setList(dataList);
        pageData.setPages(pages);
        pageData.setTotal(total);
        response.setData(pageData);
        return response;
    }

    /**
     * 空分页
     *
     * @param <T> T
     * @return 分页model
     */
    public static <T> SimpleResponse<PageData<T>> ofNullPage() {
        SimpleResponse<PageData<T>> response = new SimpleResponse<>();
        PageData<T> pageData = new PageData<>();
        pageData.setList(new ArrayList<>());
        pageData.setPages(1);
        pageData.setTotal(0);
        response.setData(pageData);
        return response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}