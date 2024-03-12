package com.mzwise;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.profit.CommonRabbitmqConfig;
import com.profit.UserProfitRabbitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableDubboConfiguration
@Import({UserProfitRabbitConfig.class, CommonRabbitmqConfig.class})
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
        System.out.println("==================================启动成功==================================");
    }
}
