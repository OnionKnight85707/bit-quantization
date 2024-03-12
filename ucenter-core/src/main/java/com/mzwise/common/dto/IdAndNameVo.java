package com.mzwise.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Id and Name
 * @Author LiangZaiChao
 * @Date 2022/7/27 10:19
 */
@Data
public class IdAndNameVo implements Serializable {

    private Long id;

    private String name;

}
