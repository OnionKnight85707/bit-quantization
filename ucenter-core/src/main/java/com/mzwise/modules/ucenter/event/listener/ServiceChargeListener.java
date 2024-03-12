package com.mzwise.modules.ucenter.event.listener;

import com.mzwise.modules.distribution.service.CommunityAwardService;
import com.mzwise.modules.distribution.service.DistributionService;
import com.mzwise.modules.distribution.service.DividendsService;
import com.mzwise.modules.distribution.service.ShareService;
import com.mzwise.modules.ucenter.event.ServiceChargeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/05/26
 */
@Slf4j
@Component
public class ServiceChargeListener implements ApplicationListener<ServiceChargeEvent> {
    @Autowired
    private DividendsService dividendsService;
    @Autowired
    private DistributionService distributionService;
    @Autowired
    private ShareService shareService;
    @Autowired
    private CommunityAwardService communityAwardService;

    @Override
    public void onApplicationEvent(ServiceChargeEvent serviceChargeEvent) {
        log.info("===============已进入方法！================");
        Long memberId = serviceChargeEvent.getMemberId();
        BigDecimal amount = serviceChargeEvent.getAmount();
        // 更新是否有效
        distributionService.updateIsEffective(memberId);
        // 更新等级
        distributionService.updateLevel(memberId);
        // 增加分享收益
        shareService.addShareAmount(memberId, amount);
        // 增加股东、懂事分红收益
        dividendsService.addDividendsProfit(memberId, amount);
        // 增加社区奖
        communityAwardService.addCommunityAward(memberId, amount);
    }

}
