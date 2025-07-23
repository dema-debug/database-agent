package com.demo.databaseagent;

import com.demo.databaseagent.dto.ConditionItem;
import com.demo.databaseagent.dto.RequestSimpleItem;
import com.demo.databaseagent.request.CourseQueryRequest;
import com.demo.databaseagent.request.CustomQueryRequest;
import com.demo.databaseagent.request.StudentQueryRequest;
import com.demo.databaseagent.service.StudentsCourseService;
import com.demo.databaseagent.service.impl.StudentsCourseServiceImpl;
import com.demo.databaseagent.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class DatabaseAgentApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseAgentApplicationTests.class);

    @Autowired
    StudentsCourseService studentsCourseService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testStudentList() {
        StudentQueryRequest sqr = new StudentQueryRequest();
        studentsCourseService.getAllStudents(sqr);
    }

    @Test
    public void testCourseList() {
        CourseQueryRequest cqr = new CourseQueryRequest();
        studentsCourseService.getAllCourses(cqr);
    }

    @Test
    public void testStudentSql() {

        CustomQueryRequest cqr = new CustomQueryRequest();
        cqr.setPage(1);
        cqr.setSize(10);
        cqr.setSort("id");
        cqr.setOrder("desc");

        var conditionItem1 = new ConditionItem();
        conditionItem1.setConditionType("and");
        conditionItem1.setConditionItems(List.of(new RequestSimpleItem("students", "id", "=", List.of("1"))));

        var conditionItem2 = new ConditionItem();
        conditionItem2.setConditionType("or");
        conditionItem2.setConditionItems(List.of(new RequestSimpleItem("students", "id", "=", List.of("2"))));

        var conditionList = new ArrayList<ConditionItem>();
        conditionList.add(conditionItem1);
        conditionList.add(conditionItem2);

        cqr.setConditions(conditionList);

        var result = studentsCourseService.customQuery(cqr);
        var resultStr = JsonUtil.writeValueAsString(objectMapper, result);
        System.out.println(resultStr);
    }

    @Test
    public void testCourseSql() {

        CustomQueryRequest cqr = new CustomQueryRequest();
        cqr.setPage(1);
        cqr.setSize(10);
        cqr.setSort("id");
        cqr.setOrder("desc");

        var conditionItem1 = new ConditionItem();
        conditionItem1.setConditionType("and");
        conditionItem1.setConditionItems(List.of(new RequestSimpleItem("courses", "id", "!=", List.of("1"))));

        var conditionItem2 = new ConditionItem();
        conditionItem2.setConditionType("or");
        conditionItem2.setConditionItems(List.of(new RequestSimpleItem("courses", "id", "=", List.of("3"))));

        var conditionList = new ArrayList<ConditionItem>();
        conditionList.add(conditionItem1);
        conditionList.add(conditionItem2);

        cqr.setConditions(conditionList);

        var result = studentsCourseService.customQuery(cqr);
        var resultStr = JsonUtil.writeValueAsString(objectMapper, result);
        System.out.println(resultStr);
    }

}
