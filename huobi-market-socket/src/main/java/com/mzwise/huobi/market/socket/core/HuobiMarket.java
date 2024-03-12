package com.mzwise.huobi.market.socket.core;

import com.mzwise.huobi.market.socket.client.Client;
import com.mzwise.huobi.market.socket.core.kline.KlineSyncWorker;
import com.mzwise.huobi.market.socket.entity.TopicEnum;
import org.quartz.SchedulerException;

import java.net.URI;
import java.net.URISyntaxException;

public class HuobiMarket {

    /**
     * 处理者
     */
    private HuobiBaseHandler handler;
    /**
     * 参数配置
     */
    private WebSocketHuobiOptions options;

    public HuobiMarket(HuobiBaseHandler handler, WebSocketHuobiOptions options) {
        this.options = options;
        this.handler = handler;
    }

    public void run() {

        // 设置WebSocket,  之前这里是spring 管理的bean
        WebSocketConnectionManage.setClient(new Client());

        try {
            URI uri = new URI("wss://api.huobi.pro/ws");// 国内不被墙的地址/wss://api.huobi.pro/ws   ws://api.huobi.br.com:443/ws wss://api.huobiasia.vip/ws
            WebSocketHuobiClient ws = new WebSocketHuobiClient(uri, handler, options);
            WebSocketConnectionManage.setWebSocket(ws);
            WebSocketConnectionManage.getClient().connect(ws);
            if (options.getTopics().contains(TopicEnum.KLINE)) {
                try {
                    new KlineSyncWorker(handler).run();
                } catch (SchedulerException e) {
                    System.out.println("huobi 创建k线同步任务出现严重错误");
                    e.printStackTrace();
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
