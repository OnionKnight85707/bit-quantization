package com.mzwise.unifyexchange.future.trading;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.binance.client.future.model.trade.MyTrade;
import com.binance.client.future.model.trade.PositionRisk;
import com.mzwise.unifyexchange.beans.*;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.util.OkexUtil;
import com.mzwise.unifyexchange.util.SymbolUtil;
import com.okex.open.api.bean.account.param.SetLeverage;
import com.okex.open.api.bean.account.param.SetPositionMode;
import com.okex.open.api.bean.trade.param.PlaceOrder;
import com.okex.open.api.config.APIConfiguration;
import com.okex.open.api.service.account.impl.AccountAPIServiceImpl;
import com.okex.open.api.service.trade.impl.TradeAPIServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public final class OkexTradingServiceFuture implements FutureITradingService {

    /**
     * todo： 获取用户资产 和持仓信息 以及添加自测指标时都要用到 。。。
     * @param accountInfo
     * @return
     */
    @Override
    public AccountInfo.Response getAccountInfo(AccountInfo accountInfo) {
        try {
            APIConfiguration config = new APIConfiguration();
            config.setApiKey(accountInfo.getAccessKey());
            config.setSecretKey(accountInfo.getSecretkey());
            config.setPassphrase(accountInfo.getPassphrase());
            AccountAPIServiceImpl accountAPIService = new AccountAPIServiceImpl(config);
            JSONObject baResponse = accountAPIService.getPositions("SWAP",null,null);
            AccountInfo.Response response = new AccountInfo.Response().builder()
                    .status(Constants.RESPONSE_STATUS.OK)
                    .isNormal(true)
                    .assetMap(new HashMap<>())
                    .build();
            JSONArray details = baResponse.getJSONArray("data");
            for (Object detail : details) {
                Map<String,String> p = (Map<String,String>) detail;

                UnifyPosition pos=new UnifyPosition();
                pos.setIsolated(p.get("mgnMode").equals("isolated"));
                pos.setLeverage(Integer.parseInt(p.get("lever")));
                pos.setInitialMargin(new BigDecimal(p.get("imr")));
                pos.setMaintMargin(new BigDecimal(p.get("mmr")));
                pos.setOpenOrderInitialMargin(new BigDecimal(p.get("imr")));
                pos.setPositionInitialMargin(new BigDecimal(p.get("imr")));
                /**
                 *   instId -> DOT-USDT-SWAP    或者 instId -> DOT-USD-SWAP
                 */
                pos.setSymbol(SymbolUtil.toPlatform(p.get("instId")));
                pos.setUnrealizedProfit(new BigDecimal(p.get("upl")));
                pos.setEntryPrice(p.get("avgPx"));
                pos.setMaxNotional("");
                // posSide  long /short
                pos.setPositionSide(p.get("posSide"));

                response.addPosition(pos);
            }

            //get account:
            JSONObject accResponse = accountAPIService.getBalance("USDT");
            JSONObject balances = OkexUtil.getSimpleResponse(accResponse);
            JSONArray accArray = balances.getJSONArray("details");
            for (Object accObj : accArray) {
                Map<String, String> acc = (Map<String, String>) accObj;
                System.out.println(" okex get asset: "+acc.toString());
                String ava=acc.get("availBal");
                if (StringUtils.isEmpty(ava))
                {
                    ava=acc.get("availEq");
                }
                response.addAsset(acc.get("ccy"),new BigDecimal(ava),new BigDecimal(acc.get("frozenBal")),new BigDecimal(acc.get("eqUsd")));
            }

            return response;
        } catch (Exception e) {
            AccountInfo.Response response = new AccountInfo.Response();
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
            return response;
        }
    }

    @Override
    public SymbolInfo.Response symbolInfo(SymbolInfo symbolInfo) {
        return null;
    }

    /**
     * 确保是 全仓 且杠杆倍数是策略表中的杠杆
     * @param riskReq
     * @return
     */
    @Override
    public Risk.Response fixedRisk(Risk riskReq) {
        try {
            /** 确保双向持仓模式 **/
            List<Risk.Response> risks = getAllRiskIncludeNull(riskReq);
            Boolean isChange = false;
            // 修改为双向持仓模式
            Risk.Response risk=null;
            // 单向
            if (risks.size()==0) {
                log.info("修改交易模式为全仓模式");
                Margin margin = (Margin) new Margin(riskReq.getSymbol(), riskReq.getMarginType()).from(riskReq);
                Margin.Response rep = changeMarginType(margin);
                if (!rep.getStatus().equals(Constants.RESPONSE_STATUS.OK)) {
                    throw new Exception(rep.getErrorMsg());
                }
                risks = getAllRiskIncludeNull(riskReq);
                if (risks.size()==0)
                {
                    throw new Exception("修改为双向持仓模式失败 !");
                }


            }
            risk = risks.get(0);

            setLeverage(riskReq);

            return risk;
        } catch (Exception e) {
            e.printStackTrace();
            return new Risk.Response().builder()
                    .status(Constants.RESPONSE_STATUS.ERROR)
                    .errorMsg(e.getMessage())
                    .build();
        }
    }

    private List<String> setLeverage(Risk risk)
    {
        AccountAPIServiceImpl accountAPIService=getAccountApi(risk);
        JSONObject result = accountAPIService.getLeverage(SymbolUtil.asOkexSwap(risk.getSymbol()),risk.getMarginType().name().toLowerCase());

        JSONArray details = result.getJSONArray("data");
        for (Object detail : details) {
            Map<String, String> p = (Map<String, String>) detail;
            String lever=p.get("lever");
            String posSide=p.get("posSide");


            if (StringUtils.isEmpty(lever) || risk.getLeverage().compareTo(new BigDecimal(lever))!=0)
            {
                log.info("okex change lever:  ak= {}  symbol ={}   posSide={}  level={}",risk.getAccessKey(),risk.getSymbol(),posSide,lever);

                Lever lvl=new Lever();
                lvl.setSymbol(risk.getSymbol());
                lvl.setLeverage(risk.getLeverage());
                lvl.from(risk);
                lvl.setPosSide(posSide);

                Lever.Response response=changeLever(lvl);
                if (response.getStatus()!=Constants.RESPONSE_STATUS.OK)
                {
                    throw  new IllegalArgumentException("设置用户杠杆失败 ! symbol="+risk.getSymbol());
                }

            }

        }

        return null;



    }


    /**
     * 获取历史交易订单 信息
     * @param req
     * @return
     */
    @Override
    public List<MyTrade> getHistoryTrade(HistoryTradeReq req) {
        return null;
    }

    @Override
    public Risk.Response getRisk(Risk risk) {
        return null;
    }

    @Override
    public List<PositionRisk> getNotNullRisks(Risk risk) {
        List<Risk.Response> list=getAllRiskIncludeNull(risk);

        list= list.stream().filter(a->!a.getEntryPrice().equals(BigDecimal.ZERO)).collect(Collectors.toList());

        List<PositionRisk> result=new ArrayList<>();

        for (Risk.Response response:list)
        {
            PositionRisk pr=toPositionRisk(response);

            result.add(pr);
        }
        return  result;


    }

    private PositionRisk toPositionRisk(Risk.Response response) {
        PositionRisk  pr=new PositionRisk();
        pr.setEntryPrice(response.getEntryPrice());
        pr.setLeverage(response.getLeverage());
//        pr.setMaxNotionalValue();
//        pr.setLiquidationPrice();
        pr.setMarkPrice(response.getPrice());
        pr.setPositionAmt(response.getPositionAmt());
//        pr.setSymbol();
        pr.setUnrealizedProfit(response.getUnRealizedProfit());
        pr.setIsolatedMargin(response.getIsolatedMargin());
        pr.setPositionSide(response.getPositionSide().name());
        pr.setMarginType(response.getMarginType().name());
        pr.setSymbol(response.getSymbol());

        return pr;

    }

    @Override
    public Lever.Response changeLever(Lever lever) {
        AccountAPIServiceImpl accountAPIService = getAccountApi(lever);
        SetLeverage setLeverage=new SetLeverage();
        setLeverage.setInstId(SymbolUtil.asOkexSwap(lever.getSymbol()));
        setLeverage.setCcy(null);
        setLeverage.setLever(lever.getLeverage().toPlainString());
        setLeverage.setMgnMode("cross");
       // setLeverage.setPosSide(lever.getPosSide());

        JSONObject object=accountAPIService.setLeverage(setLeverage);

        Lever.Response response=new Lever.Response();
        if (object.getString("code").equals("0"))
        {
            response.setStatus(Constants.RESPONSE_STATUS.OK);
        }else
        {
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
        }

        return response;
    }

    private AccountAPIServiceImpl getAccountApi(Common common) {
        APIConfiguration config = new APIConfiguration();
        config.setApiKey(common.getAccessKey());
        config.setSecretKey(common.getSecretkey());
        config.setPassphrase(common.getPassphrase());
        AccountAPIServiceImpl accountAPIService = new AccountAPIServiceImpl(config);
        return accountAPIService;
    }

    @Override
    public Margin.Response changeMarginType(Margin type) {
        AccountAPIServiceImpl accountAPIService = getAccountApi(type);

        SetPositionMode mode=new SetPositionMode();
        if (type.getMarginType()== Constants.MARGIN_TYPE.CROSS)
        {
            mode.setPosMode("long_short_mode");
        }else
        {
            mode.setPosMode("net_mode");
        }
        JSONObject object=accountAPIService.setPositionMode(mode);

        Margin.Response response=new Margin.Response();
        if (object.getString("code").equals("0"))
        {
            response.setStatus(Constants.RESPONSE_STATUS.OK);
        }else
        {
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
        }

        return response;
    }

    @Override
    public Order.Response postUsdtOrder(Order order) {

        try {
            TradeAPIServiceImpl tradeAPIService = getTradeService(order);
            // 开始下单
            PlaceOrder placeOrder = new PlaceOrder();
            String symbol = SymbolUtil.asOkexSwap(order.getSymbol());
            placeOrder.setInstId(symbol);

            //保证金模式：  isolated：逐仓 ；cross：全仓
            placeOrder.setTdMode("cross");
            placeOrder.setClOrdId(order.getClientId());

            //buy：买， sell：卖
            placeOrder.setSide(order.getDirection().getCode().toLowerCase());

            // 持仓方向 posSide  在双向持仓模式下必填，且仅可选择 long 或 short。 仅适用交割、永续
            placeOrder.setPosSide(order.getPositionside().getCode().toLowerCase());

            //委托数量  OKEX 市价购买:  表示张数，必须是整数。。。 不是 提供的金额
            placeOrder.setSz(order.getQty());
            //
//            if (order.getType().equals(Constants.ORDER_TYPE.MARKET) && order.getDirection().equals(Constants.TRADING_DIRECTION.BUY)) {
//                placeOrder.setSz(order.getAmount());
//            }

            // 限价交易
            if (order.getType().equals(Constants.ORDER_TYPE.LIMIT)) {
                placeOrder.setOrdType("limit");
                placeOrder.setPx(order.getPrice());
            }
            // 市价交易
            else if (order.getType().equals(Constants.ORDER_TYPE.MARKET)) {
                placeOrder.setOrdType("market");
            } else {
                throw new Exception("暂未实现");
            }
            JSONObject body = tradeAPIService.placeOrder(placeOrder);
            JSONObject result = OkexUtil.getSimpleResponse(body);
            if (!result.getString("sCode").equals("0")) {
                throw new Exception(result.getString("sMsg"));
            }
//            String ordId = result.getString("ordId");
            // 获取订单
            return detail(order);
        } catch (Exception e) {
            Order.Response response = new Order.Response();
            response.setExchangeName(Constants.EXCHANGE_NAME.OKEX);
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
            e.printStackTrace();
            return response;
        }

    }

    @Override
    public Order.Response detail(Order order) {
        try {
            TradeAPIServiceImpl tradeAPIService = getTradeService(order);
            String symbol = SymbolUtil.asOkexSwap(order.getSymbol());
            JSONObject body = tradeAPIService.getOrderDetails(symbol, null, order.getClientId());
            JSONObject result = OkexUtil.getSimpleResponse(body);
            BigDecimal accFillSz = new BigDecimal(result.getString("accFillSz"));
            BigDecimal avgPx = "".equals(result.getString("avgPx")) ? null : new BigDecimal(result.getString("avgPx"));
            BigDecimal executedQty = BigDecimal.ZERO;
            if (avgPx != null) {
                executedQty = accFillSz.multiply(avgPx);
            }
            Order.Response response = new Order.Response().builder()
                    .newClientOrderId(result.getString("clOrdId"))
                    .exchangeName(Constants.EXCHANGE_NAME.OKEX)
                    .status(Constants.RESPONSE_STATUS.OK)
                    .orderStatus(Constants.ORDER_RESPONSE_STATUS.fromHuobi(result.getString("state")))
                    .direction(Constants.TRADING_DIRECTION.valueOf(result.getString("side").toUpperCase()))
                    .tid(result.getString("ordId"))
                    .origQty(new BigDecimal(result.getString("sz")))
                    .executedQty(accFillSz)
                    .executedAmount(executedQty)
                    //.commission(new BigDecimal(result.getString("fee")).negate())
                    .commission(BigDecimal.ZERO)
                    .avgPrice(avgPx)
                    .symbol(SymbolUtil.toPlatform(result.getString("instId")))
                    .build();
            return response;
        } catch (Exception e) {
            Order.Response response = new Order.Response();
            response.setExchangeName(Constants.EXCHANGE_NAME.OKEX);
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
            e.printStackTrace();
            return response;
        }
    }

    @Override
    public Cancel.Response cancel(Cancel cancel) {
        return null;
    }

    /**
     * 获取所有非空的持仓信息
     * @param risk
     * @return
     */
    @Override
    public List<Risk.Response> getAllRisk(Risk risk) {

        List<Risk.Response> list=getAllRiskIncludeNull(risk);

        return list.stream().filter(a->!a.getEntryPrice().equals(BigDecimal.ZERO)).collect(Collectors.toList());
    }

    private List<Risk.Response> getAllRiskIncludeNull(Risk risk) {

        List<Risk.Response> responses=new ArrayList<>();
        try {
            APIConfiguration config = new APIConfiguration();
            config.setApiKey(risk.getAccessKey());
            config.setSecretKey(risk.getSecretkey());
            config.setPassphrase(risk.getPassphrase());
            AccountAPIServiceImpl accountAPIService = new AccountAPIServiceImpl(config);
            String okexSymbol=null;
            if(risk.getSymbol()!=null)
            {
                okexSymbol=SymbolUtil.asOkexSwap(risk.getSymbol());
            }
            JSONObject baResponse = accountAPIService.getPositions("SWAP",okexSymbol,null);
            JSONArray details = baResponse.getJSONArray("data");
            for (Object detail : details) {
                Map<String,String> p = (Map<String,String>) detail;

                Risk.Response ri=new Risk.Response();
                ri.setStatus(Constants.RESPONSE_STATUS.OK);
                ri.setErrorCode("0");
                ri.setErrorMsg("");
                ri.setMarginType(p.get("mgnMode").equals("isolated")?Constants.MARGIN_TYPE.ISOLATED:Constants.MARGIN_TYPE.CROSS);

                if (!StringUtils.isEmpty(p.get("avgPx")))
                {
                    ri.setEntryPrice(new BigDecimal(p.get("avgPx")));
                }else
                {
                    ri.setEntryPrice(BigDecimal.ZERO);
                }
                ri.setIsolatedMargin("0");
                if (!StringUtils.isEmpty(p.get("lever")))
                {
                    ri.setLeverage(new BigDecimal(p.get("lever")));
                }else
                {
                    ri.setLeverage(BigDecimal.ZERO);
                }

//                ri.setLiquidationPrice(new BigDecimal(p.get("liqPx")));

                if (!StringUtils.isEmpty(p.get("markPx")))
                {
                    ri.setPrice(new BigDecimal(p.get("markPx")));
                }else
                {
                    ri.setPrice(BigDecimal.ZERO);
                }


                ri.setMaxNotionalValue(BigDecimal.ZERO);
                ri.setPositionAmt(new BigDecimal(p.get("pos")));

                if (!StringUtils.isEmpty(p.get("upl")))
                {
                    ri.setUnRealizedProfit(new BigDecimal(p.get("upl")));
                }else
                {
                    ri.setUnRealizedProfit(BigDecimal.ZERO);
                }
                ri.setSymbol(SymbolUtil.toPlatform(p.get("instId")));

                ri.setPositionSide(p.get("posSide").equals("long")?Constants.TRADING_POSITIONSIDE.LONG:Constants.TRADING_POSITIONSIDE.SHORT);

                if (risk.getMarginType()==null
                        || risk.getMarginType()== ri.getMarginType())
                {
                    responses.add(ri);
                }

            }


            return responses;
        } catch (Exception e) {
            e.printStackTrace();
            return responses;
        }
    }

    @Override
    public UnifyOrder getExchangeOrder(HistoryOrderReq req) {
        return null;
    }

    private TradeAPIServiceImpl getTradeService(Common setting) {
        APIConfiguration config = new APIConfiguration();
        config.setApiKey(setting.getAccessKey());
        config.setSecretKey(setting.getSecretkey());
        config.setPassphrase(setting.getPassphrase());
        return new TradeAPIServiceImpl(config);
    }
}
