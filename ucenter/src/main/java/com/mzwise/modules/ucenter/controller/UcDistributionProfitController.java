package com.mzwise.modules.ucenter.controller;


import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.ucenter.service.UcDistributionProfitService;
import com.mzwise.modules.ucenter.vo.QuantifiedCommissionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-05-25
 */
@Api(tags = "量化收益")
@RestController
@RequestMapping("/ucenter/ucDistributionProfit")
public class UcDistributionProfitController {
    @Autowired
    private UcDistributionProfitService distributionProfitService;

    @ApiOperation("展示我的量化收益")
    @GetMapping("/quantified-commission")
    public CommonResult<QuantifiedCommissionVO> showQuantifiedCommission() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return CommonResult.success(distributionProfitService.showQuantifiedCommission(currentUserId));
    }
}

