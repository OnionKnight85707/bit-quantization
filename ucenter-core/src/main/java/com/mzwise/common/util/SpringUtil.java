package com.mzwise.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * Spring工具类
 * Created by admin on 2020/3/3.
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    // 获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    // 通过name获取Bean
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // 通过class获取Bean
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    // 通过接口获取一个实现Bean
    public static <T> T getBeanOfType(Class<T> clazz) {
        try {
            Map<String, T> beansOfType = getApplicationContext().getBeansOfType(clazz);
            Optional<Map.Entry<String, T>> any = beansOfType.entrySet().stream().findFirst();
            Map.Entry<String, T> anyEntry = any.get();
            return anyEntry.getValue();
        } catch (Exception e) {
            return null;
        }
    }
}
