package com.mzwise.huobi.market.socket.core.kline;

import com.mzwise.huobi.market.socket.core.HuobiBaseHandler;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.LoggerFactory;

public class KlineSyncWorker {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(KlineSyncWorker.class);

    public KlineSyncWorker(HuobiBaseHandler handler) {
        KlineSyncJob.handler = handler;
    }

    public void run() throws SchedulerException {
        // 1、创建调度器Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        JobDetail jobDetail = JobBuilder.newJob(KlineSyncJob.class)
                .withIdentity("kline-sync", "kline-sync-group").build();
        // 3、构建Trigger实例,整分钟执行
        Trigger trigger = TriggerBuilder.newTrigger()
                .startNow()//立即生效
                .withSchedule(//todo 留出10秒避免火币k线未完全形成
                        CronScheduleBuilder.cronSchedule("10 * * * *  ?"))
                .build();

        //4、执行
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}
