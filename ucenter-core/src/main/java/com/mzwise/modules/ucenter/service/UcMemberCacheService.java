package com.mzwise.modules.ucenter.service;



import com.mzwise.modules.ucenter.entity.UcMember;

/**
 * 用户缓存管理Service
 */
public interface UcMemberCacheService {
    /**
     * 删除用户缓存
     */
    void del(Long memberId);

    String getModifyUser(String userName);

    void putModifyUser(UcMember member);

    /**
     * 获取缓存用户信息
     */
    UcMember get(String username);

    /**
     * 设置缓存用户信息
     */
    void set(UcMember member);
}
