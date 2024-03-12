package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcServiceChargeRecord;
import com.mzwise.modules.ucenter.mapper.UcServiceChargeRecordMapper;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.service.UcServiceChargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-05-24
 */
@Service
public class UcServiceChargeRecordServiceImpl extends ServiceImpl<UcServiceChargeRecordMapper, UcServiceChargeRecord> implements UcServiceChargeRecordService {
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UcServiceChargeRecordMapper serviceChargeRecordMapper;

    @Override
    public void create(Long memberId, BigDecimal amount) {
        UcServiceChargeRecord record = new UcServiceChargeRecord();
        UcMember member = memberService.getById(memberId);
        if (member == null) {
            return;
        }
        String nickname = member.getNickname();
        Long pid = member.getParentId();
        String parentNickname = member.getParentNickname();
        record.setMemberId(memberId);
        record.setAmount(amount);
        record.setMemberNickname(nickname);
        record.setSupMemberId(pid);
        record.setSupMemberNickname(parentNickname);
        save(record);
    }

    @Override
    public BigDecimal sumServiceChargeLastWeek() {
        return serviceChargeRecordMapper.sumServiceChargeLastWeek();
    }
}
