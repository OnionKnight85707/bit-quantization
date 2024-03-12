package com.mzwise.common.aspect;

import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.dto.PayPasswordParam;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.service.UcMemberService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 交易密码验证切面
 *
 * @author wmf
 */
@Aspect
@Component
public class PayPasswordRequiredAspect {

    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UcMemberService memberService;

    @Pointcut("@annotation(com.mzwise.annotation.PayPasswordRequired)")
    public void validatePayPassword() {
    }

    /**
     * 交易密码
     *
     * @param joinPoint
     * @return
     */
    @Around("validatePayPassword()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Object[] args = joinPoint.getArgs();
        PayPasswordParam arg = (PayPasswordParam) args[0];
        Assert.hasText(arg.getPayPassword(), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.PayPasswordRequiredAspect_003));
        Long userId = SecurityUtils.getCurrentUserId();
        UcMember currentUser = memberService.getById(userId);
        Assert.hasText(currentUser.getPayPassword(), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.PayPasswordRequiredAspect_002));
        Assert.isTrue(passwordEncoder.matches(arg.getPayPassword(), currentUser.getPayPassword()), localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.PayPasswordRequiredAspect_001));
        result = joinPoint.proceed();
        return result;
    }
}
