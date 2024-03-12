package com.mzwise.modules.ucenter.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 合伙人佣金计算
 * @Author LiangZaiChao
 * @Date 2022/8/20 16:51
 */
@Data
public class PartnerCommissionCalcVo {

    // 上级用户(推荐人)id
    private Long parentId;

    // member表设置的合伙人佣金比例
    private BigDecimal setRate;

    // 真正参与计算的合伙人佣金比例
    private BigDecimal calcRate;

}
