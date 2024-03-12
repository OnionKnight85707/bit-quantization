package com.mzwise.common.config;

import com.mzwise.common.config.BaseSwaggerConfig;
import com.mzwise.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 * Created by admin on 2018/4/26.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.mzwise")
                .title("cloud-主项目骨架")
                .description("cloud-主项目骨架相关接口文档")
                .contactName("admin")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
