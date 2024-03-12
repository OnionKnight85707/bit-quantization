package com.mzwise.modules.ucenter.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 查询统计数据响应
 * @Author LiangZaiChao
 * @Date 2022/8/2 15:52
 */
@Data
public class UcStatisticsVo implements Serializable {

    private static final long serialVersionUID = 1800482080908826476L;

    private Long id;

    @ApiModelProperty(value = "发生日期")
    private String recordDate;

    @ApiModelProperty(value = "注册用户数")
    private Integer registerNum;

    @ApiModelProperty(value = "网络充币数量")
    private BigDecimal rechargeOnline;

    @ApiModelProperty(value = "后台充币数量")
    private BigDecimal rechargeBackstage;

    @ApiModelProperty(value = "提币(成功)数量")
    private BigDecimal withdrawSuccess;

    @ApiModelProperty(value = "用户合约收益")
    private BigDecimal userSwapProfit;

    @ApiModelProperty(value = "公司收益")
    private BigDecimal companyProfit;

    @ApiModelProperty(value = "合伙人佣金")
    private BigDecimal partnerCommission;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
