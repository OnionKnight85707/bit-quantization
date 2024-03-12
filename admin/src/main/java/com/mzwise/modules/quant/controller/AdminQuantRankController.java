package com.mzwise.modules.quant.controller;

import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.quant.entity.UcQuant;
import com.mzwise.modules.quant.service.AdminQuantRankService;
import com.mzwise.modules.quant.vo.QuantRankVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@Api(tags = "后台操作App排行榜")
@RestController
@RequestMapping("/admin/quant-rank")
public class AdminQuantRankController {

    @Autowired
    private AdminQuantRankService adminQuantRankService;

    @ApiOperation("查看历史量化收益、今日量化收益排行榜")
    @GetMapping("/queryRank")
    public CommonResult<QuantRankVO> queryRank() {
        return CommonResult.success(adminQuantRankService.queryRank());
    }

    @ApiOperation("修改历史量化收益、今日量化收益排行榜")
    @PostMapping("/modifyRank")
    public CommonResult modifyRank(@RequestParam Long memberId, @RequestParam BigDecimal swapTodayProfit, @RequestParam BigDecimal swapTotalProfit) {
        adminQuantRankService.modifyRank(memberId, swapTodayProfit, swapTotalProfit);
        return CommonResult.success();
    }

}
