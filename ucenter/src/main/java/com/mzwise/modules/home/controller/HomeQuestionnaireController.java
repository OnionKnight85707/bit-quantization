package com.mzwise.modules.home.controller;

import com.alibaba.fastjson.JSON;
import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.constant.LogActionEnum;
import com.mzwise.constant.MemberLogModuleEnum;
import com.mzwise.constant.RiskTypeEnum;
import com.mzwise.modules.home.dto.AdminQuestionnaireResultParam;
import com.mzwise.modules.home.entity.HomeQuestionnaire;
import com.mzwise.modules.home.service.HomeQuestionnaireResultService;
import com.mzwise.modules.home.service.HomeQuestionnaireService;
import com.mzwise.modules.ucenter.entity.UcMemberLog;
import com.mzwise.modules.ucenter.service.UcMemberLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Api(tags = "风险测评")
@RestController
@RequestMapping("/home/questionnaire")
public class HomeQuestionnaireController {
    @Autowired
    private HomeQuestionnaireService questionnaireService;
    @Autowired
    private HomeQuestionnaireResultService questionnaireResultService;
    @Autowired
    private UcMemberLogService memberLogService;
    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;

    @ApiOperation(value = "用户填写风险测评", notes = "返回风险类型结果" +
            "风险类型: CAUTIOUS(谨慎型),STEADY(稳健型),BALANCED(平衡型),AGGRESSIVE(进取型),RADICAL(激进型)")
    @PostMapping("/create")
    @AnonymousAccess
    public CommonResult<RiskTypeEnum> create(@RequestParam(required = false) Long memberId, @RequestBody List<AdminQuestionnaireResultParam> questionnaireResultParamList) throws Exception {
        if (memberId==null) {
            memberId = SecurityUtils.getCurrentUserId();
        }
        if (memberId==null) {
            throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.HomeQuestionnaireController_001));
        }
        RiskTypeEnum riskType = questionnaireResultService.create(memberId, questionnaireResultParamList);

        // 记录用户日志
        UcMemberLog memberLog = new UcMemberLog(memberId, LogActionEnum.ADD.getValue(), MemberLogModuleEnum.RISK_ASSESSMENT.getValue(),
                null, JSON.toJSONString(questionnaireResultParamList), "用户" + memberId + "风险评测");
        memberLogService.addMemberLog(memberLog);
        return CommonResult.success(riskType);
    }

    @ApiOperation("展示所有风险测评")
    @GetMapping("/list")
    @AnonymousAccess
    public CommonResult<List<HomeQuestionnaire>> listAll() {
        return CommonResult.success(questionnaireService.listAll());
    }
}

