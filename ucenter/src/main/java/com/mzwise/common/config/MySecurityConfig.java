package com.mzwise.common.config;

import com.mzwise.modules.ucenter.service.UcenterMemberService;
import com.mzwise.security.component.DynamicSecurityService;
import com.mzwise.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mall-security模块相关配置
 * Created by admin on 2019/11/9.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends SecurityConfig {
    @Autowired
    private UcenterMemberService memberService;

    /**
     * @return
     */
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> memberService.loadUserById(username);
    }


    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return () -> {
            Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
            return map;
        };
    }
}
