package com.mzwise.modules.quant.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.*;
import com.mzwise.modules.admin.entity.UmsAdminLog;
import com.mzwise.modules.admin.service.UmsAdminLogService;
import com.mzwise.modules.quant.dto.*;
import com.mzwise.modules.quant.entity.*;
import com.mzwise.modules.quant.service.*;
import com.mzwise.modules.quant.vo.*;
import com.mzwise.modules.ucenter.dto.UcPartnerParam;
import com.mzwise.modules.ucenter.vo.AccountAssetResponse;
import com.mzwise.modules.ucenter.vo.WalletPosition;
import com.mzwise.unifyexchange.beans.AccountInfo;
import com.mzwise.unifyexchange.beans.UnifyPosition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author piao
 * @Date 2021/06/04
 */
@Slf4j
@Api(tags = "后台量化交易")
@RestController
@RequestMapping("/admin/quant")
public class AdminQuantOrderController {
    @Autowired
    private AdminQuantOrderService adminQuantOrderService;
    @Autowired
    private QuantStrategyService quantStrategyService;
    @Autowired
    private QuantOrderService orderService;
    @Autowired
    private QuantTrustService quantTrustService;
    @Autowired
    private QuantTrustPositionService quantTrustPositionService;
    @Autowired
    private QuantApiAccessService quantApiAccessService;
    @Autowired
    private QuantStrategyDefaultParametersService defaultParametersService;
    @Autowired
    private UmsAdminLogService adminLogService;

    @Autowired
    private ReadOnlyActionService readOnlyActionService;

    @ApiOperation("后台分页查看所有量化订单")
    @GetMapping("/list")
    public CommonResult<Page<AdminQuantOrderVO>> listQuantOrderSelective(@ApiParam("量化id") Long quantId,
                                                                         @ApiParam("昵称") String nickname,
                                                                         @ApiParam("手机") String phone,
                                                                         @ApiParam("邮箱") String email,
                                                                         @ApiParam("是否是历史订单") Boolean isFinished,
                                                                         @ApiParam("订单状态，NEW：未成交，FILLED：已成交， CLOSING：出售中，OVER：已完成") OrderStatusEnum status,
                                                                         @ApiParam("订单状态，NORMARL：正常，ERROR：异常") OrderStateEnum orderState,
                                                                         @ApiParam("做多做空") PositionSideEnum positionSide,
                                                                         @ApiParam("交易类型，EXCHANGE：现货，SWAP：合约") TradeTypeEnum tradeType,
                                                                         @ApiParam("量化种类 TRUST 托管 STRATEGY 自设指标") QuantTypeEnum quantType,
                                                                         @ApiParam("交易平台，HUOBI:火币，BINANCE：币安，OKEX：okex") PlatformEnum platform,
                                                                         @ApiParam("开始时间") Date beginDate,
                                                                         @ApiParam("结束时间") Date endDate,
                                                                         @ApiParam("币对") String symbolPair,
                                                                         @RequestParam(defaultValue = "1") @ApiParam("当前页") Integer pageNum,
                                                                         @RequestParam(defaultValue = "10") @ApiParam("每页限制") Integer pageSize) {
        return CommonResult.success(adminQuantOrderService.listQuantOrderSelective(quantId, nickname, phone, email, isFinished, status, orderState, positionSide, tradeType, quantType, platform, beginDate, endDate, symbolPair, pageNum, pageSize));
    }

    @ApiOperation("查看量化订单详情")
    @GetMapping("/order/{id}")
    public CommonResult<QuantOrder> orderDetail(@PathVariable String id) {
        return CommonResult.success(orderService.getById(id));
    }

    @ApiOperation("查看自设指标详情")
    @GetMapping("/strategy/{id}")
    public CommonResult<QuantStrategy> strategyDetail(@PathVariable Long id) {
        QuantStrategy strategy = quantStrategyService.getById(id);
        strategy.fillField();
        return CommonResult.success(strategy);
    }

    @ApiOperation("查看托管详情")
    @GetMapping("/trust/{id}")
    public CommonResult<QuantTrust> trustDetail(@PathVariable Long id) {
        return CommonResult.success(quantTrustService.getById(id));
    }

    @ApiOperation("量化机器人列表")
    @GetMapping("/robot/list")
    public CommonResult<Page<AdminQuantVO>> listPageSelective(@ApiParam("昵称") String nickname,
                                                              @ApiParam("手机") String phone,
                                                              @ApiParam("邮箱") String email,
                                                              @ApiParam("开始时间") Date beginDate,
                                                              @ApiParam("结束时间") Date endDate,
                                                              @RequestParam(defaultValue = "1") @ApiParam("当前页") Integer pageNum,
                                                              @RequestParam(defaultValue = "10") @ApiParam("每页限制") Integer pageSize) {
        return CommonResult.success(adminQuantOrderService.listQuantRobotPageSelective(nickname, phone, email, beginDate, endDate, pageNum, pageSize));
    }

    @ApiOperation("后台分页查看自设指标")
    @GetMapping("/list/self-designed-indicator")
    public CommonResult<Page<AdminQuantStrategyVO>> listQuantStrategySelective(@ApiParam("昵称") String nickname,
                                                                               @ApiParam("手机") String phone,
                                                                               @ApiParam("邮箱") String email,
                                                                               @ApiParam(value = "自设指标平台") PlatformEnum platform,
                                                                               @ApiParam("自设指标类型") StrategyTypeEnum type,
                                                                               @ApiParam("自设指标状态") QuantStrategyStatusEnum status,
                                                                               @ApiParam("开始时间") Date beginDate,
                                                                               @ApiParam("结束时间") Date endDate,
                                                                               @RequestParam(defaultValue = "1") @ApiParam("当前页") Integer pageNum,
                                                                               @RequestParam(defaultValue = "10") @ApiParam("每页限制") Integer pageSize) {
        return CommonResult.success(adminQuantOrderService.listQuantStrategySelective(nickname, phone, email, platform, type, status, beginDate, endDate, pageNum, pageSize));
    }

    @ApiOperation("后台仓位管理")
    @GetMapping("/list/position-management")
    public CommonResult<Page<QuantPositionManagementVO>> postionManagementList(@ApiParam("昵称") String nickname,
                                                                                    @ApiParam("手机") String phone,
                                                                                    @ApiParam("邮箱") String email,
                                                                                    @ApiParam("交易币对") String symbol,
                                                                                    @ApiParam("方向") PositionSideEnum side,
                                                                                    @ApiParam(value = "自设指标平台") PlatformEnum platform,
                                                                                    @ApiParam("自设指标类型") StrategyTypeEnum type,
                                                                                    @ApiParam("自设指标状态") QuantStrategyStatusEnum status,
                                                                                    @ApiParam("开始时间") Date beginDate,
                                                                                    @ApiParam("结束时间") Date endDate,
                                                                                    @RequestParam(defaultValue = "1") @ApiParam("当前页") Integer pageNum,
                                                                                    @RequestParam(defaultValue = "10") @ApiParam("每页限制") Integer pageSize) {
        return CommonResult.success(adminQuantOrderService.postionManagementList(symbol,side,nickname, phone, email, platform, type, status, beginDate, endDate, pageNum, pageSize));
    }

    @ApiOperation(value = "取消自定义指标")
    @PutMapping("cancel/self-designed-indicator/{id}")
    public CommonResult cancelSelfDesignedIndicator(@PathVariable Long id) {
        CommonResult re=quantStrategyService.cancel(id);
        // 记录日志
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.DELETE.getValue(), AdminLogModuleEnum.STRATEGY_CLOSE_POSITION.getValue(),
                null, JSON.toJSONString(id), "自设指标-平仓"));
        return re;
    }


    @ApiOperation("后台分页查看托管列表")
    @GetMapping("/list/trusteeship")
    public CommonResult<Page<AdminQuantTrustVO>> listQuantTrustSelective(@ApiParam("昵称") String nickname,
                                                                         @ApiParam("手机") String phone,
                                                                         @ApiParam("邮箱") String email,
                                                                         @ApiParam(value = "托管平台") PlatformEnum platform,
                                                                         @ApiParam("托管类型") TradeTypeEnum tradeType,
                                                                         @ApiParam("托管状态") QuantTrustStatusEnum status,
                                                                         @ApiParam("开始时间") Date beginDate,
                                                                         @ApiParam("结束时间") Date endDate,
                                                                         @RequestParam(defaultValue = "1") @ApiParam("当前页") Integer pageNum,
                                                                         @RequestParam(defaultValue = "10") @ApiParam("每页限制") Integer pageSize) {
        return CommonResult.success(adminQuantOrderService.listQuantTrustSelective(nickname, phone, email, platform, tradeType, status, beginDate, endDate, pageNum, pageSize));
    }

    @ApiOperation("查看某一托管下的订单")
    @GetMapping("/list/under-trust")
    public CommonResult<List<QuantTrustPosition>> listQuantTrustPosition(@RequestParam @ApiParam("托管订单id") Long trustId) {
        return CommonResult.success(quantTrustPositionService.list(trustId));
    }

    @ApiOperation(value = "取消系统托管")
    @PutMapping("cancel/trusteeship/{id}")
    public CommonResult cancelTrusteeship(@PathVariable Long id) {
        quantTrustService.cancel(id);
        return CommonResult.success();
    }

    @ApiOperation("撤销委托 (仅在状态为 NEW/CLOSING,该操作可用) 该接口作废")
    @PutMapping("/cancel/entrust/{id}")
    public CommonResult cancelEntrust(@PathVariable("id") Long id) {
//        QuantOrder quantOrder = quantOrderService.getById(id);
//        quantExchangeService.cancelEntrust(quantOrder, null);
        return CommonResult.success();
    }

    @ApiOperation("强制平仓 (仅在状态为FILLED),该操作可用 该接口作废")
    @PutMapping("/close-force/{id}")
    public CommonResult closeForce(@PathVariable("id") Long id) {
//        QuantOrder quantOrder = quantOrderService.getById(id);
//        return quantExchangeService.closeForce(quantOrder, null);
        return CommonResult.success();
    }

    @ApiOperation("监控平仓")
    @PutMapping("/UpdateMonitorClose/{id}")
    public CommonResult UpdateMonitorClose(@PathVariable("id") Long id){
       // QuantStrategy strategy = quantStrategyService.getById(id);
        quantStrategyService.updateMonitorClose(id, MonitorCloseEnum.MONITOR_CLOSED);
        return CommonResult.success();
    }

    @ApiOperation("重置首单")
    @GetMapping("/reset/{id}")
    public CommonResult reset(@PathVariable("id") Long id) {
        quantStrategyService.reset(id,false);
        return CommonResult.success();
    }

    @ApiOperation("查看资产")
    @GetMapping("/assetDetail/{id}")
    public CommonResult<AccountAssetResponse> assetDetail(@PathVariable("id") Long id) {
        QuantStrategy quantStrategy = quantStrategyService.getById(id);
        if (ObjectUtils.isEmpty(quantStrategy)) {
            return CommonResult.failed("量化策略不存在");
        }
        AccountInfo.Response accountInfoResp = quantStrategyService.getAccountAsset(id);
        if (ObjectUtils.isEmpty(accountInfoResp) || ObjectUtils.isEmpty(accountInfoResp.getAssetMap())) {
            return CommonResult.failed("交易所账户不存在");
        }
        AccountInfo.Response.AssetItem usdt = accountInfoResp.getAssetMap().get(UnitEnum.USDT.getName());

        AccountAssetResponse response = new AccountAssetResponse();
        if (usdt!=null)
        {
            log.info("admin  assetDetail  {}",usdt);
            response.setWalletBalance(String.valueOf(usdt.getWalletBalance().setScale(2, BigDecimal.ROUND_HALF_UP)));
            response.setAvailable(String.valueOf(usdt.getAvailable().setScale(2, BigDecimal.ROUND_HALF_UP)));
            response.setFrozen(String.valueOf(usdt.getFrozen().setScale(2, BigDecimal.ROUND_HALF_UP)));

        }


        List<UnifyPosition> positions = accountInfoResp.getPositions();
        if (CollectionUtils.isEmpty(positions)) {
            return CommonResult.success(response);
        }
        List<UnifyPosition> resultList = positions;
        List<WalletPosition> walletPositionList = new ArrayList<>();
        for (UnifyPosition temp : resultList) {
            WalletPosition walletPosition = new WalletPosition();
            BeanUtils.copyProperties(temp, walletPosition);
            String entryPrice = temp.getEntryPrice();
            if (StringUtils.isNotBlank(entryPrice)) {
                BigDecimal bd = new BigDecimal(entryPrice);
                bd.setScale(8, BigDecimal.ROUND_HALF_UP);
                entryPrice = String.valueOf(bd);
            }
            walletPosition.setEntryPrice(entryPrice);
            walletPosition.setLeverage(temp.getLeverage());
            walletPosition.setPositionSide(temp.getPositionSide());
            walletPosition.setSymbol(walletPosition.getSymbol().replace("/",""));
            if (temp.getInitialMargin() != null) {
                walletPosition.setInitialMargin(temp.getInitialMargin().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            }
            if (temp.getUnrealizedProfit() != null) {
                walletPosition.setUnrealizedProfit(temp.getUnrealizedProfit().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            }
            walletPositionList.add(walletPosition);
        }
        response.setPositions(walletPositionList);
        return CommonResult.success(response);
    }

    @ApiOperation("修改量化策略")
    @PostMapping("/updateQuantStrategy")
    public CommonResult updateQuantStrategy(@Validated @RequestBody UpdateQuantStrategyParam param) {

        // 记录日志
        QuantStrategy strategy = quantStrategyService.getById(param.getId());

        if (strategy.getStatus()==QuantStrategyStatusEnum.SETTLE)
        {
            return CommonResult.failed("正在凍結結算中，請稍後再試!");
        }

        adminQuantOrderService.updateQuantStrategy(param);

        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.UPDATE.getValue(), AdminLogModuleEnum.STRATEGY_UPDATE.getValue(),
                JSON.toJSONString(strategy), JSON.toJSONString(param), "自设指标-修改"));
        return CommonResult.success();
    }

    @ApiOperation("根据传入策略ID反显策略参数")
    @GetMapping("/values")
    public CommonResult<UnifiedDefaultVO> modifyStrategyValues(@RequestParam Long strategyId) {
        QuantStrategy strategy = quantStrategyService.getById(strategyId);
        UnifiedDefaultVO vo = quantStrategyService.modifyStrategyValues(strategy);
        return CommonResult.success(vo);
    }


    @ApiOperation("查看资产对每个币种进行平仓")
    @PostMapping("/closePosition")
    public CommonResult closePosition( @RequestBody ForceCloseParam param){
        QuantStrategy strategy = quantStrategyService.getById(param.getId());
        Assert.notNull(strategy,"该策略不存在");
        QuantApiAccess apiAccess = quantApiAccessService.get(strategy.getApiAccessId());
        quantStrategyService.forceClose(strategy, apiAccess, param.getSymbol(), PositionSideEnum.fromName(param.getSide()));
        return CommonResult.success();
    }

    @ApiOperation("查看资产一键平仓")
    @PostMapping("/oneClickClosePosition")
    public CommonResult oneClickClosePosition(@RequestParam(value = "id")Long id){
        QuantStrategy strategy = quantStrategyService.getById(id);
        Assert.notNull(strategy,"该策略不存在");
        QuantApiAccess apiAccess = quantApiAccessService.get(strategy.getApiAccessId());
        quantStrategyService.forceClose(strategy,apiAccess);
        return CommonResult.success();
    }

    @ApiOperation("后台仓位平仓")
    @PostMapping("/adminClosePosition")
    public CommonResult adminClosePosition(@RequestBody ForceCloseList lists){
        List<AdminForceCloseParam> list = lists.getLists();
        for (AdminForceCloseParam param:list){
            QuantStrategy strategy = quantStrategyService.getById(param.getId());
            Assert.notNull(strategy,"该策略不存在");
            QuantApiAccess apiAccess = quantApiAccessService.get(strategy.getApiAccessId());
            quantStrategyService.forceClose(strategy, apiAccess, param.getSymbol(), param.getPositionSide());
            // 记录日志
            AdminForceCloseParam closeParam = new AdminForceCloseParam();
            BeanUtils.copyProperties(strategy, closeParam);
            adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.UPDATE.getValue(), AdminLogModuleEnum.STRATEGY_CLOSE_POSITION.getValue(),
                    JSON.toJSONString(closeParam), JSON.toJSONString(param), "仓位管理-平仓"));
        }
        return CommonResult.success();
    }


    @ApiOperation("后台仓位一键平仓")
    @PostMapping("/adminOneClickClosePosition")
    public CommonResult adminOneClickClosePosition(@RequestBody List<Long> lists){
        for (Long id:lists){
            QuantStrategy strategy = quantStrategyService.getById(id);
            Assert.notNull(strategy,"该策略不存在");
            QuantApiAccess apiAccess = quantApiAccessService.get(strategy.getApiAccessId());
            quantStrategyService.forceClose(strategy,apiAccess);
            // 记录日志
            adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.UPDATE.getValue(), AdminLogModuleEnum.STRATEGY_ONE_CLICK_CLOSE.getValue(),
                    null, JSON.toJSONString(id), "自设指标一键平仓"));
        }
        return CommonResult.success();
    }

    @ApiOperation("策略参数默认修改列表")
    @GetMapping("/strategyDefaultParametersList")
    public CommonResult<List<DefaultParametersVO>> strategyDefaultParametersList(@RequestParam(value = "type",required = false) StrategyTypeEnum type){
        List<DefaultParametersVO> list = defaultParametersService.list(type);
        return CommonResult.success(list);
    }

    @ApiOperation("策略默认参数添加")
    @PostMapping("/addStrategyDefaultParameters")
    public CommonResult<QuantStrategyDefaultParameters> addStrategyDefaultParameters(@RequestBody DefaultParams defaultParams){
        Assert.isNull(defaultParametersService.getOneByType(defaultParams.getType()),"该指标类型已有默认参数，请勿重复添加");
        QuantStrategyDefaultParameters parameters =new QuantStrategyDefaultParameters();
        parameters.setLeverage(defaultParams.getLeverage());
        parameters.setKlinePeriod(defaultParams.getKlinePeriod());
        parameters.setPosition(defaultParams.getPosition());
        parameters.setType(defaultParams.getType());
        parameters.setPositionSide(defaultParams.getPositionSide());
        if(defaultParams.getType() == StrategyTypeEnum.TREND){
            parameters.setPivotPoints(defaultParams.getTrend().toPivotPoints());
        }
        if (defaultParams.getType() == StrategyTypeEnum.FUTURE){
            parameters.setPivotPoints(defaultParams.getFuture().toPivotPoints());
        }
        defaultParametersService.save(parameters);
        return  CommonResult.success(parameters);
    }

    @ApiOperation("策略默认参数修改")
    @PostMapping("/updateStrategyDefaultParameters")
    public CommonResult updateStrategyDefaultParameters(@RequestBody DefaultParams defaultParams){
        QuantStrategyDefaultParameters parameters = defaultParametersService.getOneByType(defaultParams.getType());
        Assert.notNull(defaultParametersService.getOneByType(defaultParams.getType()),"该指标类型不存在默认参数，无法修改！");
        parameters.setLeverage(defaultParams.getLeverage());
        parameters.setKlinePeriod(defaultParams.getKlinePeriod());
        parameters.setPosition(defaultParams.getPosition());
        parameters.setType(defaultParams.getType());
        parameters.setPositionSide(defaultParams.getPositionSide());
        if(defaultParams.getType() == StrategyTypeEnum.TREND){
            parameters.setPivotPoints(defaultParams.getTrend().toPivotPoints());
        }
        if (defaultParams.getType() == StrategyTypeEnum.FUTURE){
            parameters.setPivotPoints(defaultParams.getFuture().toPivotPoints());
        }
        defaultParametersService.updateById(parameters);
        return  CommonResult.success();
    }

    @ApiOperation("平仓并结算")
    @PostMapping("/closeAndSettlePosition")
    public CommonResult closeAndSettlePosition(@RequestParam(value = "id") Long id){
        QuantStrategy strategy =quantStrategyService.getById(id);
        Assert.notNull(strategy,"该策略不存在");
        if(strategy.getStatus()==QuantStrategyStatusEnum.SETTLE)
        {
            return CommonResult.failed("正在凍結結算中，請稍後再試");
        }
        if(strategy.getStatus()!=QuantStrategyStatusEnum.ACTIVE)
        {
            return CommonResult.failed(" 只有运行中的策略 才能执行 平仓并结算!");
        }
        if(strategy.getSettleType()==SettleTypeEnum.BY_REALTIME)
        {
            return CommonResult.failed(" 只有 冻结结算策略 才能执行 平仓并结算!");
        }
        CommonResult  result=quantStrategyService.cancel(id);

        if (strategy.getOutSignalType()==OutSignalTypeEnum.READ_ONLY)
        {
            //发送只读信号 停止策略
            readOnlyActionService.sendStrategyStop(strategy.getId().intValue());

        }

        return result;
    }

}
