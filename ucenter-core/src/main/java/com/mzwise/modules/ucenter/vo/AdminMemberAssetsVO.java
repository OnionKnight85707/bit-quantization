package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/05/31
 */
@Data
@ApiModel("会员详情——资产")
public class AdminMemberAssetsVO {
    @ApiModelProperty("量化账户资产")
    private BigDecimal quantifyAssets;

    @ApiModelProperty("平台币账户资产")
    private BigDecimal bteAssets;

    @ApiModelProperty("量化社区账户资产")
    private BigDecimal quantifyCommunityAssets;

    @ApiModelProperty("平台币分红账户资产")
    private BigDecimal bteDividendAssets;

    @ApiModelProperty("服务费账户资产")
    private BigDecimal serviceFeeAssets;

    @ApiModelProperty("折合总资产(USDT)")
    private BigDecimal convertedToUSDTAssets;

    @ApiModelProperty("折合总资产(CNY)")
    private BigDecimal convertedToCNYAssets;
}
