package com.mzwise.modules.ucenter.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.mzwise.constant.DistributionProfitTypeEnum;
import com.mzwise.constant.UnitEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author piao
 * @Date 2021/06/21
 */
@Data
@ApiModel("量化分销收益记录")
public class AdminDistributionProfitRecordVO {
    @ApiModelProperty("下级id")
    private Long memberId;

    @ApiModelProperty("分销类型")
    private DistributionProfitTypeEnum type;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "收益人id")
    private Long benefitMemberId;

    @ApiModelProperty(value = "会员昵称")
    private String memberNickname;

    @ApiModelProperty(value = "受益人昵称")
    private String benefitMemberNickname;

    @ApiModelProperty(value = "收益金额")
    private BigDecimal benefitAmount;

    @ApiModelProperty(value = "单位,0:USDT,1:FIL,2:BTE(平台币)")
    private UnitEnum unit;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
