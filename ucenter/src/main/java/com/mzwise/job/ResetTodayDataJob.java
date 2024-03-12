package com.mzwise.job;

import com.mzwise.common.util.WalletProfitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 重置今日数据，每日0点
 */
@Component
@Slf4j
public class ResetTodayDataJob {

    /**
     * 重置存储今日数据的字段
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetTodayData(){
        try {
            log.info("开始处理清空钱包今日收益");
            WalletProfitUtil.resetTodayProfit();
        } catch (Exception e) {
            log.error("清空数据出现错误");
            e.printStackTrace();
        }

    }
}
