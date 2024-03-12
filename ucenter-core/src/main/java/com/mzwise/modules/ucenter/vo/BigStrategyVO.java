package com.mzwise.modules.ucenter.vo;


import com.mzwise.modules.ucenter.entity.UniSmallStrategy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BigStrategyVO {

    @ApiModelProperty(value = "大类策略编号")
    private Integer id;

    @ApiModelProperty(value = "大类策略描述")
    private String name;

    @ApiModelProperty(value = "排序，值越小排在最前面，默认1")
    private Integer sort;

    @ApiModelProperty(value = "小类策略列表")
    private List<UniSmallStrategyVO> list;
}
