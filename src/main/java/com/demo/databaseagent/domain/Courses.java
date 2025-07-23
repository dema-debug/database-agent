package com.demo.databaseagent.domain;

import com.demo.databaseagent.config.ColumnName;
import com.demo.databaseagent.config.LocalDateTimeToDateDeserializer;
import com.demo.databaseagent.config.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName course
 */
@TableName(value = "courses")
public class Courses implements Serializable {

    /**
     * 
     */
    @ColumnName("课程编号")
    private Integer id;

    /**
     * 
     */
    @ColumnName("课程名称")
    private String name;

    /**
     * 
     */
    @ColumnName("课程备注")
    private String remark;

    /**
     * 
     */
    @ColumnName("课程创建时间")
    @JsonProperty("create_time")
    @JsonDeserialize(using = LocalDateTimeToDateDeserializer.class)
    private Date createTime;

    /**
     * 
     */
    @ColumnName("课程创建人员")
    @JsonProperty("create_user")
    private Integer createUser;

    /**
     * 
     */
    @ColumnName("课程更新时间")
    @JsonProperty("update_time")
    @JsonDeserialize(using = LocalDateTimeToDateDeserializer.class)
    private Date updateTime;

    /**
     * 
     */
    @ColumnName("课程更新人员")
    @JsonProperty("update_user")
    private Integer updateUser;

    /**
     * 
     */
    @ColumnName("课程状态")
    private Integer enable;

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * 
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     */
    public Integer getEnable() {
        return enable;
    }

    /**
     * 
     */
    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}