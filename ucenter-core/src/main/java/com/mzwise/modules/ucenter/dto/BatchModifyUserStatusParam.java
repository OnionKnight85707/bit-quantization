package com.mzwise.modules.ucenter.dto;

import com.mzwise.constant.MemberStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "批量修改用户状态VO", description = "")
public class BatchModifyUserStatusParam implements Serializable {

    private static final long serialVersionUID = -8696806977370504103L;

    @ApiModelProperty(value = "idList")
    private List<Long> idList;

    @ApiModelProperty(value = "账号状态，0：不可用，1：可用")
    private MemberStatusEnum status;

    @ApiModelProperty(value = "账户禁用说明")
    private String disableInstructions;

}
