package com.mzwise.modules.ucenter.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * NameAndValue
 */
@Data
@Builder
public class NameAndValueVo implements Serializable {

    private static final long serialVersionUID = -5703676738984110358L;

    private String name;

    private String letterName; // 字母名称

    private String value;

    private String label;

}
