package com.mzwise.starter;

import com.mzwise.out.SendSignalService;
import com.mzwise.sync.kline.MongoMarketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 启动并订阅指标分析
 */
@Slf4j
@Component
public class RabbitTestStarter implements ApplicationRunner {

    @Autowired
    private SendSignalService sendSignalService;

    @Override
    @Async
    public void run(ApplicationArguments args) {
//        log.info("=================== 开始rabbitMq测试 ===================");
//        for (int i=0;i<100;i++) {
//            sendSignalService.send("哈哈哈"+i);
//        }
    }
}
