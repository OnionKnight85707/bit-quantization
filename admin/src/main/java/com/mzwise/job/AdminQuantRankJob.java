package com.mzwise.job;

import com.mzwise.common.util.GeneratorUtil;
import com.mzwise.modules.quant.service.AdminQuantRankService;
import com.mzwise.modules.quant.service.UcQuantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@Order(-1)
public class AdminQuantRankJob implements ApplicationRunner {

    @Autowired
    private AdminQuantRankService adminQuantRankService;

    @Value("${rank.id.list}")
    private String rankIdList;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] s = rankIdList.split("#");
        Long[] ids = (Long[]) ConvertUtils.convert(s, Long.class);
        List<Long> idList = new ArrayList<>(Arrays.asList(ids));

        while(true){
            for (Long id : idList) {
                log.info("开始自动添加排行榜收益");
                // 随机的收益
                BigDecimal randomProfit = GeneratorUtil.getRandomBigDecimal(1, 20, 4);
                adminQuantRankService.addRankProfit(id, randomProfit, randomProfit);
                Thread.sleep(GeneratorUtil.getRandom(5, 30) * 60 * 1000);
            }
        }

    }

    public static void main(String[] args) {
        for (int i=0; i<1000; i++) {
            System.out.println((int)(Math.random()*(30-5+1)+5));
        }
    }
}
