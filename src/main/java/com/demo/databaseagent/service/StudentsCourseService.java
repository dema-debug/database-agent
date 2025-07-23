package com.demo.databaseagent.service;

import com.demo.databaseagent.dto.ResponseSimpleItem;
import com.demo.databaseagent.request.CourseQueryRequest;
import com.demo.databaseagent.request.CustomQueryRequest;
import com.demo.databaseagent.request.StudentQueryRequest;
import com.demo.databaseagent.response.PageData;

import java.util.List;

/**
* @author Administrator
* @description 针对表【students_course】的数据库操作Service
* @createDate 2025-07-22 15:32:00
*/
public interface StudentsCourseService {

    PageData<List<ResponseSimpleItem>> customQuery(CustomQueryRequest cqr);

    PageData<List<ResponseSimpleItem>> getAllStudents(StudentQueryRequest sqr);

    PageData<List<ResponseSimpleItem>> getAllCourses(CourseQueryRequest cqr);
}
