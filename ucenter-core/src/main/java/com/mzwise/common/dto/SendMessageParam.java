package com.mzwise.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 发送手机验证码请求
 * @author: David Liang
 * @create: 2022-07-22 20:12
 */
@Data
public class SendMessageParam implements Serializable {

    // 地区码
    @NotEmpty
    private String areaCode;

    // 手机号码
    @NotEmpty
    private String phoneNumber;

}
