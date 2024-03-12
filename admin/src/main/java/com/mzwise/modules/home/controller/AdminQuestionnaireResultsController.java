package com.mzwise.modules.home.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.ucenter.vo.AdminQuestionnaireResultsVO;
import com.mzwise.modules.home.service.HomeQuestionnaireResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author piao
 * @Date 2021/03/10
 */
@Api(tags = "后台风险测评结果")
@RestController
@RequestMapping("/admin/questionResult")
public class AdminQuestionnaireResultsController {
    @Autowired
    private HomeQuestionnaireResultService questionnaireResultService;

    @ApiOperation("查看某用户回答的风险测评")
    @GetMapping("/getOne")
    public CommonResult<Page<AdminQuestionnaireResultsVO>> getOneByMemberId(@RequestParam Long memberId,
                                                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                                                            @RequestParam(defaultValue = "10") Integer pageSize) {
        return CommonResult.success(questionnaireResultService.getOneByMemberId(memberId, pageNum, pageSize));
    }


}
