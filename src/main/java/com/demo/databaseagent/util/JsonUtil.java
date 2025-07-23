package com.demo.databaseagent.util;

import com.demo.databaseagent.exception.BaseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Objects;

/**
 * @Description
 * @Author xr
 * @Date 2025/7/23 10:53
 */
public class JsonUtil {

    public static String writeValueAsString(ObjectMapper objectMapper, Object object) {
        try {
            if (Objects.isNull(object)) {
                return null;
            }
            if (object instanceof String str) {
                return str;
            }
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException jpe) {
            return null;
        }
    }

    /**
     * 将json形式的字符串数据转换成单个对象
     *
     * @param objectMapper objectMapper
     * @param str          string
     * @param clazz        对象名
     * @return T
     */
    @SuppressWarnings(value = "unchecked")
    public static <T> T string2Obj(ObjectMapper objectMapper, String str, Class<T> clazz) {
        if (str == null || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T string2Obj(ObjectMapper objectMapper, String str, TypeReference<T> typeReference) {
        if (str == null || typeReference == null) {
            return null;
        }
        try {
            return objectMapper.readValue(str, typeReference);
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, Object> obj2Map(ObjectMapper objectMapper, Object src) {
        if (src == null) {
            return null;
        }
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(src), new TypeReference<>() {
            });
        } catch (Exception ignored) {
        }
        return null;
    }

    public static <T> T map2Obj(ObjectMapper objectMapper, Object src, Class<T> clazz) {
        if (src == null) {
            return null;
        }
        try {
            return objectMapper.convertValue(src, clazz);
        } catch (BaseException ignored) {
        }
        return null;
    }
}
