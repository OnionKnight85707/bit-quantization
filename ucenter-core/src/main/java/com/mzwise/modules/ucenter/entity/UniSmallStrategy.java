package com.mzwise.modules.ucenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mzwise.constant.PlatformEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 小类策略表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uni_small_strategy")
@ApiModel(value="UniSmallStrategy对象", description="")
public class UniSmallStrategy implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "小类策略描述，比如macd策略")
    private String name;

    @ApiModelProperty(value = "大类策略id，表uni_big_strategy中的id")
    private Integer bigStrategyId;

    @ApiModelProperty(value = "支持的币种列表，格式用逗号分开，全部大写，BTC/USDT")
    private String symbolList;

    @ApiModelProperty(value = "风险等级，比如 稳健")
    private String risk;

    @ApiModelProperty(value = "参数模块ID，表uni_template 中的id")
    private Integer templateId;

    @ApiModelProperty(value = "回测URL地址")
    private String url;

    @ApiModelProperty(value = "排序，值越小排在最前面，默认1")
    private Integer sort;

    @ApiModelProperty(value = "令牌")
    private String token;

    @ApiModelProperty("IP")
    private String ip;

    @ApiModelProperty("掩码")
    private String mask;

    private PlatformEnum platform;
}
