package com.mzwise.modules.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 管理员日志列表响应
 * @author: David Liang
 * @create: 2022-07-25 14:09
 */
@Data
public class AdminLogListVo implements Serializable {

    private static final long serialVersionUID = 7406172997363173677L;

    private Long id;

    @ApiModelProperty(value = "管理员id")
    private Long adminId;

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

    @ApiModelProperty(value = "管理员名称")
    private String adminName;

}
