package com.mzwise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@EnableScheduling
@Slf4j
public class CronCheck {

     @Value("${ding.url}")
     private  String dingUrl;

    @Value("${ucenter.url}")
    private  String ucenterUrl;

    @Scheduled(cron = "0 */5 * * * ?")
    public void checkPing()
    {

        String ret=DingTalkUtil.doGet(ucenterUrl,null);

        if (ret==null)
        {
            log.error(" ucenter ping不通 ");
            try {
                DingTalkUtil.sendDingTalk(dingUrl,"系统ucenter接口不通，请检查是否在发版！");
            } catch (IOException e) {
                log.error("发送ding talk 失败 ！");
                e.printStackTrace();
            }
        }




    }


}
