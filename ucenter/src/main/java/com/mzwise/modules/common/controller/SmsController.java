package com.mzwise.modules.common.controller;

import com.mzwise.common.api.CommonResult;
import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.dto.SendMessageParam;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.common.provider.SMSProvider;
import com.mzwise.common.util.GeneratorUtil;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.common.service.CodeCacheService;
import com.mzwise.modules.ucenter.service.DictionaryService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "通用短信")
@RequestMapping("/common/sms")
@Slf4j
public class SmsController {

    @Autowired
    private SMSProvider smsProvider;
    @Autowired
    private CodeCacheService codeCacheService;
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;
//    @ApiOperation("发送手机验证码")
//    @RequestMapping(value = "/send-code/{phone}", method = RequestMethod.POST)
//    @ResponseBody
//    @AnonymousAccess
//    public CommonResult<String> getItem(@PathVariable String phone) throws Exception {
//        String randomCode = String.valueOf(GeneratorUtil.getRandomNumber(100000, 999999));
//        codeCacheService.set(phone, randomCode);
//        return smsProvider.sendVerifyMessage(phone, randomCode);
//    }

//    @ApiOperation("发送手机验证码")
//    @RequestMapping(value = "/sendAwsMessage/{phone}", method = RequestMethod.POST)
//    @ResponseBody
//    @AnonymousAccess
//    public CommonResult<String> sendAwsMessage(@PathVariable String phone) throws Exception {
//        String randomCode = String.valueOf(GeneratorUtil.getRandomNumber(100000, 999999));
////        codeCacheService.set(phone, randomCode);
//        return smsProvider.sendAwsMessage(phone, randomCode);
//    }

    @ApiOperation("发送手机验证码")
    @PostMapping("/sendMessage")
    public CommonResult<String> sendMessage(HttpServletRequest request, @Validated @RequestBody SendMessageParam param) throws Exception {
        // 滑动验证二次校验
//        ResponseModel verification = captchaService.verification(param);
//        if( ! RepCodeEnum.SUCCESS.getCode().equals(verification.getRepCode())) {
//            return CommonResult.failed();
//        }

        Long currentUserId = SecurityUtils.getCurrentUserId();

        // 防止短信轰炸机调用该接口
//        String server = request.getScheme() + "://" + request.getServerName();
//        String referer = request.getHeader("referer");
//        log.info("麦讯通发送短信：server={}, referer={}", server, referer);
//        if (referer != null && referer.startsWith(server)) {
//            String randomCode = String.valueOf(GeneratorUtil.getRandomNumber(100000, 999999));
//            // 返回正确过滤的手机号
//            String phoneNum = smsProvider.sendMaiXunTongMessage(param.getAreaCode(), param.getPhoneNumber(), randomCode);
//            codeCacheService.set(phoneNum, randomCode);
//            return CommonResult.success(phoneNum, "send_success");
//        } else {
//            return CommonResult.failed();
//        }

        // 规定每人每天发送短信最大条数
        Integer maxSendMessage = dictionaryService.queryMaxSendMessage();
        // 缓存中该用户已发送条数
        Integer sendMessageNum = codeCacheService.getSendMessageNum(currentUserId);
        if ( ! ObjectUtils.isEmpty(sendMessageNum)) {
            if (sendMessageNum >= maxSendMessage) {
                throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.SmsController_001));
            }
        }
        String randomCode = String.valueOf(GeneratorUtil.getRandomNumber(100000, 999999));
        String phoneNum = smsProvider.sendMaiXunTongMessage(currentUserId, param.getAreaCode(), param.getPhoneNumber(), randomCode);
        // 缓存中发送条数 +1
        codeCacheService.incrSendMessageNum(currentUserId);
        return CommonResult.success(phoneNum,"success");
    }

}
