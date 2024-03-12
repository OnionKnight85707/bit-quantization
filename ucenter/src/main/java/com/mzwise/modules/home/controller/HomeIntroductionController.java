package com.mzwise.modules.home.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.LocaleSourceUtil;
import com.mzwise.constant.IntroductionTypeEnum;
import com.mzwise.modules.home.entity.HomeIntroduction;
import com.mzwise.modules.home.service.HomeIntroductionService;
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
@Api(tags = "说明")
@RestController
@RequestMapping("/home/homeIntroduction")
public class HomeIntroductionController {
    @Autowired
    private HomeIntroductionService introductionService;

    @ApiOperation("显示说明")
    @GetMapping("/detail")
    @AnonymousAccess
    public CommonResult<HomeIntroduction> getByTitleAndLanguage(@RequestParam String title) {
        return CommonResult.success(introductionService.getByTitleAndLanguage(title));
    }

    @ApiOperation("说明详情")
    @GetMapping("/detailById")
    @AnonymousAccess
    public CommonResult<HomeIntroduction> getById(@RequestParam Long id) {
        return CommonResult.success(introductionService.getById(id));
    }

    @ApiOperation("根据类型查询")
    @GetMapping("/list")
    @AnonymousAccess
    public CommonResult<Page<HomeIntroduction>> list(@RequestParam(required = false) IntroductionTypeEnum type,
                                                     @RequestParam(required = false) String title,
                                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        String language = LocaleSourceUtil.getLanguage();
        return CommonResult.success(introductionService.listAll(type, language, title, pageNum, pageSize));
    }
}

