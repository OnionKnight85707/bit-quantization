package com.mzwise.modules.ucenter.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * @Author 666
 * @Date 2022/07/28
 */
@Data
public class UcPromotionsSettingParam implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "赠送USDT个数")
    private Integer usdtNum;

    @ApiModelProperty(value = "是否开启：0关闭，1开启")
    private Boolean isEnable;

    @ApiModelProperty(value = "活动开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    @ApiModelProperty(value = "活动结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
