package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.entity.UcPromotionsSetting;

import java.util.Date;

/**
 * @Author 666
 * @Date 2022/07/28
 */
public interface UcPromotionsSettingService extends IService<UcPromotionsSetting> {

    /**
     * 查询优惠活动
     * @return
     */
    UcPromotionsSetting getPromotionsSetting();

    /**
     * 判断条件满足才可修改优惠活动
     * 开始 < 结束
     * 当前 < 开始
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    void checkTime(Date nowTime, Date beginTime, Date endTime);

}
