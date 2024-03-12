package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.ucenter.entity.UcPromotionsSetting;

/**
 *  <p>
 *  后台用户登录日志表 Mapper 接口
 *  </p>
 */
public interface UcPromotionsSettingMapper extends BaseMapper<UcPromotionsSetting> {

    /**
     * 查询优惠活动
     * @return
     */
    UcPromotionsSetting getPromotionsSetting();

}
