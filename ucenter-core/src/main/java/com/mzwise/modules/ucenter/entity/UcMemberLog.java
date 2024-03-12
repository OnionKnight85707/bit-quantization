package com.mzwise.modules.ucenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户日志实体
 * @author: David Liang
 * @create: 2022-07-26 18:10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_member_log")
@ApiModel(value="UcMemberLog对象", description="用户日志表")
public class UcMemberLog implements Serializable {

    private static final long serialVersionUID = -306700976562715958L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long memberId;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "动作")
    private Integer action;

    @ApiModelProperty(value = "模块")
    private Integer module;

    @ApiModelProperty(value = "修改前参数json")
    private String beforeParam;

    @ApiModelProperty(value = "修改后参数json")
    private String afterParam;

    @ApiModelProperty(value = "日志简单描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public UcMemberLog() {
    }

    public UcMemberLog(Long memberId, Integer action, Integer module, String beforeParam, String afterParam, String description) {
        this.memberId = memberId;
        this.action = action;
        this.module = module;
        this.beforeParam = beforeParam;
        this.afterParam = afterParam;
        this.description = description;
    }

}
