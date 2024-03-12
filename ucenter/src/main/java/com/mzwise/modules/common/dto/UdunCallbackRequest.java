package com.mzwise.modules.common.dto;

import lombok.Data;

/**
 * 优盾回调请求
 * @Author LiangZaiChao
 * @Date 2022/6/30 19:28
 */
@Data
public class UdunCallbackRequest {

    private String timestamp;

    private String nonce;

    private String sign;

    private String body;

}
