package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.ucenter.entity.UcPartnerLevel;
import com.mzwise.modules.ucenter.vo.PartnerCommissionInfoVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 合伙人等级映射
 * @author 666
 * @since 2022-08-20
 */
public interface UcPartnerLevelMapper extends BaseMapper<UcPartnerLevel> {

    /**
     * 查询合伙人佣金相关信息
     * @param memberId
     * @return
     */
    PartnerCommissionInfoVo partnerCommissionInfo(@Param("memberId") Long memberId);


    /**
     *  白银合伙人返佣比例
     * @return
     */
    BigDecimal levelCommissionRate();
}
