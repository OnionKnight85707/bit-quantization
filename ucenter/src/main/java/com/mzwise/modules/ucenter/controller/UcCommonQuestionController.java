package com.mzwise.modules.ucenter.controller;


import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.CommonQuestionTypeEnum;
import com.mzwise.modules.ucenter.entity.UcCommonQuestion;
import com.mzwise.modules.ucenter.entity.UcQuestionType;
import com.mzwise.modules.ucenter.service.UcCommonQuestionService;
import com.mzwise.modules.ucenter.service.UcQuestionTypeService;
import com.mzwise.modules.ucenter.vo.QuestionIdType;
import com.mzwise.modules.ucenter.vo.QuestionResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Api(tags = "常见问题")
@RestController
@RequestMapping("/ucenter/ucCommonQuestion")
public class UcCommonQuestionController {

    @Autowired
    private UcCommonQuestionService commonQuestionService;

    @Autowired
    private UcQuestionTypeService questionTypeService;


    @ApiOperation("分类展示所有常见问题")
    @GetMapping("/listAllByType")
    @AnonymousAccess
    public CommonResult<List<QuestionResult>> listAllByType() {
        List<QuestionResult> resultList=new ArrayList<>();


        List<UcQuestionType> typeList= questionTypeService.listAllByLocale();

        for(UcQuestionType type:typeList)
        {
            QuestionResult result=new QuestionResult();
            result.setIdType(new QuestionIdType(type.getId(),type.getName()));

            List<UcCommonQuestion>  list= commonQuestionService.listByType(type.getId());
            result.setList(list);
            resultList.add(result);
        }

        return CommonResult.success(resultList);
    }

    @ApiOperation("分类")
    @GetMapping("/listType")
    @AnonymousAccess
    public CommonResult<List<UcQuestionType>> listType() {

        List<UcQuestionType> typeList= questionTypeService.listAll();

        return CommonResult.success(typeList);
    }



}

