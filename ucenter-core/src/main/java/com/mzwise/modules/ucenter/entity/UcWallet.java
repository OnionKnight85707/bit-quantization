package com.mzwise.modules.ucenter.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.mzwise.constant.WalletTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2021-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_wallet")
@ApiModel(value="UcWallet对象", description="")
public class UcWallet implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long memberId;

    @ApiModelProperty(value = "账户类型 quant量化账户 mining矿机账户 platform平台账户 quant_community量化社区  quant_service量化服务费 mining_community矿机社区 mining_profit矿机收益 mining_profit矿机收益 platform_share平台分红")
    private WalletTypeEnum type;

    @ApiModelProperty(value = "币种")
    private String symbol;

    @ApiModelProperty(value = "可用余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "奖励金额(优惠券)")
    private BigDecimal ticket;

    @ApiModelProperty(value = "累计奖励金额(优惠券)")
    private BigDecimal totalTicket;

    @ApiModelProperty(value = "冻结金额-服务费")
    private BigDecimal frozen;

    @ApiModelProperty(value = "托管金额")
    private BigDecimal deposit;

    @ApiModelProperty(value = "充值地址")
    private String address;

    @ApiModelProperty(value = "主钱包(可进行充提币)")
    private Boolean master;

    @ApiModelProperty(value = "历史盈利")
    private BigDecimal totalProfit;

    @ApiModelProperty(value = "今日盈利")
    private BigDecimal todayProfit;

    @ApiModelProperty(value = "是否平台")
    private Boolean isPlatform;

}
