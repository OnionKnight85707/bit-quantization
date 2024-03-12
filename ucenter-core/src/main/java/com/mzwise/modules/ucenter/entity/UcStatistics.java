package com.mzwise.modules.ucenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 666
 * @since 2022-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_statistics")
@ApiModel(value="UcStatistics对象", description="")
public class UcStatistics implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "发生日期")
    private Date recordDate;

    @ApiModelProperty(value = "注册用户数")
    private Integer registerNum;

    @ApiModelProperty(value = "网络充币数量")
    private BigDecimal rechargeOnline;

    @ApiModelProperty(value = "后台充币数量")
    private BigDecimal rechargeBackstage;

    @ApiModelProperty(value = "提币(成功)数量")
    private BigDecimal withdrawSuccess;

    @ApiModelProperty(value = "用户合约收益")
    private BigDecimal userSwapProfit;

    @ApiModelProperty(value = "公司收益")
    private BigDecimal companyProfit;

    @ApiModelProperty(value = "合伙人佣金")
    private BigDecimal partnerCommission;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
