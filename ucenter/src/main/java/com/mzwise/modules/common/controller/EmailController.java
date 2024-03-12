package com.mzwise.modules.common.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.common.provider.EmailProvider;
import com.mzwise.common.util.GeneratorUtil;
import com.mzwise.constant.SendEmailCodeCheckEnum;
import com.mzwise.modules.common.service.CodeCacheService;
import com.mzwise.modules.ucenter.dto.SendEmailParam;
import com.mzwise.modules.ucenter.dto.SendMessageParam;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.service.UcMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "通用邮箱")
@RequestMapping("/common/email")
public class EmailController {

    @Autowired
    private EmailProvider emailProvider;

    @Autowired
    private CodeCacheService codeCacheService;

    @Autowired
    private UcMemberService ucMemberService;

    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;

    @ApiOperation("发送验证码")
    @RequestMapping(value = "/send-code", method = RequestMethod.POST)
    @ResponseBody
    @AnonymousAccess
    public CommonResult<String> getItem(@RequestBody SendEmailParam param) throws Exception {
        // 判断数据库中是否存在该邮箱
        int emailNum = ucMemberService.count(Wrappers.<UcMember>query().lambda().eq(UcMember::getEmail, param.getEmail()));
        if (SendEmailCodeCheckEnum.CHECK == param.getCheckEmail()) {
            if (emailNum == 0) {
                throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMemberServiceImpl_003));
            }
        }
        if (SendEmailCodeCheckEnum.CHECK_NOTEXIST == param.getCheckEmail()) {
            if (emailNum > 0) {
                // 提示：邮箱已被使用
                throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.EmailController_001));
            }
        }
        String randomCode = String.valueOf(GeneratorUtil.getRandomNumber(100000, 999999));
        codeCacheService.set(param.getEmail(), randomCode);
        return emailProvider.sendVerifyMessage(param.getEmail(), randomCode,param.getAreaCode());
    }
}
