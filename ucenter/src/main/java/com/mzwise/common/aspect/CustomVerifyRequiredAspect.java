package com.mzwise.common.aspect;

import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.dto.CustomVerifyParam;
import com.mzwise.common.dto.PayPasswordParam;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.common.service.CodeCacheService;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcMemberAppend;
import com.mzwise.modules.ucenter.service.UcMemberAppendService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.service.UcenterMemberService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


/**
 * 用户自定义验证切面
 * @author wmf
 */
@Aspect
@Component
public class CustomVerifyRequiredAspect {

    @Autowired
    private CodeCacheService codeCacheService;

    @Autowired
    private UcMemberService memberService;

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @Autowired
    private UcMemberAppendService ucMemberAppendService;

    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;

    @Pointcut("@annotation(com.mzwise.annotation.CustomVerifyRequired)")
    public void validateCustomVerify(){}

    /**
     * 自定义验证
     * @param joinPoint
     * @return
     */
    @Around("validateCustomVerify()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        Object result = null;
        Object[] args = joinPoint.getArgs();
        CustomVerifyParam arg = (CustomVerifyParam) args[0];
        Long userId = SecurityUtils.getCurrentUserId();
        UcMember currentUser = memberService.getById(userId);
        // 开启手机验证
//        if (currentUser.getPhoneVerify()) {
//            Assert.isTrue(currentUser.getPhone()!=null, "missing_phone");
//            Assert.isTrue(arg.getPhoneCode()!=null, "missing_phone_code");
//            String code = codeCacheService.get(currentUser.getPhone());
//            Assert.isTrue(arg.getPhoneCode().equals(code), "error_phone_code");
//            codeCacheService.del(currentUser.getPhone());
//        }
//        // 开启邮箱验证
//        if (currentUser.getEmailVerify()) {
//            Assert.isTrue(currentUser.getEmail()!=null, "missing_email");
//            Assert.isTrue(arg.getEmailCode()!=null, "missing_email_code");
//            String code = codeCacheService.get(currentUser.getEmail());
//            Assert.isTrue(arg.getEmailCode().equals(code), "error_email_code");
//            codeCacheService.del(currentUser.getEmail());
//        }


         if(StringUtils.isNotBlank(arg.getGoogleCode())&&(StringUtils.isNotBlank(arg.getEmailCode()))){
            // 币安验证和邮箱验证
            UcMemberAppend append = ucMemberAppendService.getByMemberId(userId);
            Assert.notNull(append, localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.CustomVerifyRequiredAspect_001));
            Assert.isTrue(arg.getGoogleCode()!=null,localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.CustomVerifyRequiredAspect_002));
            Boolean isTrue = ucenterMemberService.checkInputGoogleCode(append.getGoogleKey(), arg.getGoogleCode());
            Assert.isTrue(isTrue,localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.CustomVerifyRequiredAspect_003));

            Assert.isTrue(currentUser.getEmail()!=null, localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.CustomVerifyRequiredAspect_004));
            Assert.isTrue(arg.getEmailCode()!=null, localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.CustomVerifyRequiredAspect_005));
            String emailCode = codeCacheService.get(currentUser.getEmail());
            Assert.isTrue(arg.getEmailCode().equals(emailCode), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.CustomVerifyRequiredAspect_006));
            codeCacheService.del(currentUser.getEmail());
        }else if ( (StringUtils.isNotBlank(arg.getPhoneCode()))&&(StringUtils.isNotBlank(arg.getEmailCode())) ) {
        // 开启邮箱验证和手机验证
            Assert.isTrue(currentUser.getPhone()!=null, localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.CustomVerifyRequiredAspect_007));
            Assert.isTrue(arg.getPhoneCode()!=null, localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.CustomVerifyRequiredAspect_008));
            String code = codeCacheService.get(currentUser.getPhone(),userId);
            Assert.isTrue(arg.getPhoneCode().equals(code), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.CustomVerifyRequiredAspect_009));
            Assert.isTrue(currentUser.getEmail()!=null, localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.CustomVerifyRequiredAspect_004));
            Assert.isTrue(arg.getEmailCode()!=null, localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.CustomVerifyRequiredAspect_005));
            String emailCode = codeCacheService.get(currentUser.getEmail());
            Assert.isTrue(arg.getEmailCode().equals(emailCode), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.CustomVerifyRequiredAspect_006));
            codeCacheService.del(currentUser.getEmail());
            codeCacheService.del(currentUser.getPhone());
        }else{
           throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.CustomVerifyRequiredAspect_010));
        }

        //


        result = joinPoint.proceed();
        return result;
    }
}
