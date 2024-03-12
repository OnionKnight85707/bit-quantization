package com.mzwise.unifyexchange.spot.trading;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.beans.*;
import com.mzwise.unifyexchange.util.CommonUtil;
import com.mzwise.unifyexchange.util.OkexUtil;
import com.mzwise.unifyexchange.util.SymbolUtil;
import com.okex.open.api.bean.trade.param.CancelOrder;
import com.okex.open.api.bean.trade.param.PlaceOrder;
import com.okex.open.api.config.APIConfiguration;
import com.okex.open.api.service.account.impl.AccountAPIServiceImpl;
import com.okex.open.api.service.publicData.PublicDataAPIService;
import com.okex.open.api.service.publicData.impl.PublicDataAPIServiceImpl;
import com.okex.open.api.service.trade.impl.TradeAPIServiceImpl;

import java.math.BigDecimal;
import java.util.HashMap;

public final class OkexTradingServiceSpot implements SpotITradingService {

    @Override
    public Access.Response access(Access access) {
        Access.Response response = new Access.Response();
        try {
            APIConfiguration config = new APIConfiguration();
            config.setApiKey(access.getAccessKey());
            config.setSecretKey(access.getSecretkey());
            config.setPassphrase(access.getPassphrase());
            AccountAPIServiceImpl accountAPIService = new AccountAPIServiceImpl(config);
            // 查询
            JSONObject result = accountAPIService.getAccountConfiguration();
            response.setStatus(Constants.RESPONSE_STATUS.OK);
            response.setAccountId(result.getString("uid"));
            response.setNormal(true);
            return response;
        } catch (Exception e) {
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
            return response;
        }
    }

    @Override
    public AccountInfo.Response getAccountInfo(AccountInfo accountInfo) {
        try {
            APIConfiguration config = new APIConfiguration();
            config.setApiKey(accountInfo.getAccessKey());
            config.setSecretKey(accountInfo.getSecretkey());
            config.setPassphrase(accountInfo.getPassphrase());
            AccountAPIServiceImpl accountAPIService = new AccountAPIServiceImpl(config);
            JSONObject baResponse = accountAPIService.getBalance("");
            JSONObject balances = OkexUtil.getSimpleResponse(baResponse);
            AccountInfo.Response response = new AccountInfo.Response().builder()
                    .status(Constants.RESPONSE_STATUS.OK)
                    .isNormal(balances.getBigDecimal("totalEq").compareTo(BigDecimal.ZERO)>0)
                    .assetMap(new HashMap<>())
                    .build();
            JSONArray details = balances.getJSONArray("details");
            for (Object detail : details) {
                JSONObject asset = (JSONObject) detail;
                response.addAsset(asset.getString("ccy").toUpperCase(), asset.getBigDecimal("availEq"), asset.getBigDecimal("frozenBal"));
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
        SymbolInfo.Response response = new SymbolInfo.Response();
        try {
            APIConfiguration config = new APIConfiguration();
            config.setApiKey(symbolInfo.getAccessKey());
            config.setSecretKey(symbolInfo.getSecretkey());
            config.setPassphrase(symbolInfo.getPassphrase());
            PublicDataAPIService publicDataAPIService = new PublicDataAPIServiceImpl(config);
            JSONObject result = publicDataAPIService.getInstruments("SPOT", null);
            JSONArray data = result.getJSONArray("data");
            JSONObject symbol = (JSONObject) data.stream().filter(v -> ((JSONObject) v).getString("instId").equals(SymbolUtil.asOkex(symbolInfo.getSymbol()))).findFirst().get();
            response.setStatus(Constants.RESPONSE_STATUS.OK);
            response.setAssetScale(CommonUtil.scaleBySize(symbol.getBigDecimal("lotSz")));
            response.setQuoteScale(CommonUtil.scaleBySize(symbol.getBigDecimal("tickSz")));
            // todo 没有找到最小交易额限制
            response.setMinValue(BigDecimal.valueOf(10));
            return response;
        } catch (Exception e) {
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
            return response;
        }
    }

    @Override
    public FeeInfo.Response feeInfo(FeeInfo feeInfo) {
        return null;
    }

    @Override
    public Order.Response postOrder(Order order) {
        try {
            TradeAPIServiceImpl tradeAPIService = getTradeService(order);
            // 开始下单
            PlaceOrder placeOrder = new PlaceOrder();
            String symbol = SymbolUtil.asOkex(order.getSymbol());
            placeOrder.setInstId(symbol);
            placeOrder.setTdMode("cash");
            placeOrder.setClOrdId(order.getClientId());
            placeOrder.setSide(order.getDirection().getCode().toLowerCase());
            placeOrder.setSz(order.getQty());
            // 市价购买(数量代表金额)
            if (order.getType().equals(Constants.ORDER_TYPE.MARKET) && order.getDirection().equals(Constants.TRADING_DIRECTION.BUY)) {
                placeOrder.setSz(order.getAmount());
            }
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
            String symbol = SymbolUtil.asOkex(order.getSymbol());
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
                    .commission(new BigDecimal(result.getString("fee")).negate())
                    .avgPrice(avgPx)
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
        try {
            TradeAPIServiceImpl tradeAPIService = getTradeService(cancel);
            // 开始撤销
            CancelOrder cancelOrder = new CancelOrder();
            cancelOrder.setInstId(SymbolUtil.asOkex(cancel.getSymbol()));
            cancelOrder.setOrdId(cancel.getTid());
            cancelOrder.setClOrdId(cancel.getClientId());
            JSONObject body = tradeAPIService.cancelOrder(cancelOrder);
            JSONObject result = OkexUtil.getSimpleResponse(body);
            if (result.getInteger("sCode") != 0) {
                throw new Exception(result.getString("sMsg"));
            }
            Cancel.Response response = new Cancel.Response().builder()
                    .status(Constants.RESPONSE_STATUS.OK)
                    .tid(result.getString("ordId")).build();
            return response;
        } catch (Exception e) {
            Cancel.Response response = new Cancel.Response();
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
            return response;
        }
    }

    private TradeAPIServiceImpl getTradeService(Common setting) {
        APIConfiguration config = new APIConfiguration();
        config.setApiKey(setting.getAccessKey());
        config.setSecretKey(setting.getSecretkey());
        config.setPassphrase(setting.getPassphrase());
        return new TradeAPIServiceImpl(config);
    }
}
