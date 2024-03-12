package com.mzwise.modules.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_partner_question")
@ApiModel(value = "uc_partner 常见问题", description = "")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UcPartnerQuestion {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;


    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date  createTime;
}
