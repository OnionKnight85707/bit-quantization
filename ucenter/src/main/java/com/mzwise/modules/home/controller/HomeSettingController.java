package com.mzwise.modules.home.controller;


import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.home.entity.HomeSetting;
import com.mzwise.modules.home.service.HomeSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-04-02
 */
@Api(tags = "系统配置")
@RestController
@RequestMapping("/home/setting")
public class HomeSettingController {

    @Autowired
    private HomeSettingService settingService;

    @ApiOperation("获取系统配置")
    @GetMapping
    @AnonymousAccess
    public CommonResult<HomeSetting> get() {
        HomeSetting commonSetting = settingService.get();
        return CommonResult.success(commonSetting);
    }
}

