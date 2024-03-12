package com.mzwise.modules.ucenter.service.impl;

import com.mzwise.common.config.RedisService;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.service.UcMemberCacheService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class UcMemberCacheServiceImpl implements UcMemberCacheService {
    @Autowired
    private UcMemberService memberService;
    @Value("${spring.redis.database}")
    private String REDIS_DATABASE;
    @Value("${spring.redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${spring.redis.key.member}")
    private String REDIS_KEY_MEMBER;
    @Autowired
    private RedisService redisService;

    private String MODIFY_USER_KEY="MODIFY_";

    @Override
    public void del(Long memberId) {
        UcMember member = memberService.getById(memberId);
        if (member != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + member.getUsername();
            redisService.del(key);
        }
    }
    @Override
    public String getModifyUser(String userName)
    {
        Object ob= redisService.get(MODIFY_USER_KEY+userName);
        if (ob==null)
        {
            return null;
        }
        return ob.toString();
    }

    @Override
    public void putModifyUser(UcMember member)
    {
         SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         redisService.set(MODIFY_USER_KEY+member.getId(),formater.format(member.getModifyTime()));
    }


    @Override
    public UcMember get(String id) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + id;
        return (UcMember) redisService.get(key);
    }

    @Override
    public void set(UcMember member) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + member.getId();
        redisService.set(key, member, REDIS_EXPIRE);
    }
}
