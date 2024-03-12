package com.mzwise.modules.common.service;


import java.text.ParseException;

/**
 * 短信缓存管理Service
 */
public interface CodeCacheService {
    /**
     * 删除缓存
     */
    void del(String account);

    /**
     * 获取缓存信息
     */
    String get(String account);

    /**
     * 设置缓存信息
     */
    void set(String account, String code);


    /**
     * 删除缓存
     */
    void del(String phone, Long memberId);

    /**
     * 获取缓存信息
     */
    String get(String phone, Long memberId);

    /**
     * 设置缓存信息
     */
    void set(String phone, Long memberId, String code);

    /**
     * 获取用户发短信数目
     * @param memberId
     * @return
     */
    Integer getSendMessageNum(Long memberId);

    /**
     * 用户发送短信条数 +1
     * @param memberId
     */
    void incrSendMessageNum(Long memberId) throws ParseException;

}
