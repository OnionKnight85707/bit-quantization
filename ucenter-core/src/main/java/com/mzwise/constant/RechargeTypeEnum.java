package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 充值类型
 * @author: David Liang
 * @create: 2022-07-25 23:13
 */
@AllArgsConstructor
@Getter
@ApiModel("充值类型")
public enum RechargeTypeEnum implements IEnum<Integer> {

    @ApiModelProperty("手动")
    MANUAL(1, "MANUAL"),

    @ApiModelProperty("优盾")
    U_DUN(2, "U_DUN");

    private final Integer value;
    private final String name;

}
