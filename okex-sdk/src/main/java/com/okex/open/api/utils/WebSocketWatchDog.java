package com.okex.open.api.utils;

import com.okex.open.api.constant.ConnectionStateEnum;
import com.okex.open.api.websocket.privates.PrivateWsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class WebSocketWatchDog {

  public static final long RECEIVE_LIMIT_TS = 120_000;

  public static final int DELAY_ON_FAILURE = 10;

  private static final Map<Long, PrivateWsClient> TIME_HELPER = new ConcurrentHashMap<>();
  private static final Logger log = LoggerFactory.getLogger(WebSocketWatchDog.class);


  static {
    long t = 1_000;
    ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
    exec.scheduleAtFixedRate(() -> {
//      log.info("【Okex watch dog】running");
      TIME_HELPER.entrySet().forEach(entry -> {
        PrivateWsClient connection = entry.getValue();
        if (connection.getState() == ConnectionStateEnum.CONNECTED) {
          // Check response
          long ts = System.currentTimeMillis() - connection.getLastReceivedTime();
          if (ts > RECEIVE_LIMIT_TS) {
            log.warn("[Sub][" + connection.getConnectionId() + "]【Okex】 No response from server");
            connection.reConnect(DELAY_ON_FAILURE);
          }

        } else if (connection.getState() == ConnectionStateEnum.DELAY_CONNECT) {
          connection.reConnect();
        } else if (connection.getState() == ConnectionStateEnum.CLOSED_ON_ERROR) {
          connection.reConnect(DELAY_ON_FAILURE);
        }
      });
    }, t, t, TimeUnit.MILLISECONDS);
    Runtime.getRuntime().addShutdownHook(new Thread(exec::shutdown));
  }

  private WebSocketWatchDog() {}


  public static void onConnectionCreated(PrivateWsClient connection) {
    TIME_HELPER.put(connection.getConnectionId(), connection);
  }

  public static void onClosedNormally(PrivateWsClient connection) {
    TIME_HELPER.remove(connection);
  }
}
