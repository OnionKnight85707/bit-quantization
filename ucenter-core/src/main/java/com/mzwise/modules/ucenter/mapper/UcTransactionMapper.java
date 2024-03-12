package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcPartnerStat;
import com.mzwise.modules.ucenter.entity.UcTransaction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.ucenter.vo.AdminAssetsRecordVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
public interface UcTransactionMapper extends BaseMapper<UcTransaction> {

    /**
     * 后台分页条件查询资产记录
     *
     * @param page
     * @param nickname
     * @param phone
     * @param email
     * @param wrapper
     * @return
     */
    Page<AdminAssetsRecordVO> listAssetsRecord(Page<AdminAssetsRecordVO> page, @Param("nickname") String nickname, @Param("phone") String phone, @Param("email") String email, @Param(Constants.WRAPPER) Wrapper wrapper);


    List<UcPartnerStat> findYesterdayCommissionStatistics(LocalDate startDay, LocalDate endDay);

    UcPartnerStat findYesterdayCommissionStatisticsByMember(LocalDate startDay, LocalDate endDay,int memberId);
}
