package com.mzwise.modules.home.service;

import com.mzwise.modules.home.entity.HomeSetting;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-04-02
 */
public interface HomeSettingService extends IService<HomeSetting> {
    /**
     * 修改配置
     * @param setting
     * @return
     */
    void update(HomeSetting setting);

    /**
     * 获取配置
     * @param
     * @return
     */
    HomeSetting get();
}
