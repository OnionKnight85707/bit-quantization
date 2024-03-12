package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcInviteRecord;

import java.sql.Date;

/**
 * @Author piao
 * @Date 2021/06/02
 */
public interface AdminInviteRecordService {
    /**
     * 分页条件查看邀请记录
     *
     * @param subNickname
     * @param supNickname
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<UcInviteRecord> listInviteRecord(String subNickname, String supNickname, Date beginDate, Date endDate, Integer pageNum, Integer pageSize);
}
