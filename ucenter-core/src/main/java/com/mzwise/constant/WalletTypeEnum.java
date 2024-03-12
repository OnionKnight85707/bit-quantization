package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Administrator
 * @Date 2021/01/14
 */
@AllArgsConstructor
@Getter
@ApiModel("钱包类型")
public enum WalletTypeEnum implements IEnum<Integer> {
    //    账户类型 QUANT:资产账户(USDT) MINING:资产账户(FIL) PLATFORM:平台账户(BTE) QUANT_COMMUNITY:量化社区  QUANT_SERVICE:量化服务费 MINING_COMMUNITY:矿机社区 MINING_PROFIT:矿机收益 MINING_PROFIT:矿机收益 PLATFORM_SHARE:平台分红
    @ApiModelProperty("量化账户")
    QUANT(1, "QUANT"),

    @ApiModelProperty("矿机账户")
    MINING(2, "MINING"),

    @ApiModelProperty("平台币账户")
    PLATFORM(3, "PLATFORM"),

    @ApiModelProperty("量化社区账户")
    QUANT_COMMUNITY(4, "QUANT_COMMUNITY"),

    @ApiModelProperty("量化服务费账户")
    QUANT_SERVICE(5, "QUANT_SERVICE"),

    @ApiModelProperty("矿机社区账户")
    MINING_COMMUNITY(6, "MINING_COMMUNITY"),

    @ApiModelProperty("矿机收益账户")
    MINING_PROFIT(7, "MINING_PROFIT"),

    @ApiModelProperty("平台币分红账户")
    PLATFORM_SHARE(8, "PLATFORM_SHARE");

    private final Integer value;
    private final String name;
}


