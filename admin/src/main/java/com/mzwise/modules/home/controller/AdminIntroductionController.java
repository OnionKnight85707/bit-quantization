package com.mzwise.modules.home.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.IntroductionTypeEnum;
import com.mzwise.modules.home.dto.HomeIntroductionParam;
import com.mzwise.modules.home.entity.HomeIntroduction;
import com.mzwise.modules.home.service.HomeIntroductionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author piao
 * @Date 2021/03/10
 */
@Api(tags = "后台说明管理")
@RestController
@RequestMapping("/admin/introduction")
public class AdminIntroductionController {
    @Autowired
    private HomeIntroductionService introductionService;

    @ApiOperation("创建说明")
    @PostMapping("/create")
    public CommonResult create(@RequestBody HomeIntroductionParam introductionParam) {
        introductionService.create(introductionParam);
        return CommonResult.success();
    }

    @ApiOperation("修改说明")
    @PutMapping("/update")
    public CommonResult update(@RequestBody HomeIntroduction introduction) {
        introductionService.updateById(introduction);
        return CommonResult.success();
    }

    @ApiOperation("删除说明")
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long introductionId) {
        introductionService.removeById(introductionId);
        return CommonResult.success();
    }

    @ApiOperation("展示所有说明列表")
    @GetMapping("/list")
    public CommonResult<Page<HomeIntroduction>> listAll(
            @RequestParam(required = false) IntroductionTypeEnum type,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return CommonResult.success(introductionService.listAll(type, language, title, pageNum, pageSize));
    }

    @ApiOperation("展示说明详情")
    @GetMapping("/detail")
    public CommonResult<HomeIntroduction> getDetail(@RequestParam Long introductionId) {
        return CommonResult.success(introductionService.getById(introductionId));
    }
}
