package com.example.xxumt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 导出类表格字段名
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/12/19 15:12
 * @since 1.0
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelName {

    String name() default "";
}
