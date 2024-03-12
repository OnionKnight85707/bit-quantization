package com.mzwise.modules.admin.controller;

import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.admin.entity.UcPartnerQuestion;
import com.mzwise.modules.admin.service.UcPartnerQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "合伙人常见问题")
@RestController
@RequestMapping("/admin/partnerQuestion")

public class UcPartnerQuestionController {

    @Autowired
    private UcPartnerQuestionService ucPartnerQuestionService;

    @ApiOperation("创建常见问题")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UcPartnerQuestion question) {
        ucPartnerQuestionService.saveOrUpdate(question);
        return CommonResult.success();
    }

    @ApiOperation("列表")
    @GetMapping("/list")
    public CommonResult<List<UcPartnerQuestion>> listPage() {
        return CommonResult.success(ucPartnerQuestionService.findAll());
    }

    @ApiOperation("修改")
    @PutMapping("/update")
    public CommonResult update(@RequestBody UcPartnerQuestion question) {
        ucPartnerQuestionService.updateById(question);
        return CommonResult.success();
    }


    @ApiOperation("删除")
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        ucPartnerQuestionService.removeById(id);
        return CommonResult.success();
    }


}
