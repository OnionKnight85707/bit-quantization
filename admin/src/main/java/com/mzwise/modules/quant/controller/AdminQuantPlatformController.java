package com.mzwise.modules.quant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.quant.entity.QuantPlatform;
import com.mzwise.modules.quant.service.QuantPlatformService;
import com.mzwise.modules.quant.vo.EnablePlatformVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 交易所
 * @author admin
 * @since 2022-08-19
 */
@Api(tags = "交易所Api")
@RestController
@RequestMapping("/admin/quant-platform")
public class AdminQuantPlatformController {

    @Autowired
    private QuantPlatformService quantPlatformService;

    @ApiOperation("交易所列表")
    @GetMapping("/platformList")
    public CommonResult<Page<QuantPlatform>> quantPlatformList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Page<QuantPlatform> page = quantPlatformService.platformList(pageNum, pageSize);
        return CommonResult.success(page);
    }

    @ApiOperation("禁用启用交易所")
    @PostMapping("/enable/platfrom")
    public CommonResult enablePlatform(@RequestBody EnablePlatformVO param) {
        quantPlatformService.enablePlatform(param);
        return CommonResult.success();
    }

}
