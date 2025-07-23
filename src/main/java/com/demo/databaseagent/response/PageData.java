package com.demo.databaseagent.response;

import java.util.List;

/**
 * @Description
 * @Author xr
 * @Date 2025/7/22 17:11
 */
public class PageData<T> {
    private List<T> list;

    private Integer pages;

    private Integer total;

    public PageData() {
    }

    public PageData(List<T> list, Integer pages, Integer total) {
        this.list = list;
        this.pages = pages;
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
