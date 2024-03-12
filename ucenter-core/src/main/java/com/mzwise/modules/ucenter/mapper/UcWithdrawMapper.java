package com.mzwise.modules.ucenter.mapper;

import com.mzwise.modules.ucenter.entity.UcWithdraw;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.ucenter.vo.AdminRechargeVO;
import com.mzwise.modules.ucenter.vo.AdminWithdrawalVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
public interface UcWithdrawMapper extends BaseMapper<UcWithdraw> {

    /**
     * 查询某个会员的总提币金额及笔数
     *
     * @param memberId
     * @return
     */
    AdminWithdrawalVO calWithdrawalNumAndAmount(@Param("memberId") Long memberId);
}
