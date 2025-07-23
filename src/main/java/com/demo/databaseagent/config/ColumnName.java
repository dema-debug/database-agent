package com.demo.databaseagent.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**********************************************
 * Copyright(C), EastMoney.
 * All rights reserved
 *
 * @brief

 * @file com.demo.databaseagent.config
 * @author xr
 * @date 2025/7/23
 **********************************************/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ColumnName {
    String value();
}
