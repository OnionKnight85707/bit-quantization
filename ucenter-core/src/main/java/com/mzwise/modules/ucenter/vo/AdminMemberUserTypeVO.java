package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @Author 666
 * @Date 2022/08/03
 */
@Data
@ApiModel("后台用户类别管理类")
public class AdminMemberUserTypeVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户等级")
    private String userTypeName;

}
