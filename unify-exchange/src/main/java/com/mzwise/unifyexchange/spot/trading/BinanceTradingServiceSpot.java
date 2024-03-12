package com.mzwise.unifyexchange.spot.trading;

import com.binance.client.spot.SpotRequestClient;
import com.binance.client.spot.SpotRequestOptions;
import com.binance.client.spot.exception.BinanceApiException;
import com.binance.client.spot.model.enums.NewOrderRespType;
import com.binance.client.spot.model.enums.OrderSide;
import com.binance.client.spot.model.enums.OrderType;
import com.binance.client.spot.model.market.ExchangeInfoEntry;
import com.binance.client.spot.model.market.ExchangeInformation;
import com.binance.client.spot.model.market.FeeInformation;
import com.binance.client.spot.model.trade.AccountInformation;
import com.binance.client.spot.model.trade.Asset;
import com.binance.client.spot.model.trade.Fill;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.beans.*;
import com.mzwise.unifyexchange.util.CommonUtil;
import com.mzwise.unifyexchange.util.SymbolUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class BinanceTradingServiceSpot implements SpotITradingService {

    @Override
    public Access.Response access(Access access) {
        Access.Response response = new Access.Response();
        try {
            SpotRequestOptions options = new SpotRequestOptions();
            SpotRequestClient spotRequestClient = SpotRequestClient.create(access.getAccessKey(), access.getSecretkey(),
                    options);
            AccountInformation accountInformation = spotRequestClient.getAccountInformation();
            response.setStatus(Constants.RESPONSE_STATUS.OK);
            // 币安没有且不需要accountId
            response.setAccountId(null);
            response.setNormal(accountInformation.getCanTrade());
            return response;
        } catch (BinanceApiException e) {
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
            return response;
        }
    }

    @Override
    public AccountInfo.Response getAccountInfo(AccountInfo accountInfo) {

        try {
            SpotRequestOptions options = new SpotRequestOptions();
            SpotRequestClient spotRequestClient = SpotRequestClient.create(accountInfo.getAccessKey(), accountInfo.getSecretkey(),
                    options);
            AccountInformation baResponse = spotRequestClient.getAccountInformation();
            AccountInfo.Response response = new AccountInfo.Response().builder()
                    .status(Constants.RESPONSE_STATUS.OK)
                    .isNormal(baResponse.getCanTrade())
                    .assetMap(new HashMap<>())
                    .build();
            List<Asset> assets = baResponse.getAssets();
            for (Asset asset : assets) {
                response.addAsset(asset.getAsset().toUpperCase(), asset.getFree(), asset.getLocked());
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
            SpotRequestOptions options = new SpotRequestOptions();
            SpotRequestClient spotRequestClient = SpotRequestClient.create(symbolInfo.getAccessKey(), symbolInfo.getSecretkey(),
                    options);
            ExchangeInformation exchangeInformation = spotRequestClient.getExchangeInformation();
            List<ExchangeInfoEntry> symbols = exchangeInformation.getSymbols();
            Optional<ExchangeInfoEntry> first = symbols.stream().filter(v -> v.getSymbol().equals(SymbolUtil.asBinance(symbolInfo.getSymbol()))).findFirst();
            ExchangeInfoEntry exchangeInfo = first.get();
            List<List<Map<String, String>>> filters = exchangeInfo.getFilters();
            Optional<List<Map<String, String>>> lotSize = filters.stream().filter(
                    f -> f.stream().anyMatch(
                            l -> l.entrySet().stream().anyMatch(
                                    v -> v.getKey().equals("filterType") && v.getValue().equals("LOT_SIZE")
                            )
                    )
            ).findFirst();
            List<Map<String, String>> lotSizeItems = lotSize.get();
            BigDecimal stepSize = new BigDecimal(lotSizeItems.stream().filter(l -> l.containsKey("stepSize")).findFirst().get().get("stepSize"));
            Integer assetScale = CommonUtil.scaleBySize(stepSize);
            Optional<List<Map<String, String>>> priceFilter = filters.stream().filter(
                    f -> f.stream().anyMatch(
                            l -> l.entrySet().stream().anyMatch(
                                    v -> v.getKey().equals("filterType") && v.getValue().equals("PRICE_FILTER")
                            )
                    )
            ).findFirst();
            List<Map<String, String>> priceFilterItems = priceFilter.get();
            BigDecimal tickSize = new BigDecimal(priceFilterItems.stream().filter(l -> l.containsKey("tickSize")).findFirst().get().get("tickSize"));
            Integer quoteScale = CommonUtil.scaleBySize(tickSize);
            Optional<List<Map<String, String>>> minNotionalOp = filters.stream().filter(
                    f -> f.stream().anyMatch(
                            l -> l.entrySet().stream().anyMatch(
                                    v -> v.getKey().equals("filterType") && v.getValue().equals("MIN_NOTIONAL")
                            )
                    )
            ).findFirst();
            List<Map<String, String>> minNotionalItems = minNotionalOp.get();
            BigDecimal minNotional = new BigDecimal(minNotionalItems.stream().filter(l -> l.containsKey("minNotional")).findFirst().get().get("minNotional"));
            response.setStatus(Constants.RESPONSE_STATUS.OK);
            response.setAssetScale(assetScale);
            response.setQuoteScale(quoteScale);
            response.setMinValue(minNotional);
            return response;
        } catch (Exception e) {
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
            return response;
        }
    }

    @Override
    public FeeInfo.Response feeInfo(FeeInfo feeInfo) {
        try {
            SpotRequestOptions options = new SpotRequestOptions();
            SpotRequestClient spotRequestClient = SpotRequestClient.create(feeInfo.getAccessKey(), feeInfo.getSecretkey(),
                    options);
            FeeInformation baResponse = spotRequestClient.getFeeInformation(feeInfo.getSymbol());
            return new FeeInfo.Response().builder()
                    .status(Constants.RESPONSE_STATUS.OK)
                    .makerCommission(baResponse.getMakerCommission())
                    .takerCommission(baResponse.getTakerCommission())
                    .build();
        } catch (Exception e) {
            FeeInfo.Response response = new FeeInfo.Response();
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
            return response;
        }
    }


    @Override
    public Order.Response postOrder(Order order) {
        try {
            SpotRequestOptions options = new SpotRequestOptions();
            SpotRequestClient spotRequestClient = SpotRequestClient.create(order.getAccessKey(), order.getSecretkey(),
                    options);
            com.binance.client.spot.model.trade.Order baResponse = spotRequestClient.postOrder(
                    SymbolUtil.asBinance(order.getSymbol()),
                    OrderSide.valueOf(order.getDirection().toString()),
                    OrderType.valueOf(order.getType().toString()),
                    order.getAmount(),
                    order.getQty(),
                    order.getPrice(),
                    order.getClientId(),
                    NewOrderRespType.FULL);
            Order.Response response = new Order.Response().builder()
                    .newClientOrderId(baResponse.getClientOrderId())
                    .exchangeName(Constants.EXCHANGE_NAME.BINANCE)
                    .status(Constants.RESPONSE_STATUS.OK)
                    .orderStatus(Constants.ORDER_RESPONSE_STATUS.valueOf(baResponse.getStatus()))
                    .direction(Constants.TRADING_DIRECTION.valueOf(baResponse.getSide()))
                    .tid(baResponse.getOrderId().toString())
                    .origQty(baResponse.getOrigQty())
                    .executedQty(baResponse.getExecutedQty())
                    .executedAmount(baResponse.getCummulativeQuoteQty()).build();
            BigDecimal commission = BigDecimal.ZERO;
            for (Fill fill : baResponse.getFills()) {
                commission = commission.add(fill.getCommission());
            }
            // todo 价格存储8位-USDT
            if (baResponse.getCummulativeQuoteQty().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal avgPrice = baResponse.getCummulativeQuoteQty().divide(baResponse.getExecutedQty(), 8, RoundingMode.HALF_UP);
                response.setAvgPrice(avgPrice);
            }
            response.setCommission(commission);
            return response;
        } catch (BinanceApiException e) {
            Order.Response response = new Order.Response();
            response.setExchangeName(Constants.EXCHANGE_NAME.BINANCE);
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
            return response;
        }
    }

    @Override
    public Order.Response detail(Order order) {
        try {
            SpotRequestOptions options = new SpotRequestOptions();
            SpotRequestClient spotRequestClient = SpotRequestClient.create(order.getAccessKey(), order.getSecretkey(),
                    options);
            String symbol = SymbolUtil.asBinance(order.getSymbol());
            com.binance.client.spot.model.trade.Order baResponse = spotRequestClient.getOrder(symbol, null, order.getClientId());
            Order.Response response = new Order.Response().builder()
                    .newClientOrderId(baResponse.getClientOrderId())
                    .exchangeName(Constants.EXCHANGE_NAME.BINANCE)
                    .status(Constants.RESPONSE_STATUS.OK)
                    .orderStatus(Constants.ORDER_RESPONSE_STATUS.valueOf(baResponse.getStatus()))
                    .direction(Constants.TRADING_DIRECTION.valueOf(baResponse.getSide()))
                    .tid(baResponse.getOrderId().toString())
                    .origQty(baResponse.getOrigQty())
                    .executedQty(baResponse.getExecutedQty())
                    .executedAmount(baResponse.getCummulativeQuoteQty()).build();
            BigDecimal commission = BigDecimal.ZERO;
            // todo 倒霉的币安详情不返回手续费
            FeeInfo feeInfo = new FeeInfo();
            feeInfo.setSymbol(symbol);
            feeInfo.setAccessKey(order.getAccessKey());
            feeInfo.setSecretkey(order.getSecretkey());
            FeeInfo.Response feeRep = feeInfo(feeInfo);
            if (feeRep.getStatus().equals(Constants.RESPONSE_STATUS.OK)) {
                commission = feeRep.getMakerCommission().multiply(baResponse.getExecutedQty());
            }
            response.setAvgPrice(baResponse.getPrice());
            response.setCommission(commission);
            return response;
        } catch (Exception e) {
            Order.Response response = new Order.Response();
            response.setExchangeName(Constants.EXCHANGE_NAME.BINANCE);
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
            return response;
        }
    }

    @Override
    public Cancel.Response cancel(Cancel cancel) {
        try {
            SpotRequestOptions options = new SpotRequestOptions();
            SpotRequestClient spotRequestClient = SpotRequestClient.create(cancel.getAccessKey(), cancel.getSecretkey(),
                    options);
            com.binance.client.spot.model.trade.Order baResponse = spotRequestClient.cancelOrder(SymbolUtil.asBinance(cancel.getSymbol()), null, cancel.getClientId());
            Cancel.Response response = new Cancel.Response().builder()
                    .status(Constants.RESPONSE_STATUS.OK)
                    .tid(baResponse.getOrderId().toString()).build();
            return response;
        } catch (BinanceApiException e) {
            Cancel.Response response = new Cancel.Response();
            response.setStatus(Constants.RESPONSE_STATUS.ERROR);
            response.setErrorMsg(e.getMessage());
            return response;
        }
    }
}
