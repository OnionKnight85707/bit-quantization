package com.okex.open.api.websocket.privates;

import com.alibaba.fastjson.JSONObject;

public interface PrivateChannelListener {

    void onMessage(JSONObject message);

}
