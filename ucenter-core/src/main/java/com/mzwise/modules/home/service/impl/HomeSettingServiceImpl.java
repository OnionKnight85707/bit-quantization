package com.mzwise.modules.home.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mzwise.common.util.SpringUtil;
import com.mzwise.modules.home.entity.HomeSetting;
import com.mzwise.modules.home.mapper.HomeSettingMapper;
import com.mzwise.modules.home.service.HomeSettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.TimeZone;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-04-02
 */
@Service
@CacheConfig(cacheNames = "bte.home-setting")
public class HomeSettingServiceImpl extends ServiceImpl<HomeSettingMapper, HomeSetting> implements HomeSettingService {


    @Override
    @CacheEvict(allEntries = true)
    public void update(HomeSetting setting) {
        ObjectMapper bean = SpringUtil.getBean(ObjectMapper.class);
        bean.setTimeZone(TimeZone.getTimeZone("GMT+"+setting.getGmt()));
        updateById(setting);
    }

    @Override
    @Cacheable
    public HomeSetting get() {
        return getById(1);
    }
}
