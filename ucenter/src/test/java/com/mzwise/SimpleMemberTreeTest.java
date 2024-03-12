package com.mzwise;

import com.mzwise.modules.distribution.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author piao
 * @Date 2021/05/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleMemberTreeTest {
    @Autowired
    private DistributionService distributionService;
    @Autowired
    private CommunityAwardService communityAwardService;
    @Autowired
    private ShareService shareService;
    @Autowired
    private DividendsService dividendsService;
    @Autowired
    private BTECommunityAwardService bteCommunityAwardService;

    @Test
    public void test() {
//        communityAwardService.addCommunityAward(85L, BigDecimal.valueOf(100));
//        shareService.addShareAmount(85L, BigDecimal.valueOf(100));
//        dividendsService.addDividendsProfit(85L, BigDecimal.valueOf(100));
//        bteCommunityAwardService.addBTECommunityAward();
        distributionService.updateLevel(85L);
    }


}
