package com.mzwise.job;

import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcPartnerStat;
import com.mzwise.modules.ucenter.entity.UcProfit;
import com.mzwise.modules.ucenter.service.*;
import com.mzwise.modules.ucenter.vo.DailyEarningsStatisticsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 每日收益统计任务
 * @Author LiangZaiChao
 * @Date 2022/7/5 14:20
 */
@Component
@Slf4j
public class DailyEarningsStatisticsJob {

    @Autowired
    private UcProfitDetailService profitDetailService;
    @Autowired
    private UcProfitService profitService;

    @Autowired
    private UcPartnerStatService ucPartnerStatService;

    @Autowired
    private UcTransactionService ucTransactionService;


    @Autowired
    private UcMemberService ucMemberService;

    // 每天凌晨一点执行一次
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void run() {
        log.info("{} 开始执行每日收益统计任务", LocalDateTime.now());
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<DailyEarningsStatisticsVo> list = profitDetailService.findYesterdayEarningsStatistics();
        if (CollectionUtils.isEmpty(list)) {
            log.info("日期={}, 查询无用户收益", yesterday);
            return;
        }
        List<UcProfit> saveList = new ArrayList<>();
        for (DailyEarningsStatisticsVo temp : list) {
            UcProfit e = new UcProfit();
            e.setMemberId(temp.getMemberId());
            e.setProfit(temp.getProfitSum());
            e.setAmount(temp.getAmountSum());
            e.setProfitDay(yesterday);
            saveList.add(e);
        }
        profitService.saveBatch(saveList);
    }

    // 每天凌晨一点执行一次
    @Scheduled(cron = "0 1 0 * * ?")
    @Transactional
    public void partner() {
        log.info("{} 开始执行每日分润统计任务", LocalDateTime.now());
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate today= LocalDate.now();
        List<UcMember> memberList= ucMemberService.queryAllPartner();

        List<UcPartnerStat> statList=new ArrayList<>();

        for(UcMember member:memberList)
        {
            UcPartnerStat stat = ucTransactionService.findYesterdayCommissionStatisticsByMember(yesterday,today,member.getId().intValue());
            if (stat==null)
            {
                stat=new UcPartnerStat();
                stat.setAmount(BigDecimal.ZERO);
                stat.setMemberId(member.getId());
            }
            stat.setStatDay(yesterday.toString());
            stat.setCreateTime(new Date());
            stat.setType(1);

            statList.add(stat);

        }
        if (statList.size()>0)
             ucPartnerStatService.saveBatch(statList);
    }

}
