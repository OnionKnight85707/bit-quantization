package com.mzwise.job;

import com.mzwise.common.provider.EmailProvider;
import com.mzwise.common.provider.support.JavaEmailProvider;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.vo.CheckServiceChargeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CheckServiceChargeJob {

    @Autowired
    private  EmailProvider emailProvider;
    @Autowired
    private  UcMemberService memberService;


    @Scheduled(cron = "0 0 8 * * ?")
    @Transactional
    public void check1() throws MessagingException, InterruptedException {

        log.info("{} 检查用户服务费余额", LocalDate.now());
        LocalDate localDate = LocalDate.now();
        List<CheckServiceChargeVO> list = memberService.checkServiceCharge();
        if (CollectionUtils.isEmpty(list)) {
            log.info("日期={},无用户服务费余额不足", localDate);
            return;
        }
        for (CheckServiceChargeVO temp:list){
            String email = temp.getEmail();
            Integer times = temp.getReminderTimes();
            temp.setReminderTimes(++times);
            UcMember ucMember = memberService.getMemberById(temp.getId());
            ucMember.setReminderTimes(temp.getReminderTimes());
            memberService.updateById(ucMember);
            emailProvider.sendRemindTheBalanceIsInsufficient(email,ucMember.getAreaCode());
        }
    }


}
