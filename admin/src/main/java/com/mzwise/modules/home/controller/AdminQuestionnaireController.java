package com.mzwise.modules.home.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.home.dto.HomeQuestionnaireParam;
import com.mzwise.modules.home.entity.HomeQuestionnaire;
import com.mzwise.modules.home.service.HomeQuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author piao
 * @Date 2021/03/10
 */
@RestController
@RequestMapping("/admin/questionnaire")
@Api(tags = "后台风险测评")
public class AdminQuestionnaireController {
    @Autowired
    private HomeQuestionnaireService questionnaireService;

    @ApiOperation("创建题库")
    @PostMapping("/create")
    public CommonResult create(@RequestBody HomeQuestionnaireParam questionnaireParam) {
        questionnaireService.create(questionnaireParam);
        return CommonResult.success();
    }

    @ApiOperation("编辑题库")
    @PutMapping("/update")
    public CommonResult update(@RequestBody HomeQuestionnaire questionnaire) {
        questionnaireService.updateById(questionnaire);
        return CommonResult.success();
    }

    @ApiOperation("删除题库")
    @PutMapping("/delete/{id}")
    public CommonResult logicDelete(@PathVariable("id") Long homeQuestionnaireId) {
        questionnaireService.logicDelete(homeQuestionnaireId);
        return CommonResult.success();
    }

    @ApiOperation("展示所有风险测评")
    @GetMapping("/listAll")
    public CommonResult<Page<HomeQuestionnaire>> listAll(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        return CommonResult.success(questionnaireService.listAll(pageNum, pageSize));
    }

}
