package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.util.IdCardVerifyUtil;
import com.mzwise.constant.IdCardTypeEnum;
import com.mzwise.constant.RealNameStatusEnum;
import com.mzwise.modules.ucenter.dto.UcRealNameFailedParam;
import com.mzwise.modules.ucenter.dto.UcRealNameSubmitApplyParam;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcRealNameVerified;
import com.mzwise.modules.ucenter.mapper.UcRealNameVerifiedMapper;
import com.mzwise.modules.ucenter.service.UcMemberCacheService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.service.UcRealNameVerifiedService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Service
public class UcRealNameVerifiedServiceImpl extends ServiceImpl<UcRealNameVerifiedMapper, UcRealNameVerified> implements UcRealNameVerifiedService {
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UcMemberCacheService memberCacheService;

    @Override
    public void submitApply(Long memberId, UcRealNameSubmitApplyParam realNameSubmitApplyParam) {
        UcMember member = memberService.getById(memberId);
        if (member.getRealNameStatus().equals(RealNameStatusEnum.CHECKING)) {
            throw new ApiException("do_not_submit_real_name_authentication_repeatedly");
        }
        if (member.getRealNameStatus().equals(RealNameStatusEnum.REAL_NAMED)) {
            throw new ApiException("you_have_successfully_authenticated_with_your_real_name");
        }

        UcRealNameVerified ucRealNameVerified = new UcRealNameVerified();
        BeanUtils.copyProperties(realNameSubmitApplyParam, ucRealNameVerified);
        if (realNameSubmitApplyParam.getIdentityCardType().equals(IdCardTypeEnum.IDENTITY_CARD)) {
            Assert.isTrue(IdCardVerifyUtil.verify(ucRealNameVerified.getIdentityCardNumber()), "incorrect_identity_card_number_format");
        }
        Assert.isTrue(!StringUtils.isNotBlank(ucRealNameVerified.getRealName()), "real_name_cannot_be_null");
        Assert.isTrue(!(StringUtils.isNotBlank(ucRealNameVerified.getIdentityCardFront()) || StringUtils.isEmpty(ucRealNameVerified.getIdentityCardBack())), "identity_card_photo_cannot_be_null");
        ucRealNameVerified.setStatus(RealNameStatusEnum.CHECKING);

        ucRealNameVerified.setMemberId(memberId);
        save(ucRealNameVerified);
        member.setRealNameStatus(RealNameStatusEnum.CHECKING);
        memberService.updateById(member);
        memberCacheService.del(memberId);
    }

    @Override
    public void pass(Long realNameVerifiedId) {
        UcRealNameVerified realNameVerified = getById(realNameVerifiedId);
        if (realNameVerified == null) {
            throw new ApiException("data_not_found");
        }
        realNameVerified.setStatus(RealNameStatusEnum.REAL_NAMED);
        updateById(realNameVerified);

        UcMember member = memberService.getById(realNameVerified.getMemberId());
        member.setRealName(realNameVerified.getRealName());
        member.setIdNumber(realNameVerified.getIdentityCardNumber());
        member.setRealNameStatus(RealNameStatusEnum.REAL_NAMED);
        memberService.updateById(member);
        memberCacheService.del(member.getId());
    }

    @Override
    public void failed(UcRealNameFailedParam realNameFailedParam) {
        UcRealNameVerified realNameVerified = getById(realNameFailedParam.getRealNameVerifiedId());
        realNameVerified.setStatus(RealNameStatusEnum.REAL_NAME_FAILED);
        realNameVerified.setFailedReason(realNameFailedParam.getFailedReason());
        updateById(realNameVerified);

        UcMember member = memberService.getById(realNameVerified.getMemberId());
        member.setRealNameStatus(RealNameStatusEnum.REAL_NAME_FAILED);
        memberService.updateById(member);
        memberCacheService.del(member.getId());
    }

    @Override
    public UcRealNameVerified queryLastByMemberId(Long memberId) {
        QueryWrapper<UcRealNameVerified> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcRealNameVerified::getMemberId, memberId)
                .orderByDesc(UcRealNameVerified::getCreateTime)
        ;
        List<UcRealNameVerified> verifiedList = list(wrapper);
        return (verifiedList.isEmpty() ? null : verifiedList.get(0));
    }

    @Override
    public Page<UcRealNameVerified> listAll(RealNameStatusEnum status, String realName, Integer pageNum, Integer pageSize) {
        Page<UcRealNameVerified> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcRealNameVerified> wrapper = new QueryWrapper<>();
        if (status != null) {
            wrapper.lambda().eq(UcRealNameVerified::getStatus, status);
        }
        if (StringUtils.isNotBlank(realName)) {
            wrapper.lambda().like(UcRealNameVerified::getRealName, realName);
        }
        wrapper.lambda().orderByDesc(UcRealNameVerified::getCreateTime);
        return page(page, wrapper);
    }
}
