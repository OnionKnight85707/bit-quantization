package com.mzwise.modules.ucenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.RealNameStatusEnum;
import com.mzwise.modules.ucenter.dto.UcRealNameFailedParam;
import com.mzwise.modules.ucenter.entity.UcRealNameVerified;
import com.mzwise.modules.ucenter.service.UcRealNameVerifiedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Administrator
 * @Date 2021/02/02
 */
@Api(tags = "后台实名认证管理")
@RestController
@RequestMapping("/admin/realNameVerified")
public class AdminRealNameVerifiedController {
    @Autowired
    private UcRealNameVerifiedService ucRealNameVerifiedService;

    @ApiOperation("审核实名认证 通过")
    @PutMapping("/pass/{id}")
    public CommonResult pass(@PathVariable("id") Long realNameVerifiedId) {
        ucRealNameVerifiedService.pass(realNameVerifiedId);
        return CommonResult.success();
    }

    @ApiOperation("审核实名认证 驳回")
    @PutMapping("/fail")
    public CommonResult fail(@RequestBody UcRealNameFailedParam realNameFailedParam) {
        ucRealNameVerifiedService.failed(realNameFailedParam);
        return CommonResult.success();
    }

    @ApiOperation("所有实名认证列表")
    @GetMapping("/list")
    public CommonResult<Page<UcRealNameVerified>> listAll(@RequestParam(required = false) RealNameStatusEnum realNameStatusEnum,
                                                          @RequestParam(required = false) String realName,
                                                          @RequestParam(defaultValue = "1") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        return CommonResult.success(ucRealNameVerifiedService.listAll(realNameStatusEnum, realName, page, size));
    }

    @ApiOperation("实名认证详情")
    @GetMapping("/detail")
    public CommonResult<UcRealNameVerified> getDetail(Long realNameVerifiedId) {
        return CommonResult.success(ucRealNameVerifiedService.getById(realNameVerifiedId));
    }
}
