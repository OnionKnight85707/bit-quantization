package com.mzwise.modules.ucenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.ucenter.entity.UcInviteRecord;
import com.mzwise.modules.ucenter.service.AdminInviteRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

/**
 * @Author piao
 * @Date 2021/06/02
 */
@Api(tags = "邀请记录")
@RestController
@RequestMapping("/admin/invite")
public class AdminInviteRecordController {
    @Autowired
    private AdminInviteRecordService adminInviteRecordService;

    @ApiOperation("列出所有邀请记录")
    @GetMapping("/list-record")
    public CommonResult<Page<UcInviteRecord>> listInviteRecord(@ApiParam(value = "被邀请人昵称") String subNickname,
                                                               @ApiParam(value = "邀请人昵称") String supNickname,
                                                               @ApiParam(value = "起始时间") Date beginDate,
                                                               @ApiParam(value = "终止时间") Date endDate,
                                                               @RequestParam(defaultValue = "1") @ApiParam(value = "当前页") Integer pageNum,
                                                               @RequestParam(defaultValue = "10") @ApiParam(value = "每页显示限制") Integer pageSize) {
        return CommonResult.success(adminInviteRecordService.listInviteRecord(subNickname, supNickname, beginDate, endDate, pageNum, pageSize));
    }
}
