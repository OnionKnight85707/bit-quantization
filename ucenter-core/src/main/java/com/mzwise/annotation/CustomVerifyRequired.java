package com.mzwise.annotation;

import java.lang.annotation.*;

/**
 * 用户自定义验证切面注解
 * @author wmf
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CustomVerifyRequired {}
