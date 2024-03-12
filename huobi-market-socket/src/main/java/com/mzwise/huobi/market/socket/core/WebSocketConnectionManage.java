package com.mzwise.huobi.market.socket.core;

import com.mzwise.huobi.market.socket.client.Client;

public class WebSocketConnectionManage {

    private static Client client;
    public static WebSocketHuobiClient ws; // 价格监听websocket

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        WebSocketConnectionManage.client = client;
    }

    public static WebSocketHuobiClient getWebSocket() {
        return ws;
    }

    public static void setWebSocket(WebSocketHuobiClient ws) {
        WebSocketConnectionManage.ws = ws;
    }
}
