package com.mzwise.modules.quant.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.constant.*;
import com.mzwise.modules.quant.dto.UpdateQuantStrategyParam;
import com.mzwise.modules.quant.vo.*;

import java.sql.Date;

/**
 * @Author piao
 * @Date 2021/06/03
 */
public interface AdminQuantOrderService {
    /**
     * 后台分页查看所有量化订单
     *
     * @param nickname
     * @param phone
     * @param email
     * @param orderState
     * @param tradeType
     * @param platform
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<AdminQuantOrderVO> listQuantOrderSelective(Long quantId,String nickname, String phone, String email, Boolean isFinished, OrderStatusEnum status,
                                                    OrderStateEnum orderState,PositionSideEnum positionSide, TradeTypeEnum tradeType,QuantTypeEnum quantType, PlatformEnum platform,
                                                    Date beginDate, Date endDate, String symbolPair, Integer pageNum, Integer pageSize);

    /**
     * 后台分页查看量化机器人
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
    Page<AdminQuantVO> listQuantRobotPageSelective(String nickname, String phone, String email,
                                                   Date beginDate, Date endDate, Integer pageNum, Integer pageSize);

    /**
     * 后台分页展示自设指标
     *
     * @param nickname
     * @param phone
     * @param email
     * @param platform
     * @param type
     * @param status
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<AdminQuantStrategyVO> listQuantStrategySelective(String nickname, String phone, String email,
                                                          PlatformEnum platform, StrategyTypeEnum type, QuantStrategyStatusEnum status,
                                                          Date beginDate, Date endDate, Integer pageNum, Integer pageSize);

    /**
     * 后台仓位管理
     * @param symbol
     * @param side
     * @param nickname
     * @param phone
     * @param email
     * @param platform
     * @param type
     * @param status
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<QuantPositionManagementVO> postionManagementList(String symbol,PositionSideEnum side ,String nickname, String phone, String email,
                                                               PlatformEnum platform, StrategyTypeEnum type, QuantStrategyStatusEnum status,
                                                               Date beginDate, Date endDate, Integer pageNum, Integer pageSize);

    /**
     * 后台获取托管列表
     *
     * @param nickname
     * @param phone
     * @param email
     * @param platform
     * @param tradeType
     * @param status
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<AdminQuantTrustVO> listQuantTrustSelective(String nickname, String phone, String email,
                                                    PlatformEnum platform, TradeTypeEnum tradeType, QuantTrustStatusEnum status,
                                                    Date beginDate, Date endDate, Integer pageNum, Integer pageSize);

    /**
     * 修改量化策略
     * @param param
     */
    void updateQuantStrategy(UpdateQuantStrategyParam param);

}
