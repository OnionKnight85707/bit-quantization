package com.mzwise.job;

import com.mzwise.modules.distribution.service.BTECommunityAwardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author piao
 * @Date 2021/05/25
 */
@Component
@Slf4j
public class BTECommunityAwardJob {
    @Autowired
    private BTECommunityAwardService bteCommunityAwardService;

    @Scheduled(cron = "0 0 0 ? * 1")
    public void getBitcoinTurtleCoins() {
        try {
            bteCommunityAwardService.addBTECommunityAward();
        } catch (Exception e) {
            log.error("平台币分红出现错误");
            e.printStackTrace();
        }
    }
}
