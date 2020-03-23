package com.luying.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create by luying
 * 2020-03-23
 * 路由
 */


@Target(ElementType.TYPE)           //作用域 接口、类、枚举、注解
@Retention(RetentionPolicy.CLASS)   //编译期
public @interface LRouter {

    String path();              //路由

    String group() default"";
}
