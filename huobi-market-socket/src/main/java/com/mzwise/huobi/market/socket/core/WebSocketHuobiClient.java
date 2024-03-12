package com.mzwise.huobi.market.socket.core;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mzwise.huobi.market.socket.entity.*;
import com.mzwise.huobi.market.socket.util.JSONUtils;
import com.mzwise.huobi.market.socket.util.ZipUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class WebSocketHuobiClient extends WebSocketClient {

    private Logger logger = LoggerFactory.getLogger(WebSocketClient.class);
    private ArrayList<String> subCoinList = new ArrayList<String>();
    private HuobiBaseHandler handler;
    private WebSocketHuobiOptions options;

    public static String DEPTH = "market.%s.depth.step0"; // 深度
    public static String KLINE = "market.%s.kline.%s"; // K线
    public static String DETAIL = "market.%s.detail"; // 市场概要（最新价格、成交量等）
    public static String TRADE = "market.%s.trade.detail"; // 成交明细

    private double VOLUME_PERCENT = 0.13; // 火币成交量的百分比

    private HashMap<String, Long> symbolKlineTime = new HashMap<String, Long>();

    public WebSocketHuobiClient(URI uri, HuobiBaseHandler handler, WebSocketHuobiOptions options) {
        super(uri);
        this.uri = uri;
        this.handler = handler;
        this.options = options;
    }

    @Override
    public void onOpen(ServerHandshake shake) {
        if (null == handler.symbols() || handler.symbols().size() == 0) {
            return;
        }
        logger.info("[WebSocketHuobi] 开启价格 Websocket 监听...");
        for (String symbol : handler.symbols()) {
            if (!subCoinList.contains(symbol)) {
                subCoinList.add(symbol);
            }
            if (options.getTopics().contains(TopicEnum.DEPTH)) {
                // 订阅深度
                String depthTopic = String.format(DEPTH, symbol.replace("/", "").toLowerCase());
                logger.info("[WebSocketHuobi][" + symbol + "] 深度订阅: " + depthTopic);
                sendWsMarket("sub", depthTopic);
            }

            if (options.getTopics().contains(TopicEnum.THUMB)) {
                // 订阅市场概要
                String detailTopic = String.format(DETAIL, symbol.replace("/", "").toLowerCase());
                logger.info("[WebSocketHuobi][" + symbol + "] 概要订阅: " + detailTopic);
                sendWsMarket("sub", detailTopic);
            }

            if (options.getTopics().contains(TopicEnum.TRADE)) {
                // 订阅成交明细
                String tradeTopic = String.format(TRADE, symbol.replace("/", "").toLowerCase());
                logger.info("[WebSocketHuobi][" + symbol + "] 成交明细订阅: " + tradeTopic);
                sendWsMarket("sub", tradeTopic);
            }
            // 订阅实时K线
            if (options.getTopics().contains(TopicEnum.KLINE)) {
                for (PeriodEnum period : PeriodEnum.values()) {
                    String klineTopic = String.format(KLINE, symbol.replace("/", "").toLowerCase(), period.getCode());
                    logger.info("[WebSocketHuobi][" + symbol + "] 实时K线订阅: " + klineTopic);
                    sendWsMarket("sub", klineTopic);
                }
            }
        }
    }

    /**
     * 订阅新币种价格
     *
     * @param symbol
     */
    public void subNewCoinPrice(String symbol) {
        if (!subCoinList.contains(symbol)) {
            subCoinList.add(symbol);
            String detailTopic = String.format(DETAIL, symbol.replace("/", "").toLowerCase());
            logger.info("[WebSocketHuobi][" + symbol + "] 概要订阅: " + detailTopic);
            sendWsMarket("sub", detailTopic);
        }
    }

    /**
     * 订阅新币种k线
     *
     * @param symbol
     */
    public void subNewCoinKline(String symbol) {
        if (!subCoinList.contains(symbol)) {
            subCoinList.add(symbol);
            // 订阅周期1min
            for (PeriodEnum period : PeriodEnum.values()) {
                String topic = String.format(KLINE, symbol.replace("/", "").toLowerCase(), period.getCode());
                logger.info("[WebSocketHuobi][" + symbol + "] K线订阅: " + topic);
                sendWsMarket("sub", topic);
            }
        }
    }

    /**
     * 订阅新币种深度
     *
     * @param symbol
     */
    public void subNewCoinDepth(String symbol) {
        if (!subCoinList.contains(symbol)) {
            subCoinList.add(symbol);
            // 订阅深度
            String topic = String.format(DEPTH, symbol.replace("/", "").toLowerCase());
            logger.info("[WebSocketHuobi][" + symbol + "] 深度订阅: " + topic);
            sendWsMarket("sub", topic);
        }
    }

    // 同步K线
    public void reqKLineList(String symbol, PeriodEnum period, long from, long to) {
        String topic = String.format(KLINE, symbol.replace("/", "").toLowerCase(), period.getCode());

        // Huobi Websocket要求单次请求数据不能超过300条，因此需要在次对请求进行拆分
        // 时间差
        long timeGap = to - from;
        // 1秒 * 300条
        long divideTime = period.gapTime() * 300;

        if (timeGap > divideTime) {
            long times = timeGap % (divideTime) > 0 ? (timeGap / (divideTime) + 1) : timeGap / (divideTime);
            long temTo = from;
            long temFrom = from;
            for (int i = 0; i < times; i++) {
                if (temTo + (divideTime) > to) {
                    temTo = to;
                } else {
                    temTo = temTo + (divideTime);
                }
                sendWsMarket("req", topic, temFrom, temTo);
                temFrom = temFrom + divideTime;
            }
        } else {
            sendWsMarket("req", topic, from, to);
        }
    }

    @Override
    public void onMessage(String arg0) {
        if (arg0 != null) {
            logger.info("[WebSocketHuobi] receive message: {}", arg0);
        }
    }

    @Override
    public void onError(Exception arg0) {
        arg0.printStackTrace();
        String message = "";
        try {
            message = new String(arg0.getMessage().getBytes(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[WebSocketHuobi] has error ,the message is :: {}", message);
        }
    }

    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
        logger.info("[WebSocketHuobi] connection close: {} - {} - {}", arg0, arg1, arg2);
        int tryTimes = 0;
        // 尝试20次
//        logger.info("[WebSocketHuobi] 尝试重新连接，第 " + tryTimes + "次");
        if (this.getReadyState().equals(ReadyState.NOT_YET_CONNECTED) || this.getReadyState().equals(ReadyState.CLOSED) || this.getReadyState().equals(ReadyState.CLOSING)) {

            Runnable sendable = new Runnable() {
                @Override
                public void run() {
                    logger.info("[WebSocketHuobi] 开启重新连接");
                    reconnect();
                }
            };
            new Thread(sendable).start();
        }
    }

    /**
     * 检测是否是新数据
     *
     * @return
     */
    private Boolean checkNewTime(String symbol, Long time) {
        if (!symbolKlineTime.containsKey(symbol)) {
            symbolKlineTime.put(symbol, time);
            return true;
        }
        Long old = symbolKlineTime.get(symbol);
        if (time.intValue() > old.intValue()) {
            symbolKlineTime.put(symbol, time);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        try {
            String message = new String(ZipUtils.decompress(bytes.array()), "UTF-8");

            JSONObject jsonObject = JSONObject.parseObject(message);
            if (!"".equals(message)) {
                if (message.indexOf("ping") > 0) {
                    String pong = jsonObject.toString();
                    send(pong.replace("ping", "pong"));
                } else {

                    String id = "";
                    if (jsonObject.containsKey("ch")) {
                        id = jsonObject.getString("ch");
                        if (id == null || id.split("\\.").length < 3) {
                            return;
                        }
                    }
                    if (jsonObject.containsKey("rep")) {
                        id = jsonObject.getString("rep");
                        if (id == null || id.split("\\.").length < 3) {
                            return;
                        }
                    }
                    if (id.equals("")) {
                        return;
                    }
                    StringBuilder sb = new StringBuilder(id.split("\\.")[1]);
                    String symbol = sb.insert(sb.indexOf("usdt"), "/").toString().toUpperCase();

                    String type = id.split("\\.")[2];
                    if (type.equals("kline")) {

                        String data = jsonObject.getString("data");
                        String period = id.split("\\.")[3];

                        if (null != data && !"".equals(data) && JSONUtils.isJsonArray(data)) {
                            JSONArray klineList = jsonObject.getJSONArray("data");
                            ArrayList<KLine> kLines = new ArrayList<>();
                            for (int i = 0; i < klineList.size(); i++) {
                                JSONObject klineObj = klineList.getJSONObject(i);

                                BigDecimal open = klineObj.getBigDecimal("open");
                                BigDecimal close = klineObj.getBigDecimal("close");
                                BigDecimal high = klineObj.getBigDecimal("high");
                                BigDecimal low = klineObj.getBigDecimal("low");
                                BigDecimal amount = klineObj.getBigDecimal("amount");
                                BigDecimal vol = klineObj.getBigDecimal("vol");
                                int count = klineObj.getIntValue("count");
                                long time = klineObj.getLongValue("id");

                                KLine kline = new KLine(period);
                                kline.setClose(close);
                                kline.setCount(count);
                                kline.setHigh(high);
                                kline.setLow(low);
                                kline.setOpen(open);
                                kline.setTime(time);
                                kline.setTurnover(amount.multiply(BigDecimal.valueOf(VOLUME_PERCENT)));
                                kline.setVolume(vol.multiply(BigDecimal.valueOf(VOLUME_PERCENT)));
                                kLines.add(kline);
                            }
                            if (kLines.size()==1) {
                                handler.refreshKline(symbol, period, kLines.get(0));
                            } else {
                                handler.supplyKline(symbol, period, kLines);
                            }
                        }
                    } else if (type.equals("depth")) {
                        String tick = jsonObject.getString("tick");
                        if (null != tick && !"".equals(tick) && JSONUtils.isJsonObject(tick)) {

                            JSONObject plateObj = JSONObject.parseObject(tick);

                            // 买盘深度
                            JSONArray bids = plateObj.getJSONArray("bids");
                            List<TradePlateItem> buyItems = new ArrayList<>();
                            for (int i = 0; i < bids.size(); i++) {
                                TradePlateItem item = new TradePlateItem();
                                JSONArray itemObj = bids.getJSONArray(i);
                                item.setPrice(itemObj.getBigDecimal(0));
                                item.setAmount(itemObj.getBigDecimal(1));
                                buyItems.add(item);
                            }

                            // 卖盘深度
                            JSONArray asks = plateObj.getJSONArray("asks");
                            List<TradePlateItem> sellItems = new ArrayList<>();
                            for (int i = 0; i < asks.size(); i++) {
                                TradePlateItem item = new TradePlateItem();
                                JSONArray itemObj = asks.getJSONArray(i);
                                item.setPrice(itemObj.getBigDecimal(0));
                                item.setAmount(itemObj.getBigDecimal(1));
                                sellItems.add(item);
                            }
                            // 刷新盘口数据
                            this.handler.refreshPlate(symbol, buyItems, sellItems);
                            //logger.info("[WebSocketHuobi] 盘口更新：bids共 {} 条，asks共 {} 条", bids.size(), asks.size());
                        }
                    } else if (type.equals("detail")) { // 市场行情概要
                        String tick = jsonObject.getString("tick");
                        if (null != tick && !"".equals(tick) && JSONUtils.isJsonObject(tick)) {
                            JSONObject detailObj = JSONObject.parseObject(tick);
                            BigDecimal amount = detailObj.getBigDecimal("amount");
                            BigDecimal open = detailObj.getBigDecimal("open");
                            BigDecimal close = detailObj.getBigDecimal("close");
                            BigDecimal high = detailObj.getBigDecimal("high");
                            BigDecimal count = detailObj.getBigDecimal("count");
                            BigDecimal low = detailObj.getBigDecimal("low");
                            BigDecimal vol = detailObj.getBigDecimal("vol");

                            Thumb thumb = new Thumb();
                            thumb.setSymbol(symbol);
                            thumb.setOpen(open);
                            thumb.setClose(close);
                            thumb.setHigh(high);
                            thumb.setLow(low);
                            thumb.setVolume(amount.multiply(BigDecimal.valueOf(VOLUME_PERCENT))); // 成交量
                            thumb.setTurnover(vol.multiply(BigDecimal.valueOf(VOLUME_PERCENT))); // 成交额
                            handler.refreshThumb(symbol, thumb);

                            // 委托触发 or 爆仓
//                            this.matchFactory.getContractCoinMatch(symbol).refreshPrice(close);
                            //logger.info("[WebSocketHuobi] 价格更新：{}", close);
                        }
                    } else if (type.equals("trade")) { // 成交明细
                        String tick = jsonObject.getString("tick");
                        if (null != tick && !"".equals(tick) && JSONUtils.isJsonObject(tick)) {
                            JSONObject detailObj = JSONObject.parseObject(tick);
                            JSONArray tradeList = detailObj.getJSONArray("data");
                            List<Trade> tradeArrayList = new ArrayList<Trade>();
                            for (int i = 0; i < tradeList.size(); i++) {
                                BigDecimal amount = tradeList.getJSONObject(i).getBigDecimal("amount");
                                BigDecimal price = tradeList.getJSONObject(i).getBigDecimal("price");
                                String direction = tradeList.getJSONObject(i).getString("direction");
                                long time = tradeList.getJSONObject(i).getLongValue("ts");
                                String tradeId = tradeList.getJSONObject(i).getString("tradeId");

                                // 创建交易
                                Trade trade = new Trade();
                                trade.setAmount(amount);
                                trade.setPrice(price);
                                if (direction.equals("buy")) {
                                    trade.setDirection(OrderDirectionEnum.BUY);
                                    trade.setBuyOrderId(tradeId);
                                    trade.setBuyTurnover(amount.multiply(price));
                                } else {
                                    trade.setDirection(OrderDirectionEnum.SELL);
                                    trade.setSellOrderId(tradeId);
                                    trade.setSellTurnover(amount.multiply(price));
                                }
                                trade.setSymbol(symbol);
                                trade.setTime(time);

                                tradeArrayList.add(trade);

                                // 刷新成交记录
                                handler.refreshLastedTrade(symbol, tradeArrayList);
                            }

                            //logger.info("[WebSocketHuobi] 成交明细更新：共 {} 条", tradeArrayList.size());
                        }
                    }
                }
            }
        } catch (CharacterCodingException e) {
            e.printStackTrace();
            logger.error("[WebSocketHuobi] websocket exception: {}", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[WebSocketHuobi] websocket exception: {}", e.getMessage());
        }
    }

    public void sendWsMarket(String op, String topic) {
        JSONObject req = new JSONObject();
        req.put(op, topic);
        send(req.toString());
    }

    public void sendWsMarket(String op, String topic, long from, long to) {
        JSONObject req = new JSONObject();
        req.put(op, topic);
        req.put("from", from);
        if(to>0) {
            req.put("to", to);
        }
        send(req.toString());
    }
}