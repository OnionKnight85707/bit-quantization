package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.exception.ApiException;
import com.mzwise.modules.ucenter.dto.UcBindBankCardParam;
import com.mzwise.modules.ucenter.entity.UcBindBankCard;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.mapper.UcBindBankCardMapper;
import com.mzwise.modules.ucenter.service.UcBindBankCardService;
import com.mzwise.modules.ucenter.service.UcMemberCacheService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.vo.BindBankCardVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Service
public class UcBindBankCardServiceImpl extends ServiceImpl<UcBindBankCardMapper, UcBindBankCard> implements UcBindBankCardService {
    @Autowired
    private UcBindBankCardMapper bindBankCardMapper;
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UcMemberCacheService memberCacheService;

    @Override
    public void bindBankCard(UcBindBankCardParam bindBankCardParam, Long memberId) {
        UcBindBankCard bindBankCard = new UcBindBankCard();
        BeanUtils.copyProperties(bindBankCardParam, bindBankCard);
        Long currentUserId = memberId;
        if (currentUserId == null) {
            throw new ApiException("unauthorized");
        }
        bindBankCard.setMemberId(currentUserId);
        save(bindBankCard);
        UcMember member = memberService.getById(currentUserId);
        member.setIsBindBankCard(true);
        memberService.updateById(member);
        memberCacheService.del(currentUserId);
    }

    @Override
    public Page<UcBindBankCard> listMyCards(Long memberId, Integer pageNum, Integer pageSize) {
        Page<UcBindBankCard> page = new Page<>(pageNum, pageSize);
        Long currentUserId = memberId;
        QueryWrapper<UcBindBankCard> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcBindBankCard::getMemberId, currentUserId);
        return page(page, wrapper);
    }

    @Override
    public Page<BindBankCardVO> listAllCard(String nickname, Integer pageNum, Integer pageSize) {
        Page<BindBankCardVO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcBindBankCard> wrapper = new QueryWrapper<>();
        wrapper.ge("t1.id", 1);
        return bindBankCardMapper.listAll(page, nickname, wrapper);
    }


}
