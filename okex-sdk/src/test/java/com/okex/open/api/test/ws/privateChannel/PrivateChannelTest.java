package com.okex.open.api.test.ws.privateChannel;


import com.okex.open.api.constant.APIConstants;
import com.okex.open.api.test.ws.privateChannel.config.WebSocketClient;
import com.okex.open.api.test.ws.privateChannel.config.WebSocketConfig;
import com.okex.open.api.websocket.WebSocket;
import com.okex.open.api.websocket.WebSocketListener;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrivateChannelTest {
    private static final WebSocketClient webSocketClient = new WebSocketClient();

    @Before
    public void connect() {
        WebSocketConfig.loginConnect(webSocketClient);

    }


    @After
    public void close() {
        System.out.println(Instant.now().toString() + "Private channels close connect!");
        WebSocketClient.closeConnection();
    }

    /**
     * 账户频道
     * Account Channel
     */
    @Test
    public void privateAccountChannel() {
        //添加订阅频道
        ArrayList<Map> channelList= new ArrayList<>();
        Map accountMap =new HashMap();

           accountMap.put("channel","account");
           accountMap.put("ccy","USDT");

        channelList.add(accountMap);

        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 持仓频道
     * Positions Channel
     */
    @Test
    public void privatePositionsChannel() {
        //添加订阅频道
        ArrayList<Map> channelList= new ArrayList<>();
        Map positionsMap =new HashMap();

        positionsMap.put("channel","positions");
        positionsMap.put("instType","SPOT");
//        positionsMap.put("uly","XRP-USDT");
//        positionsMap.put("instId","XRP-USDT-SWAP");

        channelList.add(positionsMap);

        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 账户余额和持仓频道
     * Balance and Position Channel
     */
    @Test
    public void privateBalanceAndPositionChannel() {
        //添加订阅频道
        ArrayList<Map> channelList= new ArrayList<>();
        Map orderMap =new HashMap();

        orderMap.put("channel","balance_and_position");

        channelList.add(orderMap);

        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 订单频道
     * Order Channel
     */
    @Test
    public void privateOrderChannel() {
        //添加订阅频道
        ArrayList<Map> channelList= new ArrayList<>();
        Map orderMap =new HashMap();

        orderMap.put("channel","orders");
        orderMap.put("instType","SPOT"); //"ANY"
//        orderMap.put("uly","SHIB-USDT");
//        orderMap.put("instId","SHIB-USDT");

        channelList.add(orderMap);

        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 订单频道
     * Order Channel
     */
    @Test
    public void privateOrderChannel2() {
//        new com.okex.open.api.websocket.WebSocketClient(APIConstants.WS_PRIVATE_URL, new WebSocketListener() {
//            @Override
//            public void onTextMessage(WebSocket ws, String text) throws Exception {
//
//            }
//
//            @Override
//            public void onWebsocketOpen(WebSocket ws) {
//
//            }
//
//            @Override
//            public void handleCallbackError(WebSocket websocket, Throwable cause) {
//
//            }
//
//            @Override
//            public void onWebsocketClose(WebSocket ws, int code) {
//
//            }
//
//            @Override
//            public void onWebsocketPong(WebSocket ws) {
//
//            }
//        })
        //添加订阅频道
        ArrayList<Map> channelList= new ArrayList<>();
        Map orderMap =new HashMap();

        orderMap.put("channel","orders");
        orderMap.put("instType","SPOT"); //"ANY"
//        orderMap.put("uly","SHIB-USDT");
//        orderMap.put("instId","SHIB-USDT");

        channelList.add(orderMap);

        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 策略委托订单频道
     * Algo Orders Channel
     */
    @Test
    public void privateAlgoOrdersChannel() {
        //添加订阅频道
        ArrayList<Map> channelList= new ArrayList<>();
        Map algoOrders =new HashMap();

        algoOrders.put("channel","orders-algo");
        algoOrders.put("instType","FUTURES");
        algoOrders.put("uly","BTC-USDT");
        algoOrders.put("instId","BTC-USDT-210625");

        channelList.add(algoOrders);

        //调用订阅方法
        WebSocketClient.subscribe(channelList);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //取消订阅
    @Test
    public void unsubscribeChannel() {
        ArrayList<Map> channelList= new ArrayList<>();

        Map map =new HashMap();
        map.put("channel","balance_and_position");
        map.put("instId","BTC-USD-210924");
        channelList.add(map);
        WebSocketClient.unsubscribe(channelList);
        //为保证收到服务端返回的消息，需要让线程延迟
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
