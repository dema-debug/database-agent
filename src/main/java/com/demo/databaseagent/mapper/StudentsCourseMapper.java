package com.demo.databaseagent.mapper;

import com.demo.databaseagent.domain.Courses;
import com.demo.databaseagent.domain.Students;
import com.demo.databaseagent.request.CourseQueryRequest;
import com.demo.databaseagent.request.CustomQueryRequest;
import com.demo.databaseagent.request.StudentQueryRequest;
import com.demo.databaseagent.response.CommonResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【students_course】的数据库操作Mapper
* @createDate 2025-07-22 15:32:00
* @Entity generator.domain.StudentsCourse
*/
public interface StudentsCourseMapper {

    int queryCountByCustom(CustomQueryRequest cqr);

    int queryCountBySql(@Param("countField") String countField, @Param("whereStr") String whereStr);

    List<Map<String, Object>> queryListBySql(@Param("objField") String objField, @Param("whereStr") String whereStr);

    Integer queryStudentCount(@Param("sqr") StudentQueryRequest sqr);

    List<Students> queryStudents(@Param("sqr") StudentQueryRequest sqr);

    Integer queryCourseCount(@Param("cqr") CourseQueryRequest cqr);

    List<Courses> queryCourses(@Param("cqr") CourseQueryRequest cqr);
}
