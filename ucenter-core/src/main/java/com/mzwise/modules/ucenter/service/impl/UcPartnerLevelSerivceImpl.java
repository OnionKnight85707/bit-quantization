package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.constant.PartnerLevelEnum;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcPartnerLevel;
import com.mzwise.modules.ucenter.mapper.UcPartnerLevelMapper;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.service.UcPartnerLevelSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 666
 * @since 2022-08-20
 */
@Service
public class UcPartnerLevelSerivceImpl extends ServiceImpl<UcPartnerLevelMapper, UcPartnerLevel> implements UcPartnerLevelSerivce {

    @Autowired
    private UcMemberService ucMemberService;

    /**
     * 推荐人如果不是合伙人则修改为是，白银合伙人
     *
     * @param memberId
     */
    @Override
    public void checkIfRecommender(Long memberId) {
        UcMember ucMember = ucMemberService.getById(memberId);
        if (!ucMember.getIsPartner()) {
            ucMember.setIsPartner(true);
            ucMember.setPartnerLevelId(PartnerLevelEnum.SILVER.getValue());
            ucMemberService.updateById(ucMember);
        }
    }

    @Override
    public BigDecimal levelCommissionRate() {
        return baseMapper.levelCommissionRate();
    }

    /**
     * 所有的合伙人等级
     *
     * @return
     */
    @Override
    public List<UcPartnerLevel> findAll() {
        return this.list();
    }

}
