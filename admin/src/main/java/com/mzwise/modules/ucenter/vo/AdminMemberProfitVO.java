package com.mzwise.modules.ucenter.vo;

import com.mzwise.modules.quant.entity.UcQuant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author piao
 * @Date 2021/06/01
 */
@Data
@ApiModel("会员详情——收益")
public class AdminMemberProfitVO {
    @ApiModelProperty("收益概览")
    private UserProfitGeneralVO userProfitGeneralVO;

    @ApiModelProperty("量化收益数据")
    private UcQuant userQuant;
}
