package com.mzwise.modules.common.service.impl;

import com.mzwise.common.config.RedisService;
import com.mzwise.constant.SysConstant;
import com.mzwise.modules.common.service.LoginLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoginLockServiceImpl implements LoginLockService {

    @Value("${spring.redis.database}")
    private String REDIS_DATABASE;
    @Autowired
    private RedisService redisService;

    @Override
    public void wrong(String accout) {
        String key = REDIS_DATABASE + ":" + SysConstant.LOGIN_LOCKED_PREFIX + ":" + accout;
        Integer times = (Integer) redisService.get(key);
        if (times==null) {
            times = 0;
        }
        times++;
        redisService.set(key, times, 30 * 60);
    }

    @Override
    public Boolean isLocked(String accout) {
        String key = REDIS_DATABASE + ":" + SysConstant.LOGIN_LOCKED_PREFIX + ":" + accout;
        Integer times = (Integer) redisService.get(key);
        if (times!=null && times >= 5) {
            return true;
        }
        return false;
    }

//    @Override
//    public void del(String account) {
//        String key = REDIS_DATABASE + ":" + SysConstant.BIND_CODE_PREFIX + ":" + account;
//        redisService.del(key);
//    }
//
//    @Override
//    public String get(String account) {
//        String key = REDIS_DATABASE + ":" + SysConstant.BIND_CODE_PREFIX + ":" + account;
//        return (String) redisService.get(key);
//    }
//
//    @Override
//    public void set(String account, String code) {
//        String key = REDIS_DATABASE + ":" + SysConstant.BIND_CODE_PREFIX + ":" + account;
//        redisService.set(key, code, 10 * 60);
//    }
}
