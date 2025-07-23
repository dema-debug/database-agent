package com.demo.databaseagent.controller;

import com.demo.databaseagent.request.CourseQueryRequest;
import com.demo.databaseagent.request.CustomQueryRequest;
import com.demo.databaseagent.request.StudentQueryRequest;
import com.demo.databaseagent.response.BaseResponse;
import com.demo.databaseagent.response.SimpleResponse;
import com.demo.databaseagent.service.StudentsCourseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author xr
 * @Date 2025/7/22 15:08
 */
@RestController
@RequestMapping("/sc")
public class StudentCourseController {

    private final StudentsCourseService studentsCourseService;

    public StudentCourseController(StudentsCourseService studentsCourseService) {
        this.studentsCourseService = studentsCourseService;
    }

    @GetMapping("student/list")
    public BaseResponse getAllStudents(StudentQueryRequest sqr) {
        return SimpleResponse.of(studentsCourseService.getAllStudents(sqr));
    }

    @GetMapping("course/list")
    public BaseResponse getAllCourses(CourseQueryRequest cqr) {
        return SimpleResponse.of(studentsCourseService.getAllCourses(cqr));
    }

    @PostMapping("custom")
    public BaseResponse customQuery(@Valid @RequestBody CustomQueryRequest cqr) {
        return SimpleResponse.of(studentsCourseService.customQuery(cqr));
    }
}
