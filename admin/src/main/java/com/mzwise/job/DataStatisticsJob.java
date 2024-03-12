package com.mzwise.job;

import com.mzwise.modules.ucenter.service.UcStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class DataStatisticsJob {

    @Autowired
    private UcStatisticsService ucStatisticsService;

    // 每天0点执行一次
    @Scheduled(cron = "0 0 0 * * ?")
    public void run(){
        try {
            log.info("{} 每日统计数据初始化", LocalDate.now());
            ucStatisticsService.resetEverydayDataStatistics();
        }catch (Exception e){
            log.error("数据初始化失败");
            e.printStackTrace();
        }
    }
}
