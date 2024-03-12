package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.entity.UcPartnerLevel;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 666
 * @since 2022-08-20
 */
public interface UcPartnerLevelSerivce extends IService<UcPartnerLevel> {

    /**
     * 推荐人如果不是合伙人则修改为是，白银合伙人
     * @param memberId
     */
    void checkIfRecommender(Long memberId);

    /**
     *  白银合伙人返佣比例
     * @return
     */
    BigDecimal levelCommissionRate();

    /**
     * 所有的合伙人等级
     * @return
     */
    List<UcPartnerLevel> findAll();

}
