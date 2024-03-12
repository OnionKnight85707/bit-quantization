package com.okex.open.api.websocket.privates;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.okex.open.api.constant.APIConstants;
import com.okex.open.api.constant.ConnectionStateEnum;
import com.okex.open.api.enums.CharsetEnum;
import com.okex.open.api.utils.DateUtils;
import com.okex.open.api.utils.IdGenerator;
import com.okex.open.api.utils.WebSocketWatchDog;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import okhttp3.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Slf4j
public class PrivateWsClient {

    private okhttp3.WebSocket webSocket = null;
    private Boolean flag = true;
    private String sign;
    private PrivateChannelListener messageHandler;
    private ConnectionStateEnum state;
    private long delayInSecond;
    private String accessKey;
    private String secretkey;
    private String passphrase;
    private Long id;
    private long lastReceivedTime;
    List<Map> channelList; // 订阅频道

    /**
     * todo 这里一个用户建一个client 是否还有其他方法
     * @param accessKey
     * @param secretkey
     * @param passphrase
     */
    public PrivateWsClient(String accessKey, String secretkey, String passphrase) {
        this.accessKey = accessKey;
        this.secretkey = secretkey;
        this.passphrase = passphrase;
        this.id = IdGenerator.getNextId();
    }



    private void connectAndSub() {
        if (state == ConnectionStateEnum.CONNECTED) {
            log.error("[Connection][" + this.getConnectionId() + "] 【OKEX】 Already connected");
            return;
        }
        //与服务器建立连接
        connection(APIConstants.WS_PRIVATE_URL);
        //登录账号,用户需提供 api-key，passphrase,secret—key 不要随意透漏 ^_^
        login();
        // 订阅内容
        String s = listToJson(channelList);
        String str = "{\"op\": \"subscribe\", \"args\":" + s + "}";

        if (null != webSocket) {
            sendMessage(str);
        }
    }





    //与服务器建立连接，参数为服务器的URL
    public okhttp3.WebSocket connection(final String url) {
        final PrivateWsClient that = this;
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            ScheduledExecutorService service;

            @Override
            public void onOpen(final okhttp3.WebSocket webSocket, final Response response) {
                lastReceivedTime = System.currentTimeMillis();
                WebSocketWatchDog.onConnectionCreated(that);
                //连接成功后，设置定时器，每隔25s，自动向服务器发送心跳，保持与服务器连接
                state = ConnectionStateEnum.CONNECTED;
                log.info("【Okex】 Connected to the server success! {}", Instant.now().toString());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        // task to run goes here
                        sendMessage("ping");
                    }
                };
                service = Executors.newSingleThreadScheduledExecutor();
                // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
                service.scheduleAtFixedRate(runnable, 25, 25, TimeUnit.SECONDS);
            }

            @Override
            public void onClosing(okhttp3.WebSocket webSocket, int code, String reason) {
                System.out.println("Connection is about to disconnect！");
                webSocket.close(1000, "Okex Long time no message was sent or received！");
                webSocket = null;
            }

            @Override
            public void onClosed(final okhttp3.WebSocket webSocket, final int code, final String reason) {
                System.out.println("Okex Connection dropped！");
                if (state == ConnectionStateEnum.CONNECTED) {
                    state = ConnectionStateEnum.IDLE;
                }
                WebSocketWatchDog.onClosedNormally(that);
            }

            @Override
            public void onFailure(final okhttp3.WebSocket webSocket, final Throwable t, final Response response) {
                t.printStackTrace();
                if (Objects.nonNull(service)) {
                    service.shutdown();
                }
                state = ConnectionStateEnum.CLOSED_ON_ERROR;
                log.error("【OKEX】 [Connection error][" + that.getConnectionId()+ "] Connection is closing due to error");
            }

            @Override
            public void onMessage(final WebSocket webSocket, final String bytes) {
                lastReceivedTime = System.currentTimeMillis();
                //测试服务器返回的字节
                final String byteString=bytes.toString();

                final String s = byteString;

                if (null != s && s.contains("login")) {
                    if (s.endsWith("true}")) {
                        flag = true;
                    }
                }

                if (!s.equals("pong")&&messageHandler!=null) {

                    System.out.println(DateFormatUtils.format(new Date(), DateUtils.TIME_STYLE_S4) + "【OKEX】Receive: " + s);
                    messageHandler.onMessage(JSON.parseObject(s));
                }
            }
        });
        return webSocket;
    }

    public void reConnect(int delayInSecond) {
        log.warn("[Sub]["+id+"]【OKEX】 Reconnecting after "
                + delayInSecond + " seconds later");
        if (webSocket != null) {
            webSocket.cancel();
            webSocket = null;
        }
        this.delayInSecond = delayInSecond;
        state = ConnectionStateEnum.DELAY_CONNECT;
    }

    public void reConnect() {
        if (delayInSecond != 0) {
            delayInSecond--;
        } else {
            connectAndSub();
        }
    }

    private void isLogin(String s) {
        if (null != s && s.contains("login")) {
            if (s.endsWith("true}")) {
                flag = true;
            }
        }
    }

    //获得sign
    private String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(CharsetEnum.UTF_8.charset()), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes(CharsetEnum.UTF_8.charset()));
            hash = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    private String listToJson(List<Map> list) {
        JSONArray jsonArray = new JSONArray();
        for (Map map : list) {
            jsonArray.add(JSONObject.fromObject(map));
        }
        return jsonArray.toJSONString();
    }

    //登录
    public void login() {
        String timestamp = DateTime.now().getMillis() / 1000+ "";
        String message = timestamp + "GET" + "/users/self/verify";
        sign = sha256_HMAC(message, secretkey);
        String str = "{\"op\"" + ":" + "\"login\"" + "," + "\"args\"" + ":" + "[{" + "\"apiKey\"" + ":"+ "\"" + accessKey + "\"" + "," + "\"passphrase\"" + ":" + "\"" + passphrase + "\"" + ","+ "\"timestamp\"" + ":"  + "\"" + timestamp + "\"" + ","+ "\"sign\"" + ":"  + "\"" + sign + "\"" + "}]}";
        sendMessage(str);
    }


    //订阅，参数为频道组成的集合
    public void subscribe(List<Map> list, PrivateChannelListener messageHandler) {
        this.channelList = list;
        this.messageHandler = messageHandler;
        connectAndSub();
    }

    //取消订阅，参数为频道组成的集合
    public void unsubscribe(List<Map> list) {
        String s = listToJson(list);
        String str = "{\"op\": \"unsubscribe\", \"args\":" + s + "}";
        if (null != webSocket) {
            sendMessage(str);
        }
    }

    private void sendMessage(String str) {
        if (null != webSocket) {
            try {
                Thread.sleep(1300);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("【OKEX】Send a message to the server:" + str);
            webSocket.send(str);
        } else {
            System.out.println("Please establish the connection before you operate it！");
        }
    }

    //断开连接
    public void closeConnection() {
        if (null != webSocket) {
            webSocket.close(1000, "User actively closes the connection");
        } else {
            System.out.println("Please establish the connection before you operate it！");
        }
    }

    public boolean getIsLogin() {
        return flag;
    }

    public ConnectionStateEnum getState() {
        return state;
    }

    public Long getConnectionId() {
        return id;
    }

    public long getLastReceivedTime() {
        return this.lastReceivedTime;
    }
}
