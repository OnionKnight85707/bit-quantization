package com.mzwise.modules.quant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.PlatformEnum;
import com.mzwise.modules.quant.service.AdminQuantApiService;
import com.mzwise.modules.quant.vo.AdminQuantApiAccessVO;
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
 * @Date 2021/06/09
 */
@Api(tags = "后台Api")
@RestController
@RequestMapping("/admin/quant-api")
public class AdminQuantApiController {
    @Autowired
    private AdminQuantApiService adminQuantApiService;

    @ApiOperation("后台分页展示用户火币等API绑定信息")
    @GetMapping("/list/api-access")
    public CommonResult<Page<AdminQuantApiAccessVO>> listAllApiSelective(@ApiParam("昵称") String nickname,
                                                                         @ApiParam("手机") String phone,
                                                                         @ApiParam("邮箱") String email,
                                                                         @RequestParam @ApiParam(value = "平台", required = true) PlatformEnum platform,
                                                                         @ApiParam("开始时间") Date beginDate,
                                                                         @ApiParam("结束时间") Date endDate,
                                                                         @RequestParam(defaultValue = "1") @ApiParam("当前页") Integer pageNum,
                                                                         @RequestParam(defaultValue = "10") @ApiParam("每页限制") Integer pageSize) {
        return CommonResult.success(adminQuantApiService.listAllApiSelective(nickname, phone, email, platform, beginDate, endDate, pageNum, pageSize));
    }
}
