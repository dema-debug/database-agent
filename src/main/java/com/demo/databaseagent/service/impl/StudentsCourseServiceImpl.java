package com.demo.databaseagent.service.impl;

import com.demo.databaseagent.config.ColumnName;
import com.demo.databaseagent.config.TableName;
import com.demo.databaseagent.dto.ConditionItem;
import com.demo.databaseagent.dto.ResponseSimpleItem;
import com.demo.databaseagent.dto.Tuple2;
import com.demo.databaseagent.dto.Tuple3;
import com.demo.databaseagent.exception.BaseException;
import com.demo.databaseagent.mapper.StudentsCourseMapper;
import com.demo.databaseagent.request.CourseQueryRequest;
import com.demo.databaseagent.request.CustomQueryRequest;
import com.demo.databaseagent.request.StudentQueryRequest;
import com.demo.databaseagent.response.PageData;
import com.demo.databaseagent.service.StudentsCourseService;
import com.demo.databaseagent.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【students_course】的数据库操作Service实现
 * @createDate 2025-07-22 15:32:00
 */
@Service
public class StudentsCourseServiceImpl implements StudentsCourseService {

    private static final Logger logger = LoggerFactory.getLogger(StudentsCourseServiceImpl.class);

    private static final Set<String> ALLOWED_SQL_KEYWORD = new HashSet<>();

    private static final Set<String> ALLOWED_TABLE = new HashSet<>();

    private static final Set<String> ALLOWED_OPERATOR = new HashSet<>();

    private static final HashMap<String, Class<?>> TABLE_2_CLASS = new HashMap<>();

    private final StudentsCourseMapper studentsCourseMapper;
    private final ObjectMapper objectMapper;

    public StudentsCourseServiceImpl(StudentsCourseMapper studentsCourseMapper, @Qualifier("objectMapper") ObjectMapper objectMapper) {
        this.studentsCourseMapper = studentsCourseMapper;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        // 关键词
        ALLOWED_SQL_KEYWORD.add("and");
        ALLOWED_SQL_KEYWORD.add("or");

        // 表名
        ALLOWED_TABLE.add("students");
        ALLOWED_TABLE.add("courses");

        // 操作符
        ALLOWED_OPERATOR.add(">");
        ALLOWED_OPERATOR.add(">=");
        ALLOWED_OPERATOR.add("<");
        ALLOWED_OPERATOR.add("<=");
        ALLOWED_OPERATOR.add("=");
        ALLOWED_OPERATOR.add("!=");
        ALLOWED_OPERATOR.add("like");
        ALLOWED_OPERATOR.add("in");
        ALLOWED_OPERATOR.add("not in");

        findTableNameAnnotation();
    }

    @Override
    public PageData<List<ResponseSimpleItem>> customQuery(CustomQueryRequest cqr) {
        checkRequest(cqr);

        var countSql = parseCountSql(cqr);
        int totalCount = studentsCourseMapper.queryCountBySql(countSql.getValue1(), countSql.getValue2());
        if (totalCount == 0) {
            return new PageData<>(new ArrayList<>(), 0, 0);
        }

        var objSql = parseObjSql(cqr);
        PageData<List<ResponseSimpleItem>> pageData = new PageData<>();
        var curPageData = studentsCourseMapper.queryListBySql(objSql.getValue1(), objSql.getValue2());
        List<List<ResponseSimpleItem>> responseList = doParseTableEntity(objSql.getValue3(), curPageData);
        pageData.setList(responseList);
        pageData.setTotal(totalCount);
        pageData.setPages((totalCount + cqr.getSize() - 1) / cqr.getSize());

        return pageData;
    }

    @Override
    public PageData<List<ResponseSimpleItem>> getAllStudents(StudentQueryRequest sqr) {

        var totalCount = studentsCourseMapper.queryStudentCount(sqr);
        if (totalCount == 0) {
            return new PageData<>(new ArrayList<>(), 0, 0);
        }

        var result = new ArrayList<List<ResponseSimpleItem>>();

        var studentList = studentsCourseMapper.queryStudents(sqr);
        studentList.forEach(student -> result.add(fineColumnNameAnnotation("students", student)));
        PageData<List<ResponseSimpleItem>> pageData = new PageData<>();
        pageData.setList(result);
        pageData.setTotal(totalCount);
        pageData.setPages((totalCount + sqr.getSize() - 1) / sqr.getSize());
        return pageData;
    }

    @Override
    public PageData<List<ResponseSimpleItem>> getAllCourses(CourseQueryRequest cqr) {

        var totalCount = studentsCourseMapper.queryCourseCount(cqr);
        if (totalCount == 0) {
            return new PageData<>(new ArrayList<>(), 0, 0);
        }

        var courseList = studentsCourseMapper.queryCourses(cqr);
        var result = new ArrayList<List<ResponseSimpleItem>>();
        courseList.forEach(student -> result.add(fineColumnNameAnnotation("courses", student)));
        PageData<List<ResponseSimpleItem>> pageData = new PageData<>();
        pageData.setList(result);
        pageData.setTotal(totalCount);
        pageData.setPages((totalCount + cqr.getSize() - 1) / cqr.getSize());
        return pageData;
    }

    // ------------------------------------------------------------------------------
    private void checkRequest(CustomQueryRequest cqr) {
        var conditionList = cqr.getConditions();
        var typeList = conditionList.stream().map(ConditionItem::getConditionType).toList();
        var existIllegalType = typeList.stream().anyMatch(type -> !ALLOWED_SQL_KEYWORD.contains(type.toLowerCase()));
        if (existIllegalType) {
            throw new BaseException(BaseException.REQUEST_ERROR, StringUtils.join(typeList, ",") + "存在非法运算符!");
        }

        var tableList = new HashSet<String>();
        var operatorList = new HashSet<String>();
        for (ConditionItem condition : conditionList) {
            var itemList = condition.getConditionItems();
            if (CollectionUtils.isEmpty(itemList)) {
                continue;
            }
            for (var item : itemList) {
                tableList.add(item.getTable());
                operatorList.add(item.getOperator());
            }
        }
        var existIllegalTable = tableList.stream().anyMatch(table -> !ALLOWED_TABLE.contains(table.toLowerCase()));
        if (existIllegalTable) {
            throw new BaseException(BaseException.REQUEST_ERROR, StringUtils.join(tableList, ",") + "存在非法运算符!");
        }

        var existIllegalOperator = operatorList.stream().anyMatch(operator -> !ALLOWED_OPERATOR.contains(operator.toLowerCase()));
        if (existIllegalOperator) {
            throw new BaseException(BaseException.REQUEST_ERROR, StringUtils.join(operatorList, ",") + "存在非法运算符!");
        }
    }

    private Tuple3<String, String, String> parseCountSql(CustomQueryRequest cqr) {
        var tableAndWhere = parseSql(cqr);
        var tableList = tableAndWhere.getValue1();
        var whereStr = tableAndWhere.getValue2();

        var value1 = "count(*)";
        // TODO join
        var tableName = tableList.stream().findFirst().get();

        var sqlTrunkList = new ArrayList<String>();
        sqlTrunkList.add(tableName);
        sqlTrunkList.add("where 1=1");
        sqlTrunkList.add(whereStr);

        var value2 = StringUtils.join(sqlTrunkList, " ");
        logger.info(value1);
        logger.info(value2);
        return Tuple3.of(value1, value2, tableName);
    }

    private Tuple3<String, String, String> parseObjSql(CustomQueryRequest cqr) {
        var tableAndWhere = parseSql(cqr);
        var tableList = tableAndWhere.getValue1();
        var whereStr = tableAndWhere.getValue2();

        var value1 = tableList.stream().map(t -> t + ".*").collect(Collectors.joining(", "));
        var tableName = tableList.stream().findFirst().get();

        var sqlTrunkList = new ArrayList<String>();
        // TODO join
        sqlTrunkList.add(tableName);
        sqlTrunkList.add("where 1=1");
        sqlTrunkList.add(whereStr);
        sqlTrunkList.add("order by " + cqr.getSort() + " " + cqr.getOrder());
        sqlTrunkList.add("limit " + cqr.getSize() + " offset " + cqr.getOffset());

        var value2 = StringUtils.join(sqlTrunkList, " ");
        logger.info(value1);
        logger.info(value2);
        return Tuple3.of(value1, value2, tableName);
    }

    private Tuple2<HashSet<String>, String> parseSql(CustomQueryRequest cqr) {
        var conditionTrunkList = new ArrayList<String>();
        var tableList = new HashSet<String>();

        for (var conditionItem : cqr.getConditions()) {
            if (CollectionUtils.isEmpty(conditionItem.getConditionItems())) {
                continue;
            }
            if ("and".equalsIgnoreCase(conditionItem.getConditionType())) {
                List<String> andConditions = new ArrayList<>();
                for (var item : conditionItem.getConditionItems()) {
                    tableList.add(item.getTable());
                    if (StringUtils.contains(item.getOperator(), "in")) {
                        andConditions.add(item.getTable() + "." + item.getFieldName() + item.getOperator() + "('" + StringUtils.join(item.getFieldValue(), "','") + "')");
                    } else if (StringUtils.contains(item.getOperator(), "like")) {
                        andConditions.add(item.getTable() + "." + item.getFieldName() + item.getOperator() + "%" + item.getFieldValue().get(0) + "%");
                    } else {
                        andConditions.add(item.getTable() + "." + item.getFieldName() + item.getOperator() + "'" + item.getFieldValue().get(0) + "'");
                    }
                }
                conditionTrunkList.add("and " + "(" + StringUtils.join(andConditions, " and ") + ")");
            }

            if ("or".equalsIgnoreCase(conditionItem.getConditionType())) {
                List<String> orConditions = new ArrayList<>();
                for (var item : conditionItem.getConditionItems()) {
                    tableList.add(item.getTable());
                    if (StringUtils.contains(item.getOperator(), "in")) {
                        orConditions.add(item.getTable() + "." + item.getFieldName() + item.getOperator() + "('" + StringUtils.join(item.getFieldValue(), "','") + "')");
                    } else if (StringUtils.contains(item.getOperator(), "like")) {
                        orConditions.add(item.getTable() + "." + item.getFieldName() + item.getOperator() + "%" + item.getFieldValue().get(0) + "%");
                    } else {
                        orConditions.add(item.getTable() + "." + item.getFieldName() + item.getOperator() + "'" + item.getFieldValue().get(0) + "'");
                    }
                }
                conditionTrunkList.add("or " + "(" + StringUtils.join(orConditions, " and ") + ")");
            }
        }

        return new Tuple2<>(tableList, StringUtils.join(conditionTrunkList, " "));
    }

    private void findTableNameAnnotation() {
        Reflections reflections = new Reflections("com.demo.databaseagent.domain");
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(TableName.class);

        // 输出结果
        annotatedClasses.forEach(clazz -> {
            TableName annotation = clazz.getAnnotation(TableName.class);
            TABLE_2_CLASS.put(annotation.value(), clazz);
        });
    }

    private List<ResponseSimpleItem> fineColumnNameAnnotation(String tableName, Object obj) {
        List<ResponseSimpleItem> result = new ArrayList<>();
        var curClass = obj.getClass();
        var allField = curClass.getDeclaredFields();
        for (var field : allField) {
            field.setAccessible(true);
            var columnNameAnnotation = field.getAnnotation(ColumnName.class);
            if (columnNameAnnotation != null) {
                ResponseSimpleItem simpleItem = new ResponseSimpleItem();
                simpleItem.setFieldName(field.getName());
                simpleItem.setFieldCnName(columnNameAnnotation.value());
                simpleItem.setTable(tableName);
                Object value;
                try {
                    value = field.get(obj);
                } catch (IllegalAccessException e) {
                    value = "";
                }
                simpleItem.setFieldValue(List.of(value.toString()));
                result.add(simpleItem);
            }
        }
        return result;
    }

    private List<List<ResponseSimpleItem>> doParseTableEntity(String tableName, List<Map<String, Object>> dataList) {
        var curClass = TABLE_2_CLASS.get(tableName);
        if (curClass == null) {
            return Collections.emptyList();
        }


        List<List<ResponseSimpleItem>> pageResult = new ArrayList<>();
        dataList.forEach(data -> {
            var curObj = JsonUtil.map2Obj(objectMapper, data, curClass);
            pageResult.add(fineColumnNameAnnotation(tableName, curObj));
        });

        return pageResult;
    }
}
