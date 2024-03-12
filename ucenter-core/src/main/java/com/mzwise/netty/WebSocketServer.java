package com.mzwise.netty;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author piao
 * @Date 2021/06/02
 */
@ServerEndpoint(path = "/webSocket/{topic}", port = "7606")
public class WebSocketServer {

    /**
     * 线程安全的hashMap
     */
    public static ConcurrentHashMap<String, List<Session>> channels = new ConcurrentHashMap<>();


    public static void addSession(String topic, Session session) {
        if (channels.containsKey(topic)) {
            List<Session> sessions = channels.get(topic);
            sessions.add(session);
        } else {
            CopyOnWriteArrayList<Session> sessions = new CopyOnWriteArrayList<>();
            sessions.add(session);
            channels.put(topic, sessions);
        }
    }

    public static void removeSession(String topic, Session session) {
        if (channels.containsKey(topic)) {
            List<Session> sessions = channels.get(topic);
            sessions.remove(session);
        }
    }

    public static void sendMessage(String topic, String msg) {
        if (channels.containsKey(topic)) {
            List<Session> sessions = channels.get(topic);
            for (Session session : sessions) {
                session.sendText(msg);
            }
        }
    }

    @BeforeHandshake
    public void handshake(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap, @PathVariable String topic, @PathVariable Map pathMap) {
        session.setSubprotocols("stomp");
        if (!"piaoapiao6606".equals(req)) {
            System.out.println("Authentication failed!");
            session.close();
        }
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap, @PathVariable String topic, @PathVariable Map pathMap) {
        System.out.println("建立连接");
        String[] topics = topic.split(",");
        for (String s : topics) {
            addSession(s, session);
        }
    }

    @OnClose
    public void onClose(Session session, @PathVariable String topic) throws IOException {
        System.out.println(topic);
        removeSession(topic, session);
    }

    @OnError
    public void onError(Session session, @PathVariable String topic, Throwable throwable) {
        System.out.println(topic);
        removeSession(topic, session);
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println(message);
        session.sendText("Hello Netty!");
    }

    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        for (byte b : bytes) {
            System.out.println(b);
        }
        session.sendBinary(bytes);
    }

    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    System.out.println("read idle");
                    break;
                case WRITER_IDLE:
                    System.out.println("write idle");
                    break;
                case ALL_IDLE:
                    System.out.println("all idle");
                    break;
                default:
                    break;
            }
        }
    }
}
