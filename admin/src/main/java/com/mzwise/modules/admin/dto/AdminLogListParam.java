package com.mzwise.modules.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 管理员日志列表请求
 * @author: David Liang
 * @create: 2022-07-25 11:09
 */
@Data
public class AdminLogListParam implements Serializable {

    private static final long serialVersionUID = -4560916074569072011L;

    @ApiModelProperty(value = "页码")
    private Integer pageNum;

    @ApiModelProperty(value = "页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "管理员id")
    private Long adminId;

    @ApiModelProperty(value = "动作")
    private Integer action;

    @ApiModelProperty(value = "模块")
    private Integer module;

    @ApiModelProperty(value = "开始时间")
    private String beginTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

}
