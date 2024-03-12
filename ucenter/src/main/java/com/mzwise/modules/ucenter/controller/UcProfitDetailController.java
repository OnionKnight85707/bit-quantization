package com.mzwise.modules.ucenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.ucenter.service.UcProfitDetailService;
import com.mzwise.modules.ucenter.vo.ProfitDetailsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收益详情控制器
 * @author: David Liang
 * @create: 2022-07-23 15:34
 */
@Api(tags = "收益详情控制器")
@RestController
@RequestMapping("/uc/profitDetail")
public class UcProfitDetailController {

    @Autowired
    private UcProfitDetailService profitDetailService;

    @ApiOperation(value = "收益详情")
    @GetMapping("/profitDetails")
    public CommonResult<Page<ProfitDetailsVo>> profitDetails(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Page<ProfitDetailsVo> page = profitDetailService.profitDetails(pageNum, pageSize, currentUserId);
        return CommonResult.success(page);
    }

    @ApiOperation(value = "按日统计收益")
    @GetMapping("/profitDetailsForDay")
    public CommonResult<Page<ProfitDetailsVo>> profitDetailsForDay(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Page<ProfitDetailsVo> page = profitDetailService.profitDetailsForDay(pageNum, pageSize, currentUserId);
        return CommonResult.success(page);
    }

    @ApiOperation(value = "月度统计收益")
    @GetMapping("/profitDetailsForMonth")
    public CommonResult<Page<ProfitDetailsVo>> profitDetailsForMonth(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Page<ProfitDetailsVo> page = profitDetailService.profitDetailsForMonth(pageNum, pageSize, currentUserId);
        return CommonResult.success(page);
    }

    @ApiOperation(value = "年度统计收益")
    @GetMapping("/profitDetailsForYear")
    public CommonResult<Page<ProfitDetailsVo>> profitDetailsForYear(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Page<ProfitDetailsVo> page = profitDetailService.profitDetailsForYear(pageNum, pageSize, currentUserId);
        return CommonResult.success(page);
    }


}
