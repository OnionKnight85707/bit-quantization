package com.mzwise.modules.ucenter.controller;

import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.ucenter.entity.UcQuestionType;
import com.mzwise.modules.ucenter.service.UcQuestionTypeService;
import com.mzwise.modules.ucenter.vo.UcQuestionTypeParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/02/02
 */
@Api(tags = "后台常见问题管理")
@RestController
@RequestMapping("/admin/questionType")
public class AdminQuestionTypeController {
    @Autowired
    private UcQuestionTypeService questionTypeService;

    @ApiOperation("创建类型")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UcQuestionTypeParam questionTypeParam) {
        questionTypeService.saveOrUpdate(questionTypeParam);
        return CommonResult.success();
    }

    @ApiOperation("所有列表")
    @GetMapping("/list")
    public CommonResult<List<UcQuestionType>> listPage() {
        return CommonResult.success(questionTypeService.listAll());
    }

    @ApiOperation("修改类型")
    @PutMapping("/update")
    public CommonResult update(@RequestBody UcQuestionTypeParam questionTypeParam) {
        questionTypeService.saveOrUpdate(questionTypeParam);
        return CommonResult.success();
    }

}
