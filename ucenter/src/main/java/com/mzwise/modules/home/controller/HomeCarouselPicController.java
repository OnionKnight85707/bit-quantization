package com.mzwise.modules.home.controller;


import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.CarousePositionTypeEnum;
import com.mzwise.modules.home.entity.HomeCarouselPic;
import com.mzwise.modules.home.service.HomeCarouselPicService;
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
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Api(tags = "轮播图")
@RestController
@RequestMapping("/home/homeCarouselPic")
public class HomeCarouselPicController {
    @Autowired
    private HomeCarouselPicService carouselPicService;

    @ApiOperation("展示轮播图")
    @GetMapping("/list")
    @AnonymousAccess
    public CommonResult<List<HomeCarouselPic>> listCarousePic(@RequestParam CarousePositionTypeEnum position) {
        return CommonResult.success(carouselPicService.showHomeCarousePic(position));
    }
}

