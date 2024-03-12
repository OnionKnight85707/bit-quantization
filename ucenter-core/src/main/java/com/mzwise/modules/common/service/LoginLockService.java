package com.mzwise.modules.common.service;


/**
 * 用户登录锁
 */
public interface LoginLockService {
    /**
     * 增加错误次数
     */
    void wrong(String accout);

    /**
     * 设置缓存信息
     */
    Boolean isLocked(String accout);
}
