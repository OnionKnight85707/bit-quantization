package com.mzwise.modules.ucenter.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户收费响应
 * @Author LiangZaiChao
 * @Date 2022/7/28 14:45
 */
@Data
public class UserChargesResp implements Serializable {

    // 平台服务费是否成功收取
    boolean serviceFeeIsSuccess;

    // 扣除奖励金额数目
    private BigDecimal deductionAward;

    // 实际扣除余额
    private BigDecimal actualDeductionBalance;

    // 实际应计算佣金收益
    private BigDecimal actualCalcCommissionProfit;

}
