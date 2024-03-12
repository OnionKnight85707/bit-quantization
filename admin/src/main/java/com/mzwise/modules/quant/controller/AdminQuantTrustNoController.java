package com.mzwise.modules.quant.controller;

import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.quant.entity.QuantTrustNo;
import com.mzwise.modules.quant.service.QuantTrustNoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author piao
 * @Date 2021/7/15 14:05
 * @Description
 */
@Api(tags = "托管配置")
@RestController
@RequestMapping("/admin/quant-trustNo")
public class AdminQuantTrustNoController {
    @Autowired
    private QuantTrustNoService quantTrustNoService;

    @ApiOperation("保存托管配置")
    @PostMapping("/save")
    public CommonResult save(@RequestBody List<QuantTrustNo> quantTrustNoList) {
        quantTrustNoService.saveOrUpdate(quantTrustNoList);
        return CommonResult.success();
    }

    @ApiOperation("列出所有托管配置")
    @GetMapping("/list")
    public CommonResult list() {
        return CommonResult.success(quantTrustNoService.listByPercentage());
    }

}
