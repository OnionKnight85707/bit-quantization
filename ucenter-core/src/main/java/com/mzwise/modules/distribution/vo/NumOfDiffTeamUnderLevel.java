package com.mzwise.modules.distribution.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author piao
 * @Date 2021/05/20
 */
@Data
@ApiModel("某等级下 某个团队的数量")
@AllArgsConstructor
public class NumOfDiffTeamUnderLevel {
    @ApiModelProperty("自己及团队下 1级人员的数量")
    private Integer numOfLevel1;

    @ApiModelProperty("自己及团队下 2级人员的数量")
    private Integer numOfLevel2;

    @ApiModelProperty("自己及团队下 3级人员的数量")
    private Integer numOfLevel3;

    @ApiModelProperty("自己及团队下 4级人员的数量")
    private Integer numOfLevel4;

    @ApiModelProperty("自己及团队下 5级人员的数量")
    private Integer numOfLevel5;

    @ApiModelProperty("自己及团队下 6级人员的数量")
    private Integer numOfLevel6;
}
