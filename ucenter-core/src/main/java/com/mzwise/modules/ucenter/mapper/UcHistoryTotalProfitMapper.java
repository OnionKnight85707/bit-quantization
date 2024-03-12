package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.ucenter.entity.UcHistoryTotalProfit;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-05-27
 */
public interface UcHistoryTotalProfitMapper extends BaseMapper<UcHistoryTotalProfit> {

    /**
     * 计算某用户A社区量化历史总收益
     *
     * @param memberIdList 用户A下级团队的所有人（不包括A）
     * @return 收益值
     */
    BigDecimal calMyTeamHistoryTotalProfit(@Param("memberIdList") List<Long> memberIdList);

    /**
     * 计算社区平台币历史总收益
     *
     * @param memberIdList 用户A下级团队的所有人（不包括A）
     * @return 收益值
     */
    BigDecimal calMyTeamBTEHistoryTotalProfit(@Param("memberIdList") List<Long> memberIdList);
}
