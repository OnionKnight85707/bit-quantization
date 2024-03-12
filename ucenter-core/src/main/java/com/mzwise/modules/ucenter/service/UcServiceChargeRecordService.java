package com.mzwise.modules.ucenter.service;

import com.mzwise.modules.ucenter.entity.UcServiceChargeRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author admin
 * @since 2021-05-24
 */
public interface UcServiceChargeRecordService extends IService<UcServiceChargeRecord> {

    /**
     * 生成服务金记录
     *
     * @param memberId
     * @param amount
     */
    void create(Long memberId, BigDecimal amount);

    /**
     * 统计近一周服务费充值总金额
     *
     * @return
     */
    BigDecimal sumServiceChargeLastWeek();
}
