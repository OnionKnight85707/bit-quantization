package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author piao
 * @Date 2021/06/03
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@ApiModel("发送消息通道")
@Getter
@AllArgsConstructor
public enum MessageChannelEnum implements IEnum<Integer> {
    @ApiModelProperty("APP消息推送")
    APP_PUSH(0, "APP_PUSH"),

    @ApiModelProperty("短信消息推送")
    SMS(1, "SMS"),

    @ApiModelProperty("站内消息推送")
    SYS_MESSAGE(2, "SYS_MESSAGE");

    private final Integer value;
    private final String name;
}
