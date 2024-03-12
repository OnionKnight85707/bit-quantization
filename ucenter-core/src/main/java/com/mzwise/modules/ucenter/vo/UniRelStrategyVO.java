package com.mzwise.modules.ucenter.vo;

import com.mzwise.modules.ucenter.entity.UniSmallStrategy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UniRelStrategyVO {
    @ApiModelProperty(value = "编号")
    private Integer id;

    @ApiModelProperty(value = "用户类别id，uni_user_type的主键")
    private Integer userTypeId;

//    @ApiModelProperty(value = "小类策略id，uni_small_strategy表的ID")
//    private Integer strategyTypeId;

    @ApiModelProperty(value = "小类策略列表")
    private List<UniSmallStrategy> list;

}
