package com.mzwise.modules.home.controller;


import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.home.entity.HomeCoin;
import com.mzwise.modules.home.entity.HomeIntroduction;
import com.mzwise.modules.home.service.HomeCoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-03-30
 */
@RestController
@RequestMapping("/home/coin")
@Api(tags = "系统币种")
public class HomeCoinController {

    @Autowired
    private HomeCoinService coinService;

    @ApiOperation("查询所有")
    @GetMapping
    @AnonymousAccess
    public CommonResult<List<HomeCoin>> list() {
        return CommonResult.success(coinService.list());
    }
}

