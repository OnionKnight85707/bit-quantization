package com.mzwise.modules.ucenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.exception.ApiException;
import com.mzwise.constant.SettleTypeEnum;
import com.mzwise.modules.ucenter.dto.UniTemplateParam;
import com.mzwise.modules.ucenter.entity.UniTemplate;
import com.mzwise.modules.ucenter.service.UniTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 策略模板控制器
 * @Author LiangZaiChao
 * @Date 2022/8/3 13:52
 */
@Api(tags = "策略模板控制器")
@RestController
@RequestMapping("/admin/template")
public class UniTemplateController {

    @Autowired
    private UniTemplateService templateService;

    @ApiOperation(value = "新增模板")
    @PostMapping("/addTemplate")
    public CommonResult addTemplate(@RequestBody @Validated UniTemplateParam param) {
//        if (SettleTypeEnum.BY_FREEZE.equals(param.getSettleType())) {
//            if (param.getFrozenRatio().compareTo(new BigDecimal("0.15")) == -1) {
//                throw new ApiException("此参数不能小于最大合伙人佣金比例，请重试");
//            }
//        }
        templateService.addTemplate(param);
        return CommonResult.success();
    }

    @ApiOperation(value = "修改模板")
    @PostMapping("/updateTemplate")
    public CommonResult updateTemplate(@RequestBody @Validated UniTemplateParam param) {
//        if (SettleTypeEnum.BY_FREEZE.equals(param.getSettleType())) {
//            if (param.getFrozenRatio().compareTo(new BigDecimal("0.15")) == -1) {
//                throw new ApiException("此参数不能小于最大合伙人佣金比例，请重试");
//            }
//        }
        templateService.updateTemplate(param);
        return CommonResult.success();
    }

    @ApiOperation(value = "模板列表")
    @GetMapping("/templateList")
    public CommonResult<Page<UniTemplate>> templateList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Page<UniTemplate> page = templateService.templateList(pageNum, pageSize);
        return CommonResult.success(page);
    }

    @ApiOperation(value = "模板列表(不分页)")
    @GetMapping("/UniTemplateList")
    public CommonResult<List<UniTemplate>> UniTemplateList(){
        List<UniTemplate> uniTemplates = templateService.UniTemplateList();
        return CommonResult.success(uniTemplates);
    }

    @ApiOperation("删除模版")
    @PostMapping("/deleteTemplate")
    public CommonResult deleteTemplate(@RequestParam Long id){
        templateService.deleteTemplate(id);
        return CommonResult.success();
    }

}
