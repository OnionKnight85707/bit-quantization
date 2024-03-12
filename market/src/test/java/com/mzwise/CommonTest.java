package com.mzwise;

import com.mzwise.huobi.market.socket.core.WebSocketHuobiOptions;
import org.junit.Test;

public class CommonTest {

    @Test
    public void test1() {
        WebSocketHuobiOptions options = new WebSocketHuobiOptions();
        System.out.println(options);
    }
}
