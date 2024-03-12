package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.constant.DistributionProfitTypeEnum;
import com.mzwise.modules.ucenter.entity.UcDistributionProfit;
import com.mzwise.modules.ucenter.vo.QuantifiedCommissionVO;

import java.math.BigDecimal;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author admin
 * @since 2021-05-25
 */
public interface UcDistributionProfitService extends IService<UcDistributionProfit> {
    /**
     * 生成分享收益记录
     *
     * @param memberId
     * @param benefitMemberId
     * @param amount
     * @param type
     */
    void create(Long memberId, Long benefitMemberId, BigDecimal amount, DistributionProfitTypeEnum type);

    /**
     * 量化佣金
     *
     * @param memberId
     * @return
     */
    QuantifiedCommissionVO showQuantifiedCommission(Long memberId);
}
