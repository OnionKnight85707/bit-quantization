package com.mzwise.modules.ucenter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Administrator
 * @Date 2021/02/19
 */
@Data
public class SendMessageParam {
    @ApiModelProperty("会员id")
    private Long memberId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;
}
