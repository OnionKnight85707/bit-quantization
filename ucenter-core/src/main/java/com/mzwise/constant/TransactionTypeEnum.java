package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@ApiModel("交易类型")
public enum TransactionTypeEnum implements IEnum<Integer> {

    @ApiModelProperty("保证金")
    PRINCIPLE(0, "PRINCIPLE"),

    @ApiModelProperty("退回保证金")
    RETURN_PRINCIPLE(1, "RETURN_PRINCIPLE"),

    @ApiModelProperty("手续费")
    FEE(2, "FEE"),

    @ApiModelProperty("收益")
    PROFIT(3, "PROFIT"),

    @ApiModelProperty("划转")
    TRANSFER(4, "TRANSFER"),

    @ApiModelProperty("兑换")
    EXCHANGE(5, "EXCHANGE"),

    @ApiModelProperty("锁仓服务费")
    FROZEN_QUANT_SERVICE(6, "FROZEN_QUANT_SERVICE"),

    @ApiModelProperty("解冻服务费")
    UN_FROZEN_QUANT_SERVICE(7, "UN_FROZEN_QUANT_SERVICE"),

    @ApiModelProperty("入金")
    RECHARGE(8, "RECHARGE"),

    @ApiModelProperty("出金")
    WITHDRAWAL(9, "WITHDRAWAL"),

    @ApiModelProperty("出金审核失败退款")
    WITHDRAWAL_FAILED_REFUND(10, "WITHDRAWAL_FAILED_REFUND"),

    @ApiModelProperty("管理员充值")
    ADMIN_DEPOSIT(11, "ADMIN_DEPOSIT"),

    @ApiModelProperty("管理员扣减")
    ADMIN_WITHDRAWAL(12, "ADMIN_WITHDRAWAL"),

    @ApiModelProperty("分享收益")
    SHARE_PROFIT(13, "SHARE_PROFIT"),

    @ApiModelProperty("社区奖励")
    COMMUNITY_AWARD(14, "SHARE_PROFIT"),

    @ApiModelProperty("股东/董事 分红")
    DIVIDENDS(15, "DIVIDENDS"),

    @ApiModelProperty("社区量化分红")
    QUANTITATIVE_PLATFORM_CURRENCY_DIVIDENDS(16, "QUANTITATIVE_PLATFORM_CURRENCY_DIVIDENDS"),

    @ApiModelProperty("扫码转账")
    SCAN_CODE_TRANSFER(17, "SCAN_CODE_TRANSFER"),

    @ApiModelProperty("收益服务费")
    CHARGE_QUANT_SERVICE(18, "CHARGE_QUANT_SERVICE"),

    @ApiModelProperty("合伙人返佣")
    PARTNER_COMMISSION(19, "PARTNER_COMMISSION"),

    @ApiModelProperty("注册奖励")
    REGISTER_AWARD(20, "REGISTER_AWARD");


    private final Integer value;
    private final String name;
}
