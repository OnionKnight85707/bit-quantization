package com.mzwise.common.util;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.mzwise.modules.admin.entity.UmsAdmin;
import com.mzwise.user.AdminUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtils {

    /**
     * 获取后台当前登录用户
     *
     * @return
     */
    public static UmsAdmin getCurrentAdmin() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ApiException("当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof AdminUserDetails) {
            AdminUserDetails userDetails = (AdminUserDetails) authentication.getPrincipal();
            return userDetails.getUmsAdmin();
        }
        throw new ApiException("当前登录状态过期");
    }

    /**
     * 获取后台当前登录用户的用户名
     *
     * @return
     */
    public static String getCurrentAdminUsername() {
        UmsAdmin currentAdmin = getCurrentAdmin();
        return currentAdmin.getUsername();
    }

    /**
     * 获取后台当前登录用户的用户id
     *
     * @return
     */
    public static Long getCurrentAdminId() {
        UmsAdmin currentAdmin = getCurrentAdmin();
        return currentAdmin.getId();
    }

}
