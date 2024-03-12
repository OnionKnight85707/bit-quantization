package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.constant.DistributionProfitTypeEnum;
import com.mzwise.modules.ucenter.vo.AdminBTEDividendRecordVO;
import com.mzwise.modules.ucenter.vo.AdminDistributionProfitRecordVO;

import java.sql.Date;

/**
 * @Author piao
 * @Date 2021/06/21
 */
public interface AdminDistributionProfitService {
    /**
     * 分页条件查询量化分销记录
     *
     * @param nickname
     * @param phone
     * @param email
     * @param typeEnum
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<AdminDistributionProfitRecordVO> listSelective(String nickname, String phone, String email, DistributionProfitTypeEnum typeEnum, Date beginDate, Date endDate, Integer pageNum, Integer pageSize);

    /**
     * 分页条件查询平台币分红记录
     *
     * @param nickname
     * @param phone
     * @param email
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<AdminBTEDividendRecordVO> listBteDividendRecord(String nickname, String phone, String email, Date beginDate, Date endDate, Integer pageNum, Integer pageSize);
}
