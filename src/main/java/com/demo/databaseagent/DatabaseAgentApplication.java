package com.demo.databaseagent;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DatabaseAgentApplication {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseAgentApplication.class);

    public static void main(String[] args) {
        logger.info("DatabaseAgentApplication start...");
        SpringApplication.run(DatabaseAgentApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES) // 忽略大小写
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) // 未知字段不报错
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS) // 允许空对象序列化
                .disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS) // 时间不变成时间戳
                .disable(MapperFeature.REQUIRE_HANDLERS_FOR_JAVA8_TIMES)
                .build();
    }
}
