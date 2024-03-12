package com.mzwise.modules.quant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.PlatformEnum;
import com.mzwise.constant.SignalTypeEnum;
import com.mzwise.modules.quant.entity.QuantSignal;
import com.mzwise.modules.quant.service.AdminQuantApiService;
import com.mzwise.modules.quant.service.QuantLogService;
import com.mzwise.modules.quant.service.QuantSignalService;
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
 * @Author wmf
 * @Date 2021/06/09
 */
@Api(tags = "量化日志")
@RestController
@RequestMapping("/admin/log")
public class AdminQuantLogController {

    @Autowired
    private QuantSignalService signalService;

    @ApiOperation(value = "查看日志", notes = "配合: \"查看量化订单详情\", \"查看自设指标详情\", \"查看托管详情\"")
    @GetMapping
    public CommonResult<Page<QuantSignal>> list(@ApiParam("类型") SignalTypeEnum signalType,
                                                @ApiParam("开始时间") Date beginDate,
                                                @ApiParam("结束时间") Date endDate,
                                                @RequestParam(defaultValue = "false") @ApiParam(value = "全部事件(包含未响应)") Boolean all,
                                                @RequestParam(defaultValue = "1") @ApiParam("当前页") Integer pageNum,
                                                @RequestParam(defaultValue = "50") @ApiParam("每页限制，最少50") Integer pageSize) {
        return CommonResult.success(signalService.list(signalType, beginDate, endDate, all, pageNum, pageSize));
    }
}
