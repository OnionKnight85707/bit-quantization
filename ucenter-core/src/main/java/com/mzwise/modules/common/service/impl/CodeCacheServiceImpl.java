package com.mzwise.modules.common.service.impl;

import com.mzwise.common.config.RedisService;
import com.mzwise.common.util.DateUtil;
import com.mzwise.modules.common.service.CodeCacheService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.constant.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;

@Service
public class CodeCacheServiceImpl implements CodeCacheService {

    @Value("${spring.redis.database}")
    private String REDIS_DATABASE;

    @Autowired
    private UcMemberService memberService;

    @Autowired
    private RedisService redisService;

    @Override
    public void del(String account) {
        String key = REDIS_DATABASE + ":" + SysConstant.BIND_CODE_PREFIX + ":" + account;
        redisService.del(key);
    }

    @Override
    public String get(String account) {
        String key = REDIS_DATABASE + ":" + SysConstant.BIND_CODE_PREFIX + ":" + account;
        return (String) redisService.get(key);
    }

    @Override
    public void set(String account, String code) {
        String key = REDIS_DATABASE + ":" + SysConstant.BIND_CODE_PREFIX + ":" + account;
        redisService.set(key, code, 10 * 60);
    }

    /**
     * 删除缓存
     *
     * @param phone
     * @param memberId
     */
    @Override
    public void del(String phone, Long memberId) {
        String key = REDIS_DATABASE + ":" + SysConstant.BIND_CODE_PREFIX + ":" + phone + ":" + memberId;
        redisService.del(key);
    }

    /**
     * 获取缓存信息
     *
     * @param phone
     * @param memberId
     */
    @Override
    public String get(String phone, Long memberId) {
        String key = REDIS_DATABASE + ":" + SysConstant.BIND_CODE_PREFIX + ":" + phone + ":" + memberId;
        return (String) redisService.get(key);
    }

    /**
     * 设置缓存信息
     *
     * @param phone
     * @param memberId
     * @param code
     */
    @Override
    public void set(String phone, Long memberId, String code) {
        String key = REDIS_DATABASE + ":" + SysConstant.BIND_CODE_PREFIX + ":" + phone + ":" + memberId;
        redisService.set(key, code, 3 * 60);
    }

    /**
     * 获取用户发短信数目
     *
     * @param memberId
     * @return
     */
    @Override
    public Integer getSendMessageNum(Long memberId) {
        String key = REDIS_DATABASE + ":" + SysConstant.SEND_MESSAGE_NUM_PREFIX + ":" + memberId;
        Object num = redisService.get(key);
        if (ObjectUtils.isEmpty(num)) {
            return null;
        }
        return Integer.parseInt(String.valueOf(num));
    }

    /**
     * 用户发送短信条数 +1
     *
     * @param memberId
     */
    @Override
    public void incrSendMessageNum(Long memberId) throws ParseException {
        String key = REDIS_DATABASE + ":" + SysConstant.SEND_MESSAGE_NUM_PREFIX + ":" + memberId;
        Object num = redisService.get(key);
        if (ObjectUtils.isEmpty(num)) {
            redisService.incr(key,1);
            // 凌晨失效
            redisService.expire(key, DateUtil.beforeMidnight());
        } else {
            redisService.incr(key,1);
        }
    }

}
