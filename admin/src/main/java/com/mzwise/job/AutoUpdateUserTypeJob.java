package com.mzwise.job;

import com.mzwise.modules.quant.entity.QuantApiAccess;
import com.mzwise.modules.quant.service.QuantApiAccessService;
import com.mzwise.modules.quant.service.QuantStrategyService;
import com.mzwise.modules.ucenter.mapper.UcMemberMapper;
import com.mzwise.modules.ucenter.service.UcMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自动更新用户类别
 */
@Component
@Slf4j
public class AutoUpdateUserTypeJob {

    @Autowired
    private QuantStrategyService quantStrategyService;
    @Autowired
    private QuantApiAccessService quantApiAccessService;

    /**
     * 获取全部用户ID，根据实际仓位自动更新用户类别
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void resetUserType() throws InterruptedException {
        List<QuantApiAccess> apiList = quantApiAccessService.list();
        for (QuantApiAccess api : apiList) {
            log.info("自动更新用户类别：当前正在更新用户：" + api.getMemberId());
            quantStrategyService.autoUpdateUserType(api.getMemberId(), api.getPlatform());
            Thread.sleep(500);
        }
    }

}
