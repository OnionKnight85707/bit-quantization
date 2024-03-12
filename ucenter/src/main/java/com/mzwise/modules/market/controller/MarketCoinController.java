package com.mzwise.modules.market.controller;


import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.market.entity.MarketCoin;
import com.mzwise.modules.market.service.MarketCoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-04-07
 */
@Api(tags = "行情币种")
@RestController
@RequestMapping("/market/coin")
public class MarketCoinController {

    @Autowired
    private MarketCoinService marketCoinService;

    @ApiOperation("所有行情币种(暂无用)")
    @GetMapping("/all")
    @AnonymousAccess
    public CommonResult list() {
        List<MarketCoin> coins = marketCoinService.list();
        return CommonResult.success(coins);
    }

}

