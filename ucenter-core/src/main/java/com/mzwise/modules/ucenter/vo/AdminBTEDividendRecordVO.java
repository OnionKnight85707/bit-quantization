package com.mzwise.modules.ucenter.vo;

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
@ApiModel("平台币分红")
public class AdminBTEDividendRecordVO {
    @ApiModelProperty("下级id")
    private Long memberId;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "平台币收益金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "金钱单位")
    private UnitEnum unit;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
