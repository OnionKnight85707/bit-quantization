package com.mzwise.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * MyBatis配置类
 * Created by admin on 2019/4/8.
 */
@Configuration
@EnableTransactionManagement
@Slf4j
@MapperScan({"com.mzwise.modules.*.mapper"})
public class MyBatisConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    @PostConstruct
    public void init(){
        log.info("设置时区 为上海");
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }
}
