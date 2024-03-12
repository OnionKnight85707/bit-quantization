package com.mzwise.modules.quant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.exception.ApiException;
import com.mzwise.constant.*;
import com.mzwise.modules.quant.dto.PositionManagementListDto;
import com.mzwise.modules.quant.dto.PositionManagementListParam;
import com.mzwise.modules.quant.dto.UpdateQuantStrategyParam;
import com.mzwise.modules.quant.entity.*;
import com.mzwise.modules.quant.mapper.QuantOrderMapper;
import com.mzwise.modules.quant.mapper.QuantStrategyMapper;
import com.mzwise.modules.quant.mapper.QuantTrustMapper;
import com.mzwise.modules.quant.service.AdminQuantOrderService;
import com.mzwise.modules.quant.service.QuantStrategyService;
import com.mzwise.modules.quant.service.QuantTrendService;
import com.mzwise.modules.quant.vo.*;
import com.mzwise.modules.quant.mapper.UcQuantMapper;
import com.mzwise.modules.ucenter.mapper.UcProfitDetailMapper;
import com.mzwise.unifyexchange.beans.AccountInfo;
import com.mzwise.unifyexchange.beans.UnifyPosition;
import com.mzwise.modules.quant.vo.*;
import com.mzwise.modules.quant.mapper.UcQuantMapper;
import com.mzwise.modules.ucenter.vo.AccountAssetResponse;
import com.mzwise.unifyexchange.beans.AccountInfo;
import com.mzwise.unifyexchange.beans.UnifyPosition;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Author piao
 * @Date 2021/06/03
 */
@Service
public class AdminQuantOrderServiceImpl implements AdminQuantOrderService {
    @Autowired
    private QuantOrderMapper quantOrderMapper;
    @Autowired
    private UcQuantMapper quantMapper;
    @Autowired
    private QuantStrategyMapper strategyMapper;
    @Autowired
    private QuantTrustMapper trustMapper;
    @Autowired

    private UcProfitDetailMapper ucProfitDetailMapper;
    @Autowired
    private QuantStrategyService quantStrategyService;

    @Autowired
    private QuantTrendService quantTrendService;



    @Override
    public Page<AdminQuantOrderVO> listQuantOrderSelective(Long quantId, String nickname, String phone, String email, Boolean isFinished, OrderStatusEnum status,
                                                           OrderStateEnum orderState, PositionSideEnum positionSide, TradeTypeEnum tradeType, QuantTypeEnum quantType, PlatformEnum platform,
                                                           Date beginDate, Date endDate, String symbolPair, Integer pageNum, Integer pageSize) {
        Page<AdminQuantOrderVO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<QuantOrder> wrapper = new QueryWrapper<>();
        wrapper.ge("qo.id", 1);

        if (isFinished != null) {
            if (isFinished) {
                wrapper.eq("qo.status", OrderStatusEnum.OVER);
            } else {
                wrapper.ne("qo.status", OrderStatusEnum.OVER);
            }
        }
        if (status != null) {
            wrapper.eq("qo.status", status);
        }
        if (positionSide != null) {
            wrapper.lambda().eq(QuantOrder::getPositionSide, positionSide);
        }
        if (quantId != null) {
            wrapper.lambda().eq(QuantOrder::getQuantId, quantId);
        }
        if (orderState != null) {
            wrapper.lambda().eq(QuantOrder::getState, orderState);
        }
        if (tradeType != null) {
            wrapper.lambda().eq(QuantOrder::getTradeType, tradeType);
        }
        if (quantType != null) {
            wrapper.lambda().eq(QuantOrder::getQuantType, quantType);
        }
        if (platform != null) {
            wrapper.lambda().eq(QuantOrder::getPlatform, platform);
        }
        if (beginDate != null && endDate != null) {
            wrapper.lambda().between(QuantOrder::getCreateTime, beginDate, endDate);
        }
        if (StringUtils.isNotBlank(symbolPair)) {
            wrapper.lambda().eq(QuantOrder::getSymbolPair, symbolPair);
        }
        return quantOrderMapper.listPage(page, nickname, phone, email, wrapper);
    }

    @Override
    public Page<AdminQuantVO> listQuantRobotPageSelective(String nickname, String phone, String email,
                                                          Date beginDate, Date endDate, Integer pageNum, Integer pageSize) {
        Page<AdminQuantVO> page = new Page<>(pageNum, pageSize);
        return quantMapper.listQuantPageSelective(page, nickname, phone, email);
    }

    @Override
    public Page<AdminQuantStrategyVO> listQuantStrategySelective(String nickname, String phone, String email,
                                                                 PlatformEnum platform, StrategyTypeEnum type, QuantStrategyStatusEnum status,
                                                                 Date beginDate, Date endDate, Integer pageNum, Integer pageSize) {
        Page<AdminQuantStrategyVO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<QuantStrategy> wrapper = new QueryWrapper<>();
        wrapper.ge("qs.id", 1L);
        if (platform != null) {
            wrapper.lambda().eq(QuantStrategy::getPlatform, platform);
        }
        if (type != null) {
            wrapper.lambda().eq(QuantStrategy::getType, type);
        }
        if (status != null) {
            wrapper.eq("qs.status", status);
        }
        if (beginDate != null && endDate != null) {
            wrapper.lambda().between(QuantStrategy::getCreateTime, beginDate, endDate);
        }
        Page<AdminQuantStrategyVO> result = strategyMapper.listQuantStrategySelective(page, nickname, phone, email, wrapper);
        result.getRecords().forEach(v -> {
            v.fillField();
            if (StrategyTypeEnum.TREND == v.getType()) {
                v.setSymbolPair("智能选币");
            }
            v.setTotalFloatingLoss(quantTrendService.getFloatProfit(v.getId().intValue()));
            v.setTodayProfit(v.getTodayProfit().setScale(2,BigDecimal.ROUND_HALF_UP));
            if(v.getTotalProfit()!=null)
                 v.setTotalProfit(v.getTotalProfit().setScale(2,BigDecimal.ROUND_HALF_UP));

        });
        return result;
    }

    @Override
    public Page<QuantPositionManagementVO> postionManagementList(String symbol,PositionSideEnum side ,String nickname, String phone, String email,
                                                                      PlatformEnum platform, StrategyTypeEnum type, QuantStrategyStatusEnum status,
                                                                      Date beginDate, Date endDate, Integer pageNum, Integer pageSize) {
        Page<AdminQuantStrategyVO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<QuantStrategy> wrapper = new QueryWrapper<>();
        wrapper.ge("qs.id", 1L);
        if (platform != null) {
            wrapper.lambda().eq(QuantStrategy::getPlatform, platform);
        }
        if (type != null) {
            wrapper.lambda().eq(QuantStrategy::getType, type);
        }
        if (status != null) {
            wrapper.eq("qs.status", status);
        }
        if (beginDate != null && endDate != null) {
            wrapper.lambda().between(QuantStrategy::getCreateTime, beginDate, endDate);
        }
        Page<QuantPositionManagementVO> resultVO = new Page<QuantPositionManagementVO> ();
        Page<AdminQuantStrategyVO> result = strategyMapper.listQuantStrategySelective(page, nickname, phone, email, wrapper);
        resultVO.setSize(result.getSize());
        resultVO.setTotal(result.getTotal());
        resultVO.setPages(result.getPages());
        List<QuantPositionManagementVO> managementVOList = new ArrayList<>();
        result.getRecords().forEach(av -> {
            QuantPositionManagementVO v = getManagementVO(av);
            //总盈利
            BigDecimal allProfit = ucProfitDetailMapper.queryAllProfit(v.getId());
            v.setTotalProfit(allProfit);
            //浮动盈亏
            AccountInfo.Response asset = quantStrategyService.getAccountAsset(v.getId());
            if (ObjectUtils.isEmpty(asset)) {
                return;
            }
            List<UnifyPosition> positions = asset.getPositions();
            List<WalletPosition> walletPositionList = new ArrayList<>();
            BigDecimal total=BigDecimal.ZERO;
            if (positions != null ) {
                for (UnifyPosition temp : positions) {
                    if (symbol != null && !toPlatfromSymbol(temp.getSymbol()).equals(symbol)) {
                        continue;
                    }
                    if (side!=null && !temp.getPositionSide().equals(side.getName())) {
                        continue;
                    }
                    WalletPosition walletPosition = toWallet(temp);
                    if (temp.getUnrealizedProfit() != null) {
                        walletPosition.setUnrealizedProfit(temp.getUnrealizedProfit().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    }
                    //总浮亏
                    total=total.add((new BigDecimal(walletPosition.getUnrealizedProfit())));
                    walletPositionList.add(walletPosition);
                    walletPosition.setId(v.getId());
                    walletPosition.setPositionSide(temp.getPositionSide());
                }
            }
            v.setTotalFloatingLoss(total);
            v.setPositionList(walletPositionList);
            if (walletPositionList.size()>0) {
                managementVOList.add(v);
            }

        });
        resultVO.setRecords(managementVOList);
        return resultVO;
    }

    /**
     * 将 交易所返回的币对统一转换成 BTC/USDT 格式
     * @param symbol  BTCUSDT
     * @return
     */
    public static String toPlatfromSymbol(String symbol)
    {
        if (symbol!=null && symbol.contains("/")) {
            return symbol.toUpperCase();
        }
        return  symbol.replace("USDT","/USDT");
    }

    public static WalletPosition toWallet( UnifyPosition pos) {
        WalletPosition walletPosition = new WalletPosition();
        BeanUtils.copyProperties(pos, walletPosition);
        walletPosition.setLeverage(pos.getLeverage());
        walletPosition.setInitialMargin(pos.getInitialMargin().toPlainString());
        walletPosition.setSymbol(toPlatfromSymbol(pos.getSymbol()));
        return walletPosition;
    }

    public static  QuantPositionManagementVO getManagementVO(AdminQuantStrategyVO v) {
        QuantPositionManagementVO result = new QuantPositionManagementVO();
        BeanUtils.copyProperties(v, result);
        return result;
    }

    @Override
    public Page<AdminQuantTrustVO> listQuantTrustSelective(String nickname, String phone, String email,
                                                           PlatformEnum platform, TradeTypeEnum tradeType, QuantTrustStatusEnum status,
                                                           Date beginDate, Date endDate, Integer pageNum, Integer pageSize) {
        Page<AdminQuantTrustVO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<QuantTrust> wrapper = new QueryWrapper<>();
        wrapper.ge("qt.id", 1L);
        if (platform != null) {
            wrapper.lambda().eq(QuantTrust::getPlatform, platform);
        }
        if (tradeType != null) {
            wrapper.lambda().eq(QuantTrust::getTradeType, tradeType);
        }
        if (status != null) {
            wrapper.eq("qt.status", status);
        }
        if (beginDate != null && endDate != null) {
            wrapper.lambda().between(QuantTrust::getCreateTime, beginDate, endDate);
        }
        return trustMapper.listQuantTrustSelective(page, nickname, phone, email, wrapper);
    }

    /**
     * 修改量化策略
     *
     * @param param
     */
    @Override
    public void updateQuantStrategy(UpdateQuantStrategyParam param) {
        QuantStrategy quantStrategy = strategyMapper.selectById(param.getId());
        if (ObjectUtils.isEmpty(quantStrategy)) {
            return;
        }
        // 检查数字
        strategyCheckNumber(param, quantStrategy.getType());
        // 趋势策略下 激活状态也能修改指标 合约对冲策略不能修改指标
        if ((quantStrategy.getType()==StrategyTypeEnum.FUTURE)&&(quantStrategy.getStatus() == QuantStrategyStatusEnum.ACTIVE)) {
            throw new ApiException("激活状态下不能修改指标");
        }
        switch (quantStrategy.getType()){
            case FUTURE:
                quantStrategy.setFuture(param.getFuture());
                break;
            case TREND:
                quantStrategy.setTrend(param.getTrend());
                break;
            case UNIFIED:
                quantStrategy.setUnified(param.getUnified());
                break;
            default:
                throw new ApiException("后台修改量化策略：策略类型错误");
        }
        quantStrategy.toPivotPoints();
        if(param.getPosition()!=null&&(param.getPosition().compareTo(BigDecimal.ZERO)<0)){
            throw  new ApiException("仓位金额不能为负数");
        }
        quantStrategy.setPosition(param.getPosition());
        quantStrategy.setLeverage(param.getLeverage());
        strategyMapper.updateById(quantStrategy);
    }

    /**
     * 检查策略数字
     * @param param
     * @param type
     */
    private void strategyCheckNumber(UpdateQuantStrategyParam param, StrategyTypeEnum type) {
        if (param.getPosition().compareTo(BigDecimal.ZERO) < 1) {
            throw new ApiException("仓位必须为正数");
        }
        if (type == StrategyTypeEnum.FUTURE) {
            QuantStrategyFUTURE.checkNum(param.getFuture());
        } else if (type == StrategyTypeEnum.TREND) {
            QuantStrategyTrend.checkNum(param.getTrend());
        }
    }

}
