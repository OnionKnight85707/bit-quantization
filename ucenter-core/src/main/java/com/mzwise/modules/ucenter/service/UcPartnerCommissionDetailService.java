package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.entity.UcPartnerCommissionDetail;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 合伙人佣金明细接口
 * @Author: LiangZaiChao
 * @Date: 2022/6/10 14:54
 */
public interface UcPartnerCommissionDetailService extends IService<UcPartnerCommissionDetail> {

    /**
     * 新增合伙人佣金明细
     * @param memberId
     * @param orderId
     * @param commission
     * @param createTime
     */
    void addPartnerCommissionDetail(Long memberId, Long orderId, BigDecimal commission, LocalDateTime createTime);

}
