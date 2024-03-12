package com.mzwise.modules.home.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2021-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("home_setting")
@ApiModel(value="HomeSetting对象", description="")
public class HomeSetting implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    @ApiModelProperty(value = "时区")
    private Integer gmt;

}
