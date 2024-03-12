package com.mzwise.unifyexchange.spot.trading;

import com.huobi.client.AccountClient;
import com.huobi.client.GenericClient;
import com.huobi.client.TradeClient;
import com.huobi.client.req.account.AccountBalanceRequest;
import com.huobi.client.req.trade.CreateOrderRequest;
import com.huobi.constant.HuobiOptions;
import com.huobi.model.account.Account;
import com.huobi.model.account.AccountBalance;
import com.huobi.model.account.Balance;
import com.huobi.model.generic.Symbol;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.beans.*;
import com.mzwise.unifyexchange.util.SymbolUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
public final class HuobiTradingServiceSpot implements SpotITradingService {

    @Override
    public Access.Response access(Access access) {
        Access.Response response = new Access.Response();
        try {
            AccountClient accountService = AccountClient.create(HuobiOptions.builder()
                    .apiKey(access.getAccessKey())
                    .secretKey(access.getSecretkey())
                    .build());

            List<Account> accountList = accountService.getAccounts();
            Optional<Account> spot = accountList.stream().filter(v -> v.getType().equals("spot")).findFirst();
            Account account = spot.get();
            response.setStatus(Constants.RESPONSE_STATUS.OK);
            response.setAccountId(account.getId().toString());
            if ("working".equals(account.getState())) {
                response.setNormal(true);
            }
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
            AccountClient accountService = AccountClient.create(HuobiOptions.builder()
                    .apiKey(accountInfo.getAccessKey())
                    .secretKey(accountInfo.getSecretkey())
                    .build());
            AccountBalance accountBalance = accountService.getAccountBalance(new AccountBalanceRequest(Long.valueOf(accountInfo.getAccountId())));
            AccountInfo.Response response = new AccountInfo.Response().builder()
                    .status(Constants.RESPONSE_STATUS.OK)
                    .isNormal("working".equals(accountBalance.getState()))
                    .assetMap(new HashMap<>())
                    .build();
            List<Balance> balances = accountBalance.getList();
            balances.forEach(v->{
                if ("trade".equals(v.getType())) {
                    response.addAvailable(v.getCurrency().toUpperCase(), v.getBalance());
                }
                if ("frozen".equals(v.getType())) {
                    response.addFrozen(v.getCurrency().toUpperCase(), v.getBalance());
                }
            });
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
            GenericClient genericService = GenericClient.create(HuobiOptions.builder().build());
            List<Symbol> symbolList = genericService.getSymbols();
            Symbol symbol = symbolList.stream().filter(v -> v.getSymbol().equals(SymbolUtil.asHuobi(symbolInfo.getSymbol()))).findFirst().get();
            response.setStatus(Constants.RESPONSE_STATUS.OK);
            response.setAssetScale(symbol.getAmountPrecision());
            response.setQuoteScale(symbol.getPricePrecision());
            response.setMinValue(symbol.getMinOrderValue());
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
            TradeClient tradeService = TradeClient.create(HuobiOptions.builder()
                    .apiKey(order.getAccessKey())
                    .secretKey(order.getSecretkey())
                    .build());
            Long spotAccountId = Long.parseLong(order.getAccountId());
            Long resultId;
            String symbol = SymbolUtil.asHuobi(order.getSymbol());
            // 限价交易
            if (order.getType().equals(Constants.ORDER_TYPE.LIMIT)) {
                // 购买
                if (order.getDirection().equals(Constants.TRADING_DIRECTION.BUY)) {
                    CreateOrderRequest buyLimitRequest = CreateOrderRequest.spotBuyLimit(spotAccountId, order.getClientId(), symbol, new BigDecimal(order.getPrice()), new BigDecimal(order.getQty()));
                    resultId = tradeService.createOrder(buyLimitRequest);
                    log.info("create buy-limit order:" + resultId);
                } else { // 出售
                    CreateOrderRequest sellLimitRequest = CreateOrderRequest.spotSellLimit(spotAccountId, order.getClientId(), symbol, new BigDecimal(order.getPrice()), new BigDecimal(order.getQty()));
                    resultId = tradeService.createOrder(sellLimitRequest);
                    log.info("create sell-limit order:" + resultId);
                }
            }
            // 市价交易
            else if (order.getType().equals(Constants.ORDER_TYPE.MARKET)) {
                // 购买
                if (order.getDirection().equals(Constants.TRADING_DIRECTION.BUY)) {
                    CreateOrderRequest buyMarketRequest = CreateOrderRequest.spotBuyMarket(spotAccountId, order.getClientId(), symbol, new BigDecimal(order.getAmount()));
                    resultId = tradeService.createOrder(buyMarketRequest);
                    log.info("create buy-market order:" + resultId);
                } else { // 出售
                    CreateOrderRequest sellMarketRequest = CreateOrderRequest.spotSellMarket(spotAccountId, order.getClientId(), symbol, new BigDecimal(order.getQty()));
                    resultId = tradeService.createOrder(sellMarketRequest);
                    log.info("create sell-market order:" + resultId);
                }
            } else {
                throw new Exception("暂未实现");
            }
            // 获取订单
            return detail(order, 3);
        } catch (Exception e) {
            Order.Response response = new Order.Response();
            response.setExchangeName(Constants.EXCHANGE_NAME.HUOBI);
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
            e.printStackTrace();
            return response;
        }
    }

    @Override
    public Order.Response detail(Order order) {
        return detail(order, 0);
    }

    private Order.Response detail(Order order, int retry) {
        try {
            TradeClient tradeService = TradeClient.create(HuobiOptions.builder()
                    .apiKey(order.getAccessKey())
                    .secretKey(order.getSecretkey())
                    .build());
            // 获取订单
            com.huobi.model.trade.Order hbOrder = tradeService.getOrder(order.getClientId());
            // todo 价格存储8位-USDT
            BigDecimal avgPrice = null;
            if (hbOrder.getFilledAmount().compareTo(BigDecimal.ZERO)>0) {
                avgPrice = hbOrder.getFilledCashAmount().divide(hbOrder.getFilledAmount(), 8, RoundingMode.HALF_UP);
            }
            String[] types = hbOrder.getType().split("-");
            String direction = types[0].toUpperCase();
            Order.Response response = new Order.Response().builder()
                    .newClientOrderId(hbOrder.getClientOrderId())
                    .exchangeName(Constants.EXCHANGE_NAME.HUOBI)
                    .status(Constants.RESPONSE_STATUS.OK)
                    .orderStatus(Constants.ORDER_RESPONSE_STATUS.fromHuobi(hbOrder.getState()))
                    .direction(Constants.TRADING_DIRECTION.valueOf(direction))
                    .tid(hbOrder.getId().toString())
                    .origQty(hbOrder.getAmount())
                    .executedQty(hbOrder.getFilledAmount())
                    .executedAmount(hbOrder.getFilledCashAmount())
                    .commission(hbOrder.getFilledFees())
                    .avgPrice(avgPrice)
                    .build();
            return response;
        } catch (Exception e) {
            // 如果出现错误重新查询
            if (retry>0) {
                log.warn("出现错误开始重试， 剩余次数： {}", retry);
                retry--;
                try {
                    Thread.sleep(300);
                    return detail(order, retry);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            Order.Response response = new Order.Response();
            response.setExchangeName(Constants.EXCHANGE_NAME.HUOBI);
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
            e.printStackTrace();
            return response;
        }
    }

    @Override
    public Cancel.Response cancel(Cancel cancel) {
        try {
            TradeClient tradeService = TradeClient.create(HuobiOptions.builder()
                    .apiKey(cancel.getAccessKey())
                    .secretKey(cancel.getSecretkey())
                    .build());
            Integer integer = tradeService.cancelOrder(cancel.getClientId());
            Cancel.Response response = new Cancel.Response().builder()
                    .status(Constants.RESPONSE_STATUS.OK).build();
            return response;
        } catch (Exception e) {
            Cancel.Response response = new Cancel.Response();
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
            return response;
        }
    }
}
