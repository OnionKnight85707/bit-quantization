package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.ucenter.entity.UcProfit;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 用户收益mapper
 * @Author LiangZaiChao
 * @Date 2022/7/5 11:58
 */
public interface UcProfitMapper extends BaseMapper<UcProfit> {

    /**
     * 更新合约收益
     * @param memberId 用户id
     * @param profit 收益
     */
    void updateSwapProfit(@Param("memberId") Long memberId, @Param("profit") BigDecimal profit);

}
