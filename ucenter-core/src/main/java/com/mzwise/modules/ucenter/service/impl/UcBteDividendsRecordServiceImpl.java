package com.mzwise.modules.ucenter.service.impl;

import com.mzwise.constant.UnitEnum;
import com.mzwise.modules.ucenter.entity.UcBteDividendsRecord;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.mapper.UcBteDividendsRecordMapper;
import com.mzwise.modules.ucenter.service.UcBteDividendsRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.ucenter.service.UcMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-05-26
 */
@Service
public class UcBteDividendsRecordServiceImpl extends ServiceImpl<UcBteDividendsRecordMapper, UcBteDividendsRecord> implements UcBteDividendsRecordService {
    @Autowired
    private UcMemberService memberService;

    @Override
    public void create(Long memberId, BigDecimal amount) {
        UcBteDividendsRecord bteDividendsRecord = new UcBteDividendsRecord();
        bteDividendsRecord.setMemberId(memberId);
        UcMember member = memberService.getById(memberId);
        if (member == null) {
            return;
        }
        bteDividendsRecord.setMemberNickname(member.getNickname());
        bteDividendsRecord.setAmount(amount);
        bteDividendsRecord.setUnit(UnitEnum.BTE);
        save(bteDividendsRecord);
    }
}
