package com.mzwise.modules.ucenter.vo;

import com.mzwise.common.dto.IdAndNameVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户类型和策略响应
 * @Author LiangZaiChao
 * @Date 2022/8/3 18:00
 */
@Data
public class UserTypeStrategyVo implements Serializable {

    @ApiModelProperty(value = "用户类型id")
    private Integer id;

    @ApiModelProperty(value = "用户类型名称")
    private String name;

    @ApiModelProperty(value = "小类策略列表")
    List<IdAndNameVo> smallStrategyList;

}
