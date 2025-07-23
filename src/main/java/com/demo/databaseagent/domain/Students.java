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
 * @TableName students
 */
@TableName("students")
public class Students implements Serializable {

    /**
     * 
     */
    @ColumnName("学生编号")
    private Integer id;

    /**
     * 
     */
    @ColumnName("学生姓名")
    private String name;

    /**
     * 
     */
    @ColumnName("学生地址")
    private String address;

    /**
     * 
     */
    @ColumnName("学生年龄")
    private Integer age;

    /**
     * 
     */
    @ColumnName("学生创建时间")
    @JsonProperty("create_time")
    @JsonDeserialize(using = LocalDateTimeToDateDeserializer.class)
    private Date createTime;

    /**
     * 
     */
    @ColumnName("学生创建人员")
    @JsonProperty("create_user")
    private Integer createUser;

    /**
     * 
     */
    @ColumnName("学生更新时间")
    @JsonProperty("update_time")
    @JsonDeserialize(using = LocalDateTimeToDateDeserializer.class)
    private Date updateTime;

    /**
     * 
     */
    @ColumnName("学生更新时间")
    @JsonProperty("update_user")
    private Integer updateUser;

    /**
     * 
     */
    @ColumnName("学生状态")
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
    public String getAddress() {
        return address;
    }

    /**
     * 
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 
     */
    public void setAge(Integer age) {
        this.age = age;
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