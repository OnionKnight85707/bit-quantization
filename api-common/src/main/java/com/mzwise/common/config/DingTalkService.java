package com.mzwise.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class DingTalkService  {

     @Value("${ding.readonly.url}")
     private  String dingUrl;



    public void sendStrategyOpen(int id)
    {
            try {
                DingTalkUtil.sendDingTalk(dingUrl,"用户开启了 只读信号策略 ,memberId="+id);
            } catch (IOException e) {
                log.error("发送ding talk 失败 ！");
                e.printStackTrace();
            }
    }

    public void sendStrategyActive(int id)
    {
        try {
            DingTalkUtil.sendDingTalk(dingUrl,"用户重新激活了 只读信号策略 ,策略id="+id);
        } catch (IOException e) {
            log.error("发送ding talk 失败 ！");
            e.printStackTrace();
        }
    }

    public void sendStrategySettle(int id)
    {
        try {
            DingTalkUtil.sendDingTalk(dingUrl,"只读信号策略 正在执行冻结结算! ,策略id="+id);
        } catch (IOException e) {
            log.error("发送ding talk 失败 ！");
            e.printStackTrace();
        }

    }

    public void sendStrategyStop(int id)
    {
        try {
            DingTalkUtil.sendDingTalk(dingUrl,"只读信号策略 用户执行了策略停止 ,策略id="+id);
        } catch (IOException e) {
            log.error("发送ding talk 失败 ！");
            e.printStackTrace();
        }
    }

    public void sendStrategyPause(int id)
    {
        try {
            DingTalkUtil.sendDingTalk(dingUrl,"只读信号策略 用户执行了策略暂停 ,策略id="+id);
        } catch (IOException e) {
            log.error("发送ding talk 失败 ！");
            e.printStackTrace();
        }
    }


}
