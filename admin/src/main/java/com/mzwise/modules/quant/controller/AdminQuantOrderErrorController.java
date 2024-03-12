package com.mzwise.modules.quant.controller;

import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.quant.entity.QuantLog;
import com.mzwise.modules.quant.service.QuantLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author piao
 * @Date 2021/06/09
 */
@Api(tags = "错误订单管理")
@RestController
@RequestMapping("/admin/quant-order-error")
public class AdminQuantOrderErrorController {
    @Autowired
    private QuantLogService quantLogService;

    @ApiOperation("获取指定错误订单")
    @GetMapping("/getOne")
    public CommonResult<List<QuantLog>> getByOrderId(@RequestParam @ApiParam("订单id") String orderId) {
        return CommonResult.success(quantLogService.getByOrderId(orderId));
    }
}
