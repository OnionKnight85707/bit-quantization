package com.mzwise.modules.ucenter.dto;

import com.mzwise.constant.IdCardTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class UcRealNameSubmitApplyParam {
    @ApiModelProperty(value = "真实姓名",required = true)
    private String realName;

    @ApiModelProperty(value = "身份证件类型",required = true)
    private IdCardTypeEnum identityCardType;

    @ApiModelProperty(value = "身份证号",required = true)
    private String identityCardNumber;

    @ApiModelProperty(value = "身份证照片正面",required = true)
    private String identityCardFront;

    @ApiModelProperty(value = "身份证照片反面",required = true)
    private String identityCardBack;
}
