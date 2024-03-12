package com.binance.client.spot.impl;

import java.util.LinkedList;
import java.util.List;

import com.binance.client.spot.impl.utils.JsonWrapper;
import com.binance.client.spot.impl.utils.JsonWrapperArray;

import com.binance.client.spot.SubscriptionErrorHandler;
import com.binance.client.spot.SubscriptionListener;
import com.binance.client.spot.impl.utils.Channels;
import com.binance.client.spot.model.enums.CandlestickInterval;
import com.binance.client.spot.model.event.AggregateTradeEvent;
import com.binance.client.spot.model.event.CandlestickEvent;
import com.binance.client.spot.model.event.LiquidationOrderEvent;
import com.binance.client.spot.model.event.MarkPriceEvent;
import com.binance.client.spot.model.event.OrderBookEvent;
import com.binance.client.spot.model.event.SymbolBookTickerEvent;
import com.binance.client.spot.model.event.SymbolMiniTickerEvent;
import com.binance.client.spot.model.event.SymbolTickerEvent;
import com.binance.client.spot.model.market.OrderBookEntry;
import com.binance.client.spot.model.user.OrderUpdate;
import com.binance.client.spot.model.user.UserDataUpdateEvent;

class WebsocketRequestImpl {

    WebsocketRequestImpl() {
    }

    WebsocketRequest<AggregateTradeEvent> subscribeAggregateTradeEvent(String symbol,
            SubscriptionListener<AggregateTradeEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        InputChecker.checker()
                .shouldNotNull(symbol, "symbol")
                .shouldNotNull(subscriptionListener, "listener");
        WebsocketRequest<AggregateTradeEvent> request = new WebsocketRequest<>(subscriptionListener, errorHandler);
        request.name = "***Aggregate Trade for " + symbol + "***"; 
        request.connectionHandler = (connection) -> connection.send(Channels.aggregateTradeChannel(symbol));

        request.jsonParser = (jsonWrapper) -> {
            AggregateTradeEvent result = new AggregateTradeEvent();
            result.setEventType(jsonWrapper.getString("e"));
            result.setEventTime(jsonWrapper.getLong("E"));
            result.setSymbol(jsonWrapper.getString("s"));
            result.setId(jsonWrapper.getLong("a"));
            result.setPrice(jsonWrapper.getBigDecimal("p"));
            result.setQty(jsonWrapper.getBigDecimal("q"));
            result.setFirstId(jsonWrapper.getLong("f"));
            result.setLastId(jsonWrapper.getLong("l"));
            result.setTime(jsonWrapper.getLong("T"));
            result.setIsBuyerMaker(jsonWrapper.getBoolean("m"));
            return result;
        };
        return request;
    }

    WebsocketRequest<MarkPriceEvent> subscribeMarkPriceEvent(String symbol,
            SubscriptionListener<MarkPriceEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        InputChecker.checker()
                .shouldNotNull(symbol, "symbol")
                .shouldNotNull(subscriptionListener, "listener");
        WebsocketRequest<MarkPriceEvent> request = new WebsocketRequest<>(subscriptionListener, errorHandler);
        request.name = "***Mark Price for " + symbol + "***"; 
        request.connectionHandler = (connection) -> connection.send(Channels.markPriceChannel(symbol));

        request.jsonParser = (jsonWrapper) -> {
            MarkPriceEvent result = new MarkPriceEvent();
            result.setEventType(jsonWrapper.getString("e"));
            result.setEventTime(jsonWrapper.getLong("E"));
            result.setSymbol(jsonWrapper.getString("s"));
            result.setMarkPrice(jsonWrapper.getBigDecimal("p"));
            result.setFundingRate(jsonWrapper.getBigDecimal("r"));
            result.setNextFundingTime(jsonWrapper.getLong("T"));
            return result;
        };
        return request;
    }

    WebsocketRequest<CandlestickEvent> subscribeCandlestickEvent(String symbol, CandlestickInterval interval,
            SubscriptionListener<CandlestickEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        InputChecker.checker()
                .shouldNotNull(symbol, "symbol")
                .shouldNotNull(subscriptionListener, "listener");
        WebsocketRequest<CandlestickEvent> request = new WebsocketRequest<>(subscriptionListener, errorHandler);
        request.name = "***Candlestick for " + symbol + "***"; 
        request.connectionHandler = (connection) -> connection.send(Channels.candlestickChannel(symbol, interval));

        request.jsonParser = (jsonWrapper) -> {
            CandlestickEvent result = new CandlestickEvent();
            result.setEventType(jsonWrapper.getString("e"));
            result.setEventTime(jsonWrapper.getLong("E"));
            result.setSymbol(jsonWrapper.getString("s"));
            JsonWrapper jsondata = jsonWrapper.getJsonObject("k");
            result.setStartTime(jsondata.getLong("t"));
            result.setCloseTime(jsondata.getLong("T"));
            result.setSymbol(jsondata.getString("s"));
            result.setInterval(jsondata.getString("i"));
            result.setFirstTradeId(jsondata.getLong("f"));
            result.setLastTradeId(jsondata.getLong("L"));
            result.setOpen(jsondata.getBigDecimal("o"));
            result.setClose(jsondata.getBigDecimal("c"));
            result.setHigh(jsondata.getBigDecimal("h"));
            result.setLow(jsondata.getBigDecimal("l"));
            result.setVolume(jsondata.getBigDecimal("v"));
            result.setNumTrades(jsondata.getLong("n"));
            result.setIsClosed(jsondata.getBoolean("x"));
            result.setQuoteAssetVolume(jsondata.getBigDecimal("q"));
            result.setTakerBuyBaseAssetVolume(jsondata.getBigDecimal("V"));
            result.setTakerBuyQuoteAssetVolume(jsondata.getBigDecimal("Q"));
            result.setIgnore(jsondata.getLong("B"));
            return result;
        };
        return request;
    }

    WebsocketRequest<SymbolMiniTickerEvent> subscribeSymbolMiniTickerEvent(String symbol,
            SubscriptionListener<SymbolMiniTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        InputChecker.checker()
                .shouldNotNull(symbol, "symbol")
                .shouldNotNull(subscriptionListener, "listener");
        WebsocketRequest<SymbolMiniTickerEvent> request = new WebsocketRequest<>(subscriptionListener, errorHandler);
        request.name = "***Individual Symbol Mini Ticker for " + symbol + "***"; 
        request.connectionHandler = (connection) -> connection.send(Channels.miniTickerChannel(symbol));

        request.jsonParser = (jsonWrapper) -> {
            SymbolMiniTickerEvent result = new SymbolMiniTickerEvent();
            result.setEventType(jsonWrapper.getString("e"));
            result.setEventTime(jsonWrapper.getLong("E"));
            result.setSymbol(jsonWrapper.getString("s"));
            result.setOpen(jsonWrapper.getBigDecimal("o"));
            result.setClose(jsonWrapper.getBigDecimal("c"));
            result.setHigh(jsonWrapper.getBigDecimal("h"));
            result.setLow(jsonWrapper.getBigDecimal("l"));
            result.setTotalTradedBaseAssetVolume(jsonWrapper.getBigDecimal("v"));
            result.setTotalTradedQuoteAssetVolume(jsonWrapper.getBigDecimal("q"));
            return result;
        };
        return request;
    }

    WebsocketRequest<List<SymbolMiniTickerEvent>> subscribeAllMiniTickerEvent(
            SubscriptionListener<List<SymbolMiniTickerEvent>> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        InputChecker.checker()
                .shouldNotNull(subscriptionListener, "listener");
        WebsocketRequest<List<SymbolMiniTickerEvent>> request = new WebsocketRequest<>(subscriptionListener, errorHandler);
        request.name = "***All Market Mini Tickers"; 
        request.connectionHandler = (connection) -> connection.send(Channels.miniTickerChannel());

        request.jsonParser = (jsonWrapper) -> {
            List<SymbolMiniTickerEvent> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach(item -> {
                SymbolMiniTickerEvent element = new SymbolMiniTickerEvent();
                element.setEventType(item.getString("e"));
                element.setEventTime(item.getLong("E"));
                element.setSymbol(item.getString("s"));
                element.setOpen(item.getBigDecimal("o"));
                element.setClose(item.getBigDecimal("c"));
                element.setHigh(item.getBigDecimal("h"));
                element.setLow(item.getBigDecimal("l"));
                element.setTotalTradedBaseAssetVolume(item.getBigDecimal("v"));
                element.setTotalTradedQuoteAssetVolume(item.getBigDecimal("q"));
                result.add(element);
            });
            return result;
        };
        return request;
    }

    WebsocketRequest<SymbolTickerEvent> subscribeSymbolTickerEvent(String symbol,
            SubscriptionListener<SymbolTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        InputChecker.checker()
                .shouldNotNull(symbol, "symbol")
                .shouldNotNull(subscriptionListener, "listener");
        WebsocketRequest<SymbolTickerEvent> request = new WebsocketRequest<>(subscriptionListener, errorHandler);
        request.name = "***Individual Symbol Ticker for " + symbol + "***"; 
        request.connectionHandler = (connection) -> connection.send(Channels.tickerChannel(symbol));

        request.jsonParser = (jsonWrapper) -> {
            SymbolTickerEvent result = new SymbolTickerEvent();
            result.setEventType(jsonWrapper.getString("e"));
            result.setEventTime(jsonWrapper.getLong("E"));
            result.setSymbol(jsonWrapper.getString("s"));
            result.setPriceChange(jsonWrapper.getBigDecimal("p"));
            result.setPriceChangePercent(jsonWrapper.getBigDecimal("P"));
            result.setWeightedAvgPrice(jsonWrapper.getBigDecimal("w"));
            result.setLastPrice(jsonWrapper.getBigDecimal("c"));
            result.setLastQty(jsonWrapper.getBigDecimal("Q"));
            result.setOpen(jsonWrapper.getBigDecimal("o"));
            result.setHigh(jsonWrapper.getBigDecimal("h"));
            result.setLow(jsonWrapper.getBigDecimal("l"));
            result.setTotalTradedBaseAssetVolume(jsonWrapper.getBigDecimal("v"));
            result.setTotalTradedQuoteAssetVolume(jsonWrapper.getBigDecimal("q"));
            result.setOpenTime(jsonWrapper.getLong("O"));
            result.setCloseTime(jsonWrapper.getLong("C"));
            result.setFirstId(jsonWrapper.getLong("F"));
            result.setLastId(jsonWrapper.getLong("L"));
            result.setCount(jsonWrapper.getLong("n"));
            return result;
        };
        return request;
    }

    WebsocketRequest<List<SymbolTickerEvent>> subscribeAllTickerEvent(
            SubscriptionListener<List<SymbolTickerEvent>> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        InputChecker.checker()
                .shouldNotNull(subscriptionListener, "listener");
        WebsocketRequest<List<SymbolTickerEvent>> request = new WebsocketRequest<>(subscriptionListener, errorHandler);
        request.name = "***All Market Tickers"; 
        request.connectionHandler = (connection) -> connection.send(Channels.tickerChannel());

        request.jsonParser = (jsonWrapper) -> {
            List<SymbolTickerEvent> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach(item -> {
                SymbolTickerEvent element = new SymbolTickerEvent();
                element.setEventType(item.getString("e"));
                element.setEventTime(item.getLong("E"));
                element.setSymbol(item.getString("s"));
                element.setPriceChange(item.getBigDecimal("p"));
                element.setPriceChangePercent(item.getBigDecimal("P"));
                element.setWeightedAvgPrice(item.getBigDecimal("w"));
                element.setLastPrice(item.getBigDecimal("c"));
                element.setLastQty(item.getBigDecimal("Q"));
                element.setOpen(item.getBigDecimal("o"));
                element.setHigh(item.getBigDecimal("h"));
                element.setLow(item.getBigDecimal("l"));
                element.setTotalTradedBaseAssetVolume(item.getBigDecimal("v"));
                element.setTotalTradedQuoteAssetVolume(item.getBigDecimal("q"));
                element.setOpenTime(item.getLong("O"));
                element.setCloseTime(item.getLong("C"));
                element.setFirstId(item.getLong("F"));
                element.setLastId(item.getLong("L"));
                element.setCount(item.getLong("n"));
                result.add(element);
            });
           
            return result;
        };
        return request;
    }

    WebsocketRequest<SymbolBookTickerEvent> subscribeSymbolBookTickerEvent(String symbol,
            SubscriptionListener<SymbolBookTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        InputChecker.checker()
                .shouldNotNull(symbol, "symbol")
                .shouldNotNull(subscriptionListener, "listener");
        WebsocketRequest<SymbolBookTickerEvent> request = new WebsocketRequest<>(subscriptionListener, errorHandler);
        request.name = "***Individual Symbol Book Ticker for " + symbol + "***"; 
        request.connectionHandler = (connection) -> connection.send(Channels.bookTickerChannel(symbol));

        request.jsonParser = (jsonWrapper) -> {
            SymbolBookTickerEvent result = new SymbolBookTickerEvent();
            result.setOrderBookUpdateId(jsonWrapper.getLong("u"));
            result.setSymbol(jsonWrapper.getString("s"));
            result.setBestBidPrice(jsonWrapper.getBigDecimal("b"));
            result.setBestBidQty(jsonWrapper.getBigDecimal("B"));
            result.setBestAskPrice(jsonWrapper.getBigDecimal("a"));
            result.setBestAskQty(jsonWrapper.getBigDecimal("A"));
            return result;
        };
        return request;
    }

    WebsocketRequest<SymbolBookTickerEvent> subscribeAllBookTickerEvent(
            SubscriptionListener<SymbolBookTickerEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        InputChecker.checker()
                .shouldNotNull(subscriptionListener, "listener");
        WebsocketRequest<SymbolBookTickerEvent> request = new WebsocketRequest<>(subscriptionListener, errorHandler);
        request.name = "***All Market Book Tickers***"; 
        request.connectionHandler = (connection) -> connection.send(Channels.bookTickerChannel());

        request.jsonParser = (jsonWrapper) -> {
            SymbolBookTickerEvent result = new SymbolBookTickerEvent();
            result.setOrderBookUpdateId(jsonWrapper.getLong("u"));
            result.setSymbol(jsonWrapper.getString("s"));
            result.setBestBidPrice(jsonWrapper.getBigDecimal("b"));
            result.setBestBidQty(jsonWrapper.getBigDecimal("B"));
            result.setBestAskPrice(jsonWrapper.getBigDecimal("a"));
            result.setBestAskQty(jsonWrapper.getBigDecimal("A"));
            return result;
        };
        return request;
    }

    WebsocketRequest<LiquidationOrderEvent> subscribeSymbolLiquidationOrderEvent(String symbol,
            SubscriptionListener<LiquidationOrderEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        InputChecker.checker()
                .shouldNotNull(symbol, "symbol")
                .shouldNotNull(subscriptionListener, "listener");
        WebsocketRequest<LiquidationOrderEvent> request = new WebsocketRequest<>(subscriptionListener, errorHandler);
        request.name = "***Individual Symbol Liquidation Order for " + symbol + "***"; 
        request.connectionHandler = (connection) -> connection.send(Channels.liquidationOrderChannel(symbol));

        request.jsonParser = (jsonWrapper) -> {
            LiquidationOrderEvent result = new LiquidationOrderEvent();
            result.setEventType(jsonWrapper.getString("e"));
            result.setEventTime(jsonWrapper.getLong("E"));
            JsonWrapper jsondata = jsonWrapper.getJsonObject("o");
            result.setSymbol(jsondata.getString("s"));
            result.setSide(jsondata.getString("S"));
            result.setType(jsondata.getString("o"));
            result.setTimeInForce(jsondata.getString("f"));
            result.setOrigQty(jsondata.getBigDecimal("q"));
            result.setPrice(jsondata.getBigDecimal("p"));
            result.setAveragePrice(jsondata.getBigDecimal("ap"));
            result.setOrderStatus(jsondata.getString("X"));
            result.setLastFilledQty(jsondata.getBigDecimal("l"));
            result.setLastFilledAccumulatedQty(jsondata.getBigDecimal("z"));
            result.setTime(jsondata.getLong("T"));
            return result;
        };
        return request;
    }

    WebsocketRequest<LiquidationOrderEvent> subscribeAllLiquidationOrderEvent(
            SubscriptionListener<LiquidationOrderEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        InputChecker.checker()
                .shouldNotNull(subscriptionListener, "listener");
        WebsocketRequest<LiquidationOrderEvent> request = new WebsocketRequest<>(subscriptionListener, errorHandler);
        request.name = "***All Liquidation Orders***"; 
        request.connectionHandler = (connection) -> connection.send(Channels.liquidationOrderChannel());

        request.jsonParser = (jsonWrapper) -> {
            LiquidationOrderEvent result = new LiquidationOrderEvent();
            result.setEventType(jsonWrapper.getString("e"));
            result.setEventTime(jsonWrapper.getLong("E"));
            JsonWrapper jsondata = jsonWrapper.getJsonObject("o");
            result.setSymbol(jsondata.getString("s"));
            result.setSide(jsondata.getString("S"));
            result.setType(jsondata.getString("o"));
            result.setTimeInForce(jsondata.getString("f"));
            result.setOrigQty(jsondata.getBigDecimal("q"));
            result.setPrice(jsondata.getBigDecimal("p"));
            result.setAveragePrice(jsondata.getBigDecimal("ap"));
            result.setOrderStatus(jsondata.getString("X"));
            result.setLastFilledQty(jsondata.getBigDecimal("l"));
            result.setLastFilledAccumulatedQty(jsondata.getBigDecimal("z"));
            result.setTime(jsondata.getLong("T"));
            return result;
        };
        return request;
    }

    WebsocketRequest<OrderBookEvent> subscribeBookDepthEvent(String symbol, Integer limit,
            SubscriptionListener<OrderBookEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        InputChecker.checker()
                .shouldNotNull(symbol, "symbol")
                .shouldNotNull(limit, "limit")
                .shouldNotNull(subscriptionListener, "listener");
        WebsocketRequest<OrderBookEvent> request = new WebsocketRequest<>(subscriptionListener, errorHandler);
        request.name = "***Partial Book Depth for " + symbol + "***"; 
        request.connectionHandler = (connection) -> connection.send(Channels.bookDepthChannel(symbol, limit));

        request.jsonParser = (jsonWrapper) -> {
            OrderBookEvent result = new OrderBookEvent();
            result.setEventType(jsonWrapper.getString("e"));
            result.setEventTime(jsonWrapper.getLong("E"));
            result.setTransactionTime(jsonWrapper.getLong("T"));
            result.setSymbol(jsonWrapper.getString("s"));
            result.setFirstUpdateId(jsonWrapper.getLong("U"));
            result.setLastUpdateId(jsonWrapper.getLong("u"));
            result.setLastUpdateIdInlastStream(jsonWrapper.getLong("pu"));

            List<OrderBookEntry> elementList = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("b");
            dataArray.forEachAsArray((item) -> {
                OrderBookEntry element = new OrderBookEntry();
                element.setPrice(item.getBigDecimalAt(0));
                element.setQty(item.getBigDecimalAt(1));
                elementList.add(element);
            });
            result.setBids(elementList);

            List<OrderBookEntry> askList = new LinkedList<>();
            JsonWrapperArray askArray = jsonWrapper.getJsonArray("a");
            askArray.forEachAsArray((item) -> {
                OrderBookEntry element = new OrderBookEntry();
                element.setPrice(item.getBigDecimalAt(0));
                element.setQty(item.getBigDecimalAt(1));
                askList.add(element);
            });
            result.setAsks(askList);
            
            return result;
        };
        return request;
    }

    WebsocketRequest<OrderBookEvent> subscribeDiffDepthEvent(String symbol,
            SubscriptionListener<OrderBookEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        InputChecker.checker()
                .shouldNotNull(symbol, "symbol")
                .shouldNotNull(subscriptionListener, "listener");
        WebsocketRequest<OrderBookEvent> request = new WebsocketRequest<>(subscriptionListener, errorHandler);
        request.name = "***Partial Book Depth for " + symbol + "***"; 
        request.connectionHandler = (connection) -> connection.send(Channels.diffDepthChannel(symbol));

        request.jsonParser = (jsonWrapper) -> {
            OrderBookEvent result = new OrderBookEvent();
            result.setEventType(jsonWrapper.getString("e"));
            result.setEventTime(jsonWrapper.getLong("E"));
            result.setTransactionTime(jsonWrapper.getLong("T"));
            result.setSymbol(jsonWrapper.getString("s"));
            result.setFirstUpdateId(jsonWrapper.getLong("U"));
            result.setLastUpdateId(jsonWrapper.getLong("u"));
            result.setLastUpdateIdInlastStream(jsonWrapper.getLong("pu"));

            List<OrderBookEntry> elementList = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("b");
            dataArray.forEachAsArray((item) -> {
                OrderBookEntry element = new OrderBookEntry();
                element.setPrice(item.getBigDecimalAt(0));
                element.setQty(item.getBigDecimalAt(1));
                elementList.add(element);
            });
            result.setBids(elementList);

            List<OrderBookEntry> askList = new LinkedList<>();
            JsonWrapperArray askArray = jsonWrapper.getJsonArray("a");
            askArray.forEachAsArray((item) -> {
                OrderBookEntry element = new OrderBookEntry();
                element.setPrice(item.getBigDecimalAt(0));
                element.setQty(item.getBigDecimalAt(1));
                askList.add(element);
            });
            result.setAsks(askList);
            
            return result;
        };
        return request;
    }

    WebsocketRequest<UserDataUpdateEvent> subscribeUserDataEvent(String listenKey,
            SubscriptionListener<UserDataUpdateEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler) {
        InputChecker.checker()
                .shouldNotNull(listenKey, "listenKey")
                .shouldNotNull(subscriptionListener, "listener");
        WebsocketRequest<UserDataUpdateEvent> request = new WebsocketRequest<>(subscriptionListener, errorHandler);
        request.name = "***User Data***"; 
        request.connectionHandler = (connection) -> connection.send(Channels.userDataChannel(listenKey));

        request.jsonParser = (jsonWrapper) -> {
            UserDataUpdateEvent result = new UserDataUpdateEvent();
            result.setEventType(jsonWrapper.getString("e"));
            result.setEventTime(jsonWrapper.getLong("E"));
            if (jsonWrapper.containKey("T")) {
                result.setTransactionTime(jsonWrapper.getLong("T"));
            }

            if(jsonWrapper.getString("e").equals("executionReport")) {
                OrderUpdate orderUpdate = new OrderUpdate();
                JsonWrapper jsondata = jsonWrapper;
                orderUpdate.setSymbol(jsondata.getString("s"));
                orderUpdate.setClientOrderId(jsondata.getString("c"));
                orderUpdate.setSide(jsondata.getString("S"));
                orderUpdate.setType(jsondata.getString("o"));
                orderUpdate.setTimeInForce(jsondata.getString("f"));
                orderUpdate.setOrigQty(jsondata.getBigDecimal("q"));
                orderUpdate.setPrice(jsondata.getBigDecimal("p"));
                orderUpdate.setStopPrice(jsondata.getBigDecimal("P"));
                orderUpdate.setExecutionType(jsondata.getString("x"));
                orderUpdate.setOrderStatus(jsondata.getString("X"));
                orderUpdate.setOrderId(jsondata.getLong("i"));
                orderUpdate.setLastFilledQty(jsondata.getBigDecimal("l"));
                orderUpdate.setCumulativeFilledQty(jsondata.getBigDecimal("z"));
                orderUpdate.setLastFilledPrice(jsondata.getBigDecimal("L"));
                orderUpdate.setCommissionAsset(jsondata.getString("N"));
                orderUpdate.setCommissionAmount(jsondata.getBigDecimal("n"));
                orderUpdate.setOrderTradeTime(jsondata.getLong("T"));
                orderUpdate.setTradeID(jsondata.getLong("t"));
                orderUpdate.setIsMarkerSide(jsondata.getBoolean("m"));
                orderUpdate.setCumulativeFilledQuoteQty(jsondata.getBigDecimal("Z"));
                orderUpdate.setLastFilledQuoteQty(jsondata.getBigDecimal("Y"));
                result.setOrderUpdate(orderUpdate); 
            }
            
            return result;
        };
        return request;
    }

}