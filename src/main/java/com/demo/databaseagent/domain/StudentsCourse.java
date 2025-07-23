package com.demo.databaseagent.domain;

import com.demo.databaseagent.config.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName students_course
 */
@TableName("students_course")
public class StudentsCourse implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer studentId;

    /**
     * 
     */
    private Integer courseId;

    /**
     * 
     */
    private Date createTime;

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
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * 
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * 
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * 
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
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
}