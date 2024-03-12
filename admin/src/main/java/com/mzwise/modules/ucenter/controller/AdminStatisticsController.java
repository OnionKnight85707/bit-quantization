package com.mzwise.modules.ucenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.ucenter.dto.UcStatisticsParam;
import com.mzwise.modules.ucenter.entity.UcStatistics;
import com.mzwise.modules.ucenter.service.UcStatisticsService;
import com.mzwise.modules.ucenter.vo.UcStatisticsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/**
 * @Author 666
 * @Date 2022/08/01
 */
@Api(tags = "统计数据")
@RestController
@RequestMapping("/statistics")
public class AdminStatisticsController {

    @Autowired
    private UcStatisticsService ucStatisticsService;

    @ApiOperation("分页查询统计数据")
    @PostMapping("/all-statistics")
    public CommonResult<Page<UcStatisticsVo>> listAllStatistics(@RequestBody UcStatisticsParam param) {
        return CommonResult.success(ucStatisticsService.listAllStatistics(param));
    }

}
