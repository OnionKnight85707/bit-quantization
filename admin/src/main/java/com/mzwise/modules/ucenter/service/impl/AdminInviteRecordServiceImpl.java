package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcInviteRecord;
import com.mzwise.modules.ucenter.service.AdminInviteRecordService;
import com.mzwise.modules.ucenter.service.UcInviteRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * @Author piao
 * @Date 2021/06/02
 */
@Service
public class AdminInviteRecordServiceImpl implements AdminInviteRecordService {
    @Autowired
    private UcInviteRecordService inviteRecordService;

    @Override
    public Page<UcInviteRecord> listInviteRecord(String subNickname, String supNickname, Date beginDate, Date endDate, Integer pageNum, Integer pageSize) {
        Page<UcInviteRecord> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcInviteRecord> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(subNickname)) {
            wrapper.lambda().likeRight(UcInviteRecord::getSubMemberNickname, subNickname);
        }
        if (StringUtils.isNotBlank(supNickname)) {
            wrapper.lambda().likeRight(UcInviteRecord::getSupMemberNickname, supNickname);
        }
        if (beginDate != null && endDate != null) {
            wrapper.lambda().between(UcInviteRecord::getCreateTime, beginDate, endDate);
        }
        wrapper.lambda().orderByDesc(UcInviteRecord::getCreateTime);
        return inviteRecordService.page(page, wrapper);
    }
}
