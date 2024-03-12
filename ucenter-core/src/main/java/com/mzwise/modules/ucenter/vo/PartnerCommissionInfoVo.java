package com.mzwise.modules.ucenter.vo;

import com.mzwise.constant.PartnerLevelEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 合伙人佣金详情
 * @Author LiangZaiChao
 * @Date 2022/8/20 16:24
 */
@Data
public class PartnerCommissionInfoVo {

    // 上级用户(推荐人)id
    private Long parentId;

    // 是否合伙人
    private Boolean isPartner;

    // member表设置的合伙人佣金比例
    private BigDecimal setRate;

    // 合伙人等级id
    private Integer levelId;

    // 合伙人等级
    private PartnerLevelEnum level;

    // 合伙人等级佣金比例
    private BigDecimal levelRate;

}
