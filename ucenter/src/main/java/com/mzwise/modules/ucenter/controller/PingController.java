package com.mzwise.modules.ucenter.controller;


import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
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
@Api(tags = "ping")
@RestController
public class PingController {

    @ApiOperation("ping")
    @RequestMapping("/ucenter/ping")
    @AnonymousAccess
    public CommonResult listAllByType() {
        return CommonResult.success();
    }

}

