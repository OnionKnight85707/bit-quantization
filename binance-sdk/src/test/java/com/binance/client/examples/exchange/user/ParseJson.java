package com.binance.client.examples.exchange.user;

import com.binance.client.spot.impl.utils.JsonWrapper;
import com.binance.client.spot.model.user.OrderUpdate;
import com.binance.client.spot.model.user.UserDataUpdateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseJson {
    public static void main(String[] args) {
        String text = "{\"e\":\"executionReport\",\"E\":1622186944591,\"s\":\"SHIBUSDT\",\"c\":\"web_7ea25cb5d2be4f518c89856916e555f3\",\"S\":\"SELL\",\"o\":\"LIMIT\",\"f\":\"GTC\",\"q\":\"2009367.00\",\"p\":\"0.00000817\",\"P\":\"0.00000000\",\"F\":\"0.00\",\"g\":-1,\"C\":\"\",\"x\":\"TRADE\",\"X\":\"FILLED\",\"r\":\"NONE\",\"i\":85836845,\"l\":\"2009367.00\",\"z\":\"2009367.00\",\"L\":\"0.00000817\",\"n\":\"0.01641653\",\"N\":\"USDT\",\"T\":1622186944590,\"t\":33516565,\"I\":205299744,\"w\":false,\"m\":true,\"M\":true,\"O\":1622186922025,\"Z\":\"16.41652839\",\"Y\":\"16.41652839\",\"Q\":\"0.00000000\"}";
        log.info("【Binance】[On Message]:{}", text);
        JsonWrapper jsonWrapper = JsonWrapper.parseFromString(text);


        UserDataUpdateEvent result = new UserDataUpdateEvent();
        result.setEventType(jsonWrapper.getString("e"));
        result.setEventTime(jsonWrapper.getLong("E"));
        result.setTransactionTime(jsonWrapper.getLong("T"));

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
    }
}
