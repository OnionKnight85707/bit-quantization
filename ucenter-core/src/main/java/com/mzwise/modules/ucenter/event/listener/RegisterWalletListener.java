package com.mzwise.modules.ucenter.event.listener;

import com.mzwise.modules.ucenter.event.RegisteEvent;
import com.mzwise.modules.ucenter.service.UcStatisticsService;
import com.mzwise.modules.ucenter.service.UcWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author wmf
 * 注册事件监听者(初始化钱包)
 */
@Component
public class RegisterWalletListener implements ApplicationListener<RegisteEvent> {

    @Autowired
    private UcWalletService walletService;
    @Autowired
    private UcStatisticsService statisticsService;

    @Override
    public void onApplicationEvent(RegisteEvent registeEvent) {
        Long memberId = registeEvent.getMemberId();
        // 初始化钱包
        walletService.initWallets(memberId);
        // 统计：增加注册用户数
        statisticsService.addRegisterNum();
    }
}
