package com.mzwise.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 解决单元测试passwordEncoder循环依赖问题
 *
 * @Author piao
 * @Date 2021/05/31
 */
@Order(1)
@Configuration
public class PasswprdEncoderConfig {
    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
