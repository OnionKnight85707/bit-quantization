package com.mzwise.modules.home.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.home.entity.HomeNews;
import com.mzwise.modules.home.service.HomeNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Api(tags = "新闻")
@RestController
@RequestMapping("/home/homeNews")
public class HomeNewsController {
    @Autowired
    private HomeNewsService homeNewsService;

    @ApiOperation("首页新闻列表")
    @GetMapping("/list")
    @AnonymousAccess
    public CommonResult<Page<HomeNews>> list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return CommonResult.success(homeNewsService.listPage(page, size));
    }

    @ApiOperation("新闻详情")
    @GetMapping("/detail")
    public CommonResult<HomeNews> getDetail(@RequestParam Long id) {
        return CommonResult.success(homeNewsService.getDetail(id));
    }
}

