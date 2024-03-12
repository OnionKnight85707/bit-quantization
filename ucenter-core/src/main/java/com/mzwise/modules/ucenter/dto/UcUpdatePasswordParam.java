package com.mzwise.modules.ucenter.dto;

import lombok.Data;

/**
 * @Author Administrator
 * @Date 2021/01/29
 */
@Data
public class UcUpdatePasswordParam {
    private String oldPassword;

    private String newPassword;

}
