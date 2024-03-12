package com.mzwise.common.util;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.user.LoginDetail;
import com.mzwise.modules.ucenter.entity.UcMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class SecurityUtils {

    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;

    private static LocaleMessageDecorateSourceService messageService;

    @PostConstruct
    public void postConstruct() {
        messageService = localeMessageDecorateSourceService;
    }

    /**
     * 获取前台当前登录的用户
     *
     * @return UcMember
     */
    public static UcMember getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ApiException(messageService.getSystemMessage(ExceptionCodeConstant.SecurityUtils_001));
        }
        if (authentication.getPrincipal() instanceof LoginDetail) {
            LoginDetail userDetails = (LoginDetail) authentication.getPrincipal();
            return userDetails.getUcMember();
        }
        throw new ApiException(messageService.getSystemMessage(ExceptionCodeConstant.SecurityUtils_001));
    }

    /**
     * 获取前台当前登录用户的用户名
     *
     * @return 系统用户名称
     */
    public static String getCurrentUsername() {
        UcMember currentUser = getCurrentUser();
        return currentUser.getUsername();
    }

    /**
     * 获取前台当前登录用户的用户id
     *
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        UcMember currentUser = getCurrentUser();
        return currentUser.getId();
    }

    /**
     * 尝试获取前台当前用户ID(使用于不知道用户是否登录情况，未登录返回空)
     *
     * @return 系统用户ID
     */
    public static Long tryGetCurrentUserId() {
        try {
            UcMember currentUser = getCurrentUser();
            return currentUser.getId();
        } catch (Exception e) {
            return null;
        }
    }

}
