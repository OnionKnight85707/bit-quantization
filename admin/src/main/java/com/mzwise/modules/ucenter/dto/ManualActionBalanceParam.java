package com.mzwise.modules.ucenter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 手动操作余额
 * @Author LiangZaiChao
 * @Date 2022/7/11 15:32
 */
@Data
public class ManualActionBalanceParam implements Serializable {

    @ApiModelProperty(value = "用户id")
    @NotNull
    private Long memberId;

    @ApiModelProperty(value = "金额")
    @NotNull
    private BigDecimal amount;

}
