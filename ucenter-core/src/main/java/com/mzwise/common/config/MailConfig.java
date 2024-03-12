package com.mzwise.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @Author piao
 * @Date 2021/04/28
 */
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class MailConfig extends JavaMailSenderImpl {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private Properties properties;
    private String defaultEncoding;
}
