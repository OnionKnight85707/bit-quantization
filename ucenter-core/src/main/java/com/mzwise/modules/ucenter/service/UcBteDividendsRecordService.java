package com.mzwise.modules.ucenter.service;

import com.mzwise.modules.ucenter.entity.UcBteDividendsRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author admin
 * @since 2021-05-26
 */
public interface UcBteDividendsRecordService extends IService<UcBteDividendsRecord> {

    /**
     * 创建平台币分红记录
     *
     * @param memberId
     * @param amount
     */
    void create(Long memberId, BigDecimal amount);
}
