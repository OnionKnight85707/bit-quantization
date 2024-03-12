package com.mzwise.modules.home.controller;

import com.mzwise.common.api.CommonResult;
import com.mzwise.common.component.RatePool;
import com.mzwise.modules.home.entity.HomeCoinRate;
import com.mzwise.modules.home.service.HomeCoinRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author wmf
 * @Date 2021/7/8 10:54
 * @Description
 */
@Api(tags = "后台汇率设置")
@RestController
@RequestMapping("/admin/rate")
public class AdminRateController {
    @Autowired
    private HomeCoinRateService coinRateService;
    @Autowired
    private RatePool ratePool;

    @ApiOperation("获取所有汇率配置, BTE汇率，FIL汇率，USD-CNY： 美元兑人民币汇率")
    @GetMapping
    public CommonResult<List<HomeCoinRate>> list() {
        List<HomeCoinRate> rates = coinRateService.list();
        return CommonResult.success(rates);
    }

    @ApiOperation("设置汇率")
    @PutMapping("/{coin}/{rate}")
    public CommonResult put(@PathVariable String coin, @PathVariable BigDecimal rate) {
        ratePool.setRate(coin, rate);
        return CommonResult.success();
    }
}
