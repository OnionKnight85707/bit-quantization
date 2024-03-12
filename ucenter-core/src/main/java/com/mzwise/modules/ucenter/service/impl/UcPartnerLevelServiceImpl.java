package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.constant.PartnerLevelEnum;
import com.mzwise.modules.ucenter.entity.UcPartnerLevel;
import com.mzwise.modules.ucenter.mapper.UcPartnerLevelMapper;
import com.mzwise.modules.ucenter.service.UcPartnerLevelService;
import com.mzwise.modules.ucenter.vo.PartnerCommissionCalcVo;
import com.mzwise.modules.ucenter.vo.PartnerCommissionInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 合伙人等级服务实现
 * @author 666
 * @since 2022-08-20
 */
@Slf4j
@Service
public class UcPartnerLevelServiceImpl extends ServiceImpl<UcPartnerLevelMapper, UcPartnerLevel> implements UcPartnerLevelService {

    /**
     * 获取用户的合伙人佣金比例
     *
     * @param memberId                     用户id
     * @param businessDirectParentId       成交者直属上级id
     * @param lastSetPartnerCommissionRate 上一级设置的合伙人佣金比例(member表设置的)
     * @return
     */
    @Override
    public PartnerCommissionCalcVo getPartnerCommissionRate(Long memberId, Long businessDirectParentId, BigDecimal lastSetPartnerCommissionRate) {
        PartnerCommissionCalcVo returnVo = new PartnerCommissionCalcVo();
        // 查询合伙人佣金相关信息
        PartnerCommissionInfoVo infoVo = baseMapper.partnerCommissionInfo(memberId);
        returnVo.setParentId(infoVo.getParentId());
        returnVo.setSetRate(infoVo.getSetRate());
        if ( ! infoVo.getIsPartner()) {
            // 如果该用户不是合伙人，直接返回0
            returnVo.setCalcRate(BigDecimal.ZERO);
        } else if (PartnerLevelEnum.SILVER == infoVo.getLevel()) {
            // 白银合伙人下：如果设置的合伙人佣金比例为0，需要比较是否是 成交者直属上级
            if (infoVo.getSetRate().compareTo(BigDecimal.ZERO) == 0) {
                // 如果该用户是 成交者直属上级，佣金比例为白银合伙人等级的佣金比例3%; 否则是0%
                if (memberId.equals(businessDirectParentId)) {
                    returnVo.setCalcRate(infoVo.getLevelRate());
                } else {
                    returnVo.setCalcRate(BigDecimal.ZERO);
                }
            } else {
                // 白银合伙人下：如果设置的合伙人佣金比例不为0，合伙人佣金比例 = 自己的设置比例 - 上一级设置的合伙人佣金比例
                returnVo.setCalcRate(infoVo.getSetRate().subtract(lastSetPartnerCommissionRate));
            }
        } else if (PartnerLevelEnum.GOLD == infoVo.getLevel() || PartnerLevelEnum.DIAMOND == infoVo.getLevel()) {
            // 黄金/钻石合伙人下：合伙人佣金比例 = 自己的设置比例 - 上一级设置的合伙人佣金比例
            returnVo.setCalcRate(infoVo.getSetRate().subtract(lastSetPartnerCommissionRate));
        }
        return returnVo;
    }

}
