package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.ucenter.entity.UcPartnerCommissionDetail;
import com.mzwise.modules.ucenter.mapper.UcPartnerCommissionDetailMapper;
import com.mzwise.modules.ucenter.service.UcPartnerCommissionDetailService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 合伙人佣金明细实现
 * @Author: LiangZaiChao
 * @Date: 2022/6/10 14:56
 */
@Service
public class UcPartnerCommissionDetailServiceImpl extends ServiceImpl<UcPartnerCommissionDetailMapper, UcPartnerCommissionDetail> implements UcPartnerCommissionDetailService {

    /**
     * 新增合伙人佣金明细
     * @param memberId
     * @param orderId
     * @param commission
     * @param createTime
     */
    @Override
    public void addPartnerCommissionDetail(Long memberId, Long orderId, BigDecimal commission, LocalDateTime createTime) {
        UcPartnerCommissionDetail detail = new UcPartnerCommissionDetail();
        detail.setMemberId(memberId);
        detail.setQuantOrderId(orderId);
        detail.setCommission(commission);
        detail.setCreateTime(createTime);
        save(detail);
    }

}
