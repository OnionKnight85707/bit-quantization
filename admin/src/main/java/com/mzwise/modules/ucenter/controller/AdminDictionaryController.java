package com.mzwise.modules.ucenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.ucenter.entity.Dictionary;
import com.mzwise.modules.ucenter.service.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 666
 * @since 2022-08-11
 */
@RestController
@Api(tags = "字典表")
@RequestMapping("/dictionary")
public class AdminDictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @ApiOperation("列表查询字典表数据")
    @GetMapping("/list")
    public CommonResult<Page<Dictionary>> listAllDictionary(@ApiParam(value = "说明") String explanation,
                                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<Dictionary> allDictionary = dictionaryService.listAllDictionary(explanation, pageNum, pageSize);
        return CommonResult.success(allDictionary);
    }

    @ApiOperation("添加字典表信息")
    @PostMapping("/add")
    public CommonResult addDictionary(@RequestBody @Validated Dictionary dictionary) {
        dictionaryService.save(dictionary);
        return CommonResult.success();
    }

    @ApiOperation("修改字典表信息")
    @PostMapping("/modify")
    public CommonResult modifyDictionary(@RequestBody Dictionary dictionary) {
        dictionaryService.updateById(dictionary);
        return CommonResult.success();
    }

}
