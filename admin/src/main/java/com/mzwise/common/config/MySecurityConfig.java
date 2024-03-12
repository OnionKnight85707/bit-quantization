package com.mzwise.common.config;

import com.mzwise.modules.admin.entity.UmsResource;
import com.mzwise.modules.admin.service.UmsAdminService;
import com.mzwise.modules.admin.service.UmsResourceService;
import com.mzwise.security.component.DynamicSecurityService;
import com.mzwise.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
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
    private UmsAdminService umsAdminService;

    @Autowired
    private UmsResourceService resourceService;
    /**
     *
     * @return
     */
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> umsAdminService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return () -> {
            Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
            List<UmsResource> resourceList = resourceService.list();
            for (UmsResource resource : resourceList) {
                map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
            }
            return map;
        };
    }
}
