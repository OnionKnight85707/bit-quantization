package com.mzwise.modules.home.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.quant.trade.UnifyExchangeUtil;
import com.mzwise.constant.PlatformEnum;
import com.mzwise.modules.home.dto.SymbolInfoParam;
import com.mzwise.modules.quant.entity.QuantCoin;
import com.mzwise.modules.quant.entity.QuantSetting;
import com.mzwise.modules.quant.service.QuantCoinService;
import com.mzwise.modules.quant.service.QuantSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author piao
 * @Date 2021/06/09
 */
@Api(tags = "后台设置管理")
@RestController
@RequestMapping("/admin/system-setting")
public class AdminSystemSettingController {
    @Autowired
    private QuantSettingService quantSettingService;
    @Autowired
    private QuantCoinService quantCoinService;

    @ApiOperation("获取量化设置")
    @GetMapping("/quant-setting")
    public CommonResult getQuantSetting() {
        return CommonResult.success(quantSettingService.get());
    }

    @ApiOperation("修改量化设置")
    @PutMapping("/update/quant-setting")
    public CommonResult updateQuantSetting(@RequestBody QuantSetting quantSetting) {
        quantSettingService.update(quantSetting);
        return CommonResult.success();
    }

    @ApiOperation("新增币种设置")
    @PostMapping("/create/quantCoin")
    public CommonResult createQuantCoin(@RequestBody QuantCoin coin) {
        quantCoinService.create(coin);
        return CommonResult.success();
    }

    @ApiOperation("修改币种设置")
    @PutMapping("/update/quantCoin")
    public CommonResult updateQuantCoin(@RequestBody QuantCoin quantCoin) {
        quantCoinService.update(quantCoin);
        return CommonResult.success();
    }

    @ApiOperation(value = "获取第三方交易规则", notes = "传两个字段symbolPair和platform即可")
    @PostMapping("/syncExchange")
    public CommonResult<QuantCoin> syncExchange(@RequestBody SymbolInfoParam param) {
        QuantCoin quantCoin = new QuantCoin();
        quantCoin.setPlatform(param.getPlatform());
        quantCoin.setSymbolPair(param.getSymbolPair());
        QuantCoin symbolInfo = UnifyExchangeUtil.getSymbolInfo(quantCoin);
        return CommonResult.success(symbolInfo);
    }

    @ApiOperation("获取所有币种")
    @GetMapping("/list/quantCoin")
    public CommonResult<Page<QuantCoin>> listAllQuantCoinSelective(@ApiParam("平台") PlatformEnum platform, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, @ApiParam("交易对名称") String name) {
        return CommonResult.success(quantCoinService.list(platform, pageNum, pageSize, name));
    }


}
