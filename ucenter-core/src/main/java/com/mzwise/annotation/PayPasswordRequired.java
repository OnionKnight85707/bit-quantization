package com.mzwise.annotation;

import java.lang.annotation.*;

/**
 * 交易密码切面注解
 * @author wmf
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PayPasswordRequired {}
