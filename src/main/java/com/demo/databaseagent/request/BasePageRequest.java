package com.demo.databaseagent.request;

/**
 * @Description
 * @Author xr
 * @Date 2025/7/22 16:05
 */
public class BasePageRequest {

    private int page = 1;

    private int size = 5;

    private String sort = "id";

    private String order = "desc";

    private String keyword;

    private int offset;

    public int getOffset() {
        offset = (page - 1) * size;
        return offset;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
