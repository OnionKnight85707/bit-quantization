package com.mzwise.modules.ucenter.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RelStrategyParam {

    @ApiModelProperty(value = "用户类别id，uni_user_type的主键")
    private Integer userTypeId;

    @ApiModelProperty(value = "小类策略id，uni_small_strategy表的ID")
    private Integer strategyTypeId;
}
