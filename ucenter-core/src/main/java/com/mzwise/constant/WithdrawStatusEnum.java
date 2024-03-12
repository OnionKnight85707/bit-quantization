package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Administrator
 * @Date 2021/01/14
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@AllArgsConstructor
@Getter
@ApiModel("出金状态枚举")
public enum WithdrawStatusEnum implements IEnum<Integer> {
    @ApiModelProperty("等待")
    WAITING(0, "WAITING"),

    @ApiModelProperty("取消")
    CANCEL(1, "CANCEL"),

    @ApiModelProperty("失败")
    FAIL(2, "FAIL"),

    @ApiModelProperty("通过")
    PASS(4, "PASS"),

    @ApiModelProperty("交易完成")
    DONE(5, "DONE");

    private final Integer value;
    private final String name;
}

