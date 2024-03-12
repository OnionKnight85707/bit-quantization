package com.mzwise.modules.ucenter.mapper;

import com.mzwise.modules.ucenter.entity.UcServiceChargeRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-05-24
 */
public interface UcServiceChargeRecordMapper extends BaseMapper<UcServiceChargeRecord> {
    /**
     * 统计近一周的服务费金额
     *
     * @return
     */
    BigDecimal sumServiceChargeLastWeek();
}
