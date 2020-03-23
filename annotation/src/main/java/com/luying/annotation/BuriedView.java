package com.luying.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create by luying
 * 2020-03-23
 * 需要被埋点的字段
 */

@Target(ElementType.FIELD)          //作用域，字段
@Retention(RetentionPolicy.CLASS)   //编译器
public @interface BuriedView {
}
