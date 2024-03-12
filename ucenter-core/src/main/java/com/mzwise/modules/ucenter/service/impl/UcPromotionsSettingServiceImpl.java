package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.util.DateUtil;
import com.mzwise.modules.ucenter.entity.UcPromotionsSetting;
import com.mzwise.modules.ucenter.mapper.UcPromotionsSettingMapper;
import com.mzwise.modules.ucenter.service.UcPromotionsSettingService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author 666
 * @Date 2022/07/28
 */
@Service
public class UcPromotionsSettingServiceImpl extends ServiceImpl<UcPromotionsSettingMapper, UcPromotionsSetting> implements UcPromotionsSettingService {

    /**
     * 查询优惠活动
     *
     * @return
     */
    @Override
    public UcPromotionsSetting getPromotionsSetting() {
        return this.baseMapper.getPromotionsSetting();
    }

    /**
     * 判断条件满足才可修改优惠活动
     * 开始 < 结束
     * 当前 < 开始
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public void checkTime(Date nowTime, Date beginTime, Date endTime) {
        boolean checkOne = DateUtil.whichBig(beginTime, endTime);
        boolean checkTwo = DateUtil.whichBig(nowTime, beginTime);
        if (!checkOne) {
            throw new ApiException("优惠活动开始时间应小于优惠活动结束时间");
        }
        if (!checkTwo) {
            throw new ApiException("当前时间应小于优惠活动开始时间");
        }
    }
}