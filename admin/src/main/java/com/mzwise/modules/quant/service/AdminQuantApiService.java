package com.mzwise.modules.quant.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.constant.PlatformEnum;
import com.mzwise.modules.quant.vo.AdminQuantApiAccessVO;

import java.sql.Date;

/**
 * @Author piao
 * @Date 2021/06/09
 */
public interface AdminQuantApiService {
    /**
     * 分页展示用户火币等API绑定信息
     *
     * @param nickname
     * @param phone
     * @param email
     * @param platform
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<AdminQuantApiAccessVO> listAllApiSelective(
            String nickname, String phone, String email,
            PlatformEnum platform,
            Date beginDate, Date endDate, Integer pageNum, Integer pageSize);
}
