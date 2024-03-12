package com.mzwise.modules.ucenter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 保存用户类型和策略关系请求
 * @Author LiangZaiChao
 * @Date 2022/8/3 21:31
 */
@Data
public class SaveRelStrategyParam implements Serializable {

    private static final long serialVersionUID = -1093674177875902824L;

    @ApiModelProperty(value = "用户类型")
    @NotNull
    private Integer userTypeId;

    @ApiModelProperty(value = "小策略id")
    private List<Integer> smallStrategyIds;

}
