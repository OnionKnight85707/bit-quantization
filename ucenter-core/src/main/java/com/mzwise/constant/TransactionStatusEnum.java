package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@ApiModel("交易状态")
public enum TransactionStatusEnum implements IEnum<Integer> {

    @ApiModelProperty("审核中")
    UNDER_REVIEW(1, "UNDER_REVIEW"),

    @ApiModelProperty("成功")
    SUCCESS(2, "SUCCESS"),

    @ApiModelProperty("失败")
    FAIL(3, "FAIL");

    private final Integer value;
    private final String name;

}
