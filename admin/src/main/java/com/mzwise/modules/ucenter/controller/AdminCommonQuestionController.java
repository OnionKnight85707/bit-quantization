package com.mzwise.modules.ucenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.CommonQuestionTypeEnum;
import com.mzwise.modules.ucenter.dto.UcCommonQuestionParam;
import com.mzwise.modules.ucenter.entity.UcCommonQuestion;
import com.mzwise.modules.ucenter.service.UcCommonQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Administrator
 * @Date 2021/02/02
 */
@Api(tags = "后台常见问题管理")
@RestController
@RequestMapping("/admin/commonQuestion")
public class AdminCommonQuestionController {
    @Autowired
    private UcCommonQuestionService commonQuestionService;

    @ApiOperation("创建常见问题")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UcCommonQuestionParam commonQuestionParam) {
        commonQuestionService.create(commonQuestionParam);
        return CommonResult.success();
    }

    @ApiOperation("所有常见问题列表")
    @GetMapping("/list")
    public CommonResult<Page<UcCommonQuestion>> listPage(@RequestParam(required = false) String language,
                                                         @RequestParam(required = false) String title,
                                                         @RequestParam(required = false) Integer type,
                                                         @RequestParam(defaultValue = "1") Integer pageNum,
                                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        return CommonResult.success(commonQuestionService.listPage(language, title, type, pageNum, pageSize));
    }

    @ApiOperation("修改常见问题")
    @PutMapping("/update")
    public CommonResult update(@RequestBody UcCommonQuestion commonQuestion) {
        commonQuestionService.updateById(commonQuestion);
        return CommonResult.success();
    }

    @ApiOperation("修改显示与否")
    @PutMapping("/isShow/{id}")
    public CommonResult updateIsShow(@PathVariable(value = "id") Long commonQuestionId) {
        commonQuestionService.updateIsShow(commonQuestionId);
        return CommonResult.success();
    }

    @ApiOperation("修改置顶与否")
    @PutMapping("/isTop/{id}")
    public CommonResult updateIsTop(@PathVariable(value = "id") Long commonQuestionId) {
        commonQuestionService.updateIsTop(commonQuestionId);
        return CommonResult.success();
    }

    @ApiOperation("删除常见问题")
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        commonQuestionService.removeById(id);
        return CommonResult.success();
    }

    @ApiOperation("多选删除常见问题")
    @DeleteMapping("/deleteBatch")
    public CommonResult deleteBatch(@RequestBody Long[] commonQuestionIds) {
        commonQuestionService.deleteBatch(commonQuestionIds);
        return CommonResult.success();
    }
}
