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
 *  <p>
 *  优惠活动表
 *  </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_promotions_setting")
@ApiModel(value="UcPromotionsSetting对象", description="优惠活动表")
public class UcPromotionsSetting implements Serializable  {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "赠送USDT个数")
    private Integer usdtNum;

    @ApiModelProperty(value = "是否开启：0关闭，1开启")
    private Boolean isEnable;

    @ApiModelProperty(value = "活动开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "活动结束时间")
    private Date endTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
