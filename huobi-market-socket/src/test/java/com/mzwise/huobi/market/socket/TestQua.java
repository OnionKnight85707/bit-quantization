package com.mzwise.huobi.market.socket;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class TestQua {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 1、创建调度器Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        JobDetail jobDetail = JobBuilder.newJob(PrintWordsJob.class)
                .withIdentity("kline-sync", "kline-sync-group").build();
        // 3、构建Trigger实例,整分钟执行
        Trigger trigger = TriggerBuilder.newTrigger()
                .startNow()//立即生效
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0 * * * *  ?"))
                .build();

        //4、执行
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();


    }
}
