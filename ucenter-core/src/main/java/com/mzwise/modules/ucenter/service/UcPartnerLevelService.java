package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.entity.UcPartnerLevel;
import com.mzwise.modules.ucenter.vo.PartnerCommissionCalcVo;

import java.math.BigDecimal;

/**
 * 合伙人等级服务
 * @author 666
 * @since 2022-08-20
 */
public interface UcPartnerLevelService extends IService<UcPartnerLevel> {

    /**
     * 获取用户的合伙人佣金比例
     * @param memberId 用户id
     * @param businessDirectParentId 成交者直属上级id
     * @param lastSetPartnerCommissionRate 上一级设置的合伙人佣金比例(member表设置的)
     * @return
     */
    PartnerCommissionCalcVo getPartnerCommissionRate(Long memberId, Long businessDirectParentId, BigDecimal lastSetPartnerCommissionRate);

}
