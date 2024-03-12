package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcInviteRecord;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.mapper.UcInviteRecordMapper;
import com.mzwise.modules.ucenter.service.UcInviteRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.vo.MySubMembersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-05-19
 */
@Service
public class UcInviteRecordServiceImpl extends ServiceImpl<UcInviteRecordMapper, UcInviteRecord> implements UcInviteRecordService {
    @Autowired
    private UcMemberService memberService;

    @Override
    public void create(Long subMemberId, Long supMemberId) {
        UcInviteRecord inviteRecord = new UcInviteRecord();
        UcMember subMember = memberService.getById(subMemberId);
        UcMember supMember = memberService.getById(supMemberId);
        if (subMember == null || supMember == null) {
            return;
        }
        inviteRecord.setSubMemberId(subMemberId);
        inviteRecord.setSubMemberNickname(subMember.getNickname());
        inviteRecord.setSupMemberId(supMemberId);
        inviteRecord.setSupMemberNickname(supMember.getNickname());
        inviteRecord.setIsEffective(false);
        save(inviteRecord);
    }

    @Override
    public UcInviteRecord queryBySubMemberId(Long subMemberId) {
        QueryWrapper<UcInviteRecord> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcInviteRecord::getSubMemberId, subMemberId);
        return getOne(wrapper);
    }

    @Override
    public Page<MySubMembersVO> queryMySubMembers(Long supMemberId, Integer pageNum, Integer pageSize) {
        Page<MySubMembersVO> page = new Page<>(pageNum, pageSize);
        return baseMapper.mySubMember(page, supMemberId);
    }


}
