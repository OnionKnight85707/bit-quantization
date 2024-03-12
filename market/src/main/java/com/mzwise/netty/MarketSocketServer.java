package com.mzwise.netty;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.util.MultiValueMap;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@ServerEndpoint(path = "/market/{symbol}", port = "${netty.server.port}")
public class MarketSocketServer {

    public static ConcurrentHashMap<String, List<Session>> channels = new ConcurrentHashMap<>();

    public static void addSession(String symbol, Session session) {
        if(channels.containsKey(symbol)) {
            List<Session> sessions = channels.get(symbol);
            sessions.add(session);
        } else {
            CopyOnWriteArrayList<Session> sessions = new CopyOnWriteArrayList<>();
            sessions.add(session);
            channels.put(symbol, sessions);
        }
    }

    public static void removeSession(String symbol, Session session) {
        if(channels.containsKey(symbol)) {
            List<Session> sessions = channels.get(symbol);
            sessions.remove(session);
        }
    }

    public static void sendMessage(String symbol, String msg) {
        symbol = symbol.replace("/", "");
        if(channels.containsKey(symbol)) {
            List<Session> sessions = channels.get(symbol);
            for (Session session : sessions) {
                session.sendText(msg);
            }
        }
    }

    @BeforeHandshake
    public void handshake(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap, @PathVariable String symbol, @PathVariable Map pathMap){
        session.setSubprotocols("stomp");
        if (!"fda2131d".equals(req)){
            System.out.println("Authentication failed!");
            session.close();
        }
    }

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap reqMap, @PathVariable String symbol, @PathVariable Map pathMap){
        String[] symbols = symbol.split(",");
        for (String s : symbols) {
            addSession(s, session);
        }
    }

    @OnClose
    public void onClose(Session session, @PathVariable String symbol) throws IOException {
        System.out.println(symbol);
        removeSession(symbol, session);
    }

    @OnError
    public void onError(Session session, @PathVariable String symbol, Throwable throwable) {
        System.out.println(symbol);
        removeSession(symbol, session);
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
