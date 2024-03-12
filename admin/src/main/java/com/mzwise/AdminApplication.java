package com.mzwise;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.profit.CommonRabbitmqConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDubboConfiguration
@Import(CommonRabbitmqConfig.class)
@EnableScheduling
public class AdminApplication {
    public static void main(String[] args) {
        System.setProperty("dubbo.application.logger","slf4j");
        SpringApplication.run(AdminApplication.class, args);
        System.out.println("==================================启动成功==================================");
    }
}

