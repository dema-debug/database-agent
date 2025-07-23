package com.demo.databaseagent.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateTimeToDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        String year = node.get("year").asText();
        String month = node.get("monthValue").asInt() < 10 ? "0" + node.get("monthValue").asText() : node.get("monthValue").asText();
        String day = node.get("dayOfMonth").asInt() < 10 ? "0" + node.get("dayOfMonth").asText() : node.get("dayOfMonth").asText();
        String hour = node.get("hour").asInt() < 10 ? "0" + node.get("hour").asText() : node.get("hour").asText();
        String minute = node.get("minute").asInt() < 10 ? "0" + node.get("minute").asText() : node.get("minute").asText();
        String second = node.get("second").asInt() < 10 ? "0" + node.get("second").asText() : node.get("second").asText();
        String dateStr = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;

        String timezone = node.has("timezone") ? node.get("timezone").asText() : "UTC";

        // 解析为 LocalDateTime（假设日期字符串是 "yyyy-MM-dd hh:mm:ss"）
        LocalDateTime localDateTime = LocalDateTime.parse(
                dateStr,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );

        // 转换为 Date（使用指定的时区）
        return Date.from(localDateTime.atZone(ZoneId.of(timezone)).toInstant());
    }
}