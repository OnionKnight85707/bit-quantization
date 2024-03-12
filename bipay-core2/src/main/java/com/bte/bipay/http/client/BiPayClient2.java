package com.bte.bipay.http.client;

import com.alibaba.fastjson.JSONObject;
import com.bte.bipay.entity.Address;
import com.bte.bipay.entity.SupportCoin;
import com.bte.bipay.entity.Transaction;
import com.bte.bipay.http.ResponseMessage;
import com.bte.bipay.utils.HttpUtil;
import com.uduncloud.sdk.client.UdunClient;
import com.uduncloud.sdk.domain.Coin;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Data
public class BiPayClient2 {
//    private String gateway = "http://wallet.ig-aus.com";
    private static String CONNECTION_EXCEPTION = "connect exception";
    private static String ENCODE_TYPE = "UTF-8";
    private static String CONTENT_TYPE_VALUE = "application/json";
    private static String CONTENT_TYPE_NAME = "Content-Type";
    private int connectTimeout = 1000;
    private int requestTimeout = 1000;
    private Boolean redirectEnabled = true;

    private String username = "nodeWallet";
    private String key = "DENVKpIxHqoQn86UbjrXPCTH4pVqpJVB";

    private String gateway;
    private String merchantId;
    private String merchantKey;
    private String callbackIP;
    private String defaultCallBackUrl = "/master/bipay/notify";

    public static final CookieStore cookieStore = new BasicCookieStore();

    public RequestConfig requestConfig;

    public BiPayClient2(String gateway, String merchantId, String merchantKey, String callbackIP) {
        this.gateway = gateway;
        this.merchantId = merchantId;
        this.merchantKey = merchantKey;
        this.callbackIP = callbackIP;
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(requestTimeout)
                .setRedirectsEnabled(redirectEnabled).build();
    }


    /**
     * 创建地址
     *
     * @param mainCoinType
     * @param callbackUrl
     * @return
     * @throws Exception
     */
    public ResponseMessage<Address> createCoinAddress(String mainCoinType, String callbackUrl, String alias, String walletId) throws Exception {
        JSONObject body = new JSONObject();
        body.put("url", callbackUrl);
        log.info("创建钱包回调地址：{}", callbackUrl);
        String json = HttpUtil.wrapperParams(this.username, this.key, body);
        ResponseMessage<String> response = HttpUtil.doPostJson(this.gateway + "/trx/createAccount", json);
        ResponseMessage<Address> result = new ResponseMessage<>(response.getCode(), response.getMessage());
        if (result.getCode() == ResponseMessage.SUCCESS_CODE) {
            result.setData(Address.parse(response.getData()));
        }
        return result;
    }

    /**
     * 生成地址接口==================================================
     * @param mainCoinType 主币种编号
     * @param alias 地址别名
     * @param walletId 钱包编号
     * @return
     * @throws Exception
     */
    public String createCoinAddress(String mainCoinType, String alias, String walletId) {
        UdunClient udunClient = new UdunClient(gateway, merchantId, merchantKey, callbackIP + defaultCallBackUrl);
        //创建地址
        com.uduncloud.sdk.domain.Address address = udunClient.createAddress(mainCoinType, null, null, callbackIP + defaultCallBackUrl);
        return address.getAddress();

//        JSONObject body = new JSONObject();
//        body.put("merchantId", merchantId);
//        body.put("mainCoinType", mainCoinType);
//        body.put("url", callbackUrl);
//        log.info("创建钱包回调地址：{}", callbackUrl);
//        String json = HttpUtil.wrapperParams(this.username, this.key, body);
//        ResponseMessage<String> response = HttpUtil.doPostJson(this.gateway + "/trx/createAccount", json);
//        ResponseMessage<Address> result = new ResponseMessage<>(response.getCode(), response.getMessage());
//        if (result.getCode() == ResponseMessage.SUCCESS_CODE) {
//            result.setData(Address.parse(response.getData()));
//        }
//        return result;
    }


    /**
     * 转账
     *
     * @param orderId
     * @param amount
     * @param mainCoinType
     * @param subCoinType
     * @param address
     * @param callbackUrl
     * @return
     * @throws Exception
     */
    public ResponseMessage<Transaction> transfer(String orderId, BigDecimal amount, String mainCoinType, String subCoinType, String address, String callbackUrl, String memo) throws Exception {
        JSONObject body = new JSONObject();
        body.put("contract", "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
        body.put("to", address);
        body.put("amount", new BigDecimal(amount.stripTrailingZeros().toPlainString()));
        String json = HttpUtil.wrapperParams(this.username, this.key, body);
        ResponseMessage<String> response = HttpUtil.doPostJson(this.gateway + "/trx/drawToken", json);
        ResponseMessage<Transaction> result = new ResponseMessage<>(response.getCode(), response.getMessage());
        if (result.getCode() == ResponseMessage.SUCCESS_CODE) {
            result.setData(Transaction.parse(response.getData()));
        }
        return result;
    }


    /** ========================
     * 提币
     *
     * @param address      提币地址
     * @param amount       提币数量
     * @param merchantId   商户号
     * @param mainCoinType 主币种编号，使用获取商户币种信息接口
     * @param coinType     子币种编号，使用获取商户币种信息接口
     * @param callUrl      回调地址，通过该callUrl告知您该笔提币交易的状态
     * @param businessId   商务编号，必须保证该字段在系统内唯一，如果重复，则该笔提币钱包将不会接收
     * @param memo         备注，xrp和eos两种币可选填，其他类型币种不填
     * @return
     */
    public ResponseMessage<Transaction> transfer2(String address, BigDecimal amount, String merchantId, String mainCoinType, String coinType, String callUrl, String businessId, String memo) {
        JSONObject body = new JSONObject();
        body.put("address", address);
        body.put("amount", amount);
        body.put("merchantId", merchantId);
        body.put("mainCoinType", mainCoinType);
        body.put("coinType", coinType);
        body.put("callUrl", callUrl);
        body.put("businessId", businessId);
        body.put("memo", memo);

        String json = HttpUtil.wrapperParams(this.username, this.key, body);
        ResponseMessage<String> message = HttpUtil.doPostJson(this.gateway + "/trx/drawToken", json);
        ResponseMessage<Transaction> result = new ResponseMessage<>(message.getCode(), message.getMessage());
        if (result.getCode() == ResponseMessage.SUCCESS_CODE) {
            result.setData(Transaction.parse(message.getData()));
        }
        return result;
    }

    /**
     * 代付
     *
     * @param orderId
     * @param amount
     * @param mainCoinType
     * @param subCoinType
     * @param address
     * @param callbackUrl
     * @return
     * @throws Exception
     */
    public ResponseMessage<String> autoTransfer(String orderId, BigDecimal amount, String mainCoinType, String subCoinType, String address, String callbackUrl, String memo) throws Exception {
//        Transfer transfer = new Transfer();
//        transfer.setAddress(address);
//        transfer.setMainCoinType(mainCoinType);
//        transfer.setCoinType(subCoinType);
//        transfer.setBusinessId(orderId);
//        transfer.setMerchantId(merchantId);
//        transfer.setAmount(amount);
//        transfer.setCallUrl(callbackUrl);
//        transfer.setMemo(memo);
//        JSONArray body = new JSONArray();
//        body.add(transfer);
//        Map<String, String> map = HttpUtil.wrapperParams(this.merchantId, this.merchantKey, body.toJSONString());
//        ResponseMessage<String> response = post(API.AUTO_WITHDRAW, map);
//        return response;
        return null;
    }

    /**
     * 检查是否内部地址
     *
     * @param address
     * @return
     * @throws Exception
     */
    public boolean checkAddress(String mainCoinType, String address) throws Exception {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("merchantId", merchantId);
//        jsonObject.put("mainCoinType", mainCoinType);
//        jsonObject.put("address", address);
//        Map<String, String> map = HttpUtil.wrapperParams(this.merchantId, this.merchantKey, "[" + jsonObject.toJSONString() + "]");
//        ResponseMessage<String> response = post(API.CHECK_ADDRESS, map);
//        return  response.getCode() == 200;
        return false;
    }

    /**
     * 校验地址合法性===================================================
     * @param merchantId 商户号
     * @param mainCoinType 主币种编号
     * @param address 目标地址
     * @return
     * @throws Exception
     */
    public boolean checkAddress2(String merchantId, String mainCoinType, String address) throws Exception {
        JSONObject body = new JSONObject();
        body.put("merchantId", merchantId);
        body.put("mainCoinType", mainCoinType);
        body.put("address", address);
        String json = HttpUtil.wrapperParams(this.username, this.key, body);
        ResponseMessage<String> response = HttpUtil.doPostJson(this.gateway + "/mch/check/address", json);
        return (boolean) JSONObject.parse(response.getData());
    }

    /**
     * 获取支持的币种和对应余额
     *
     * @return
     * @throws Exception
     */
    public List<SupportCoin> getSupportCoin(Boolean showBalance) throws Exception {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("merchantId", merchantId);
//        jsonObject.put("showBalance", showBalance);
//        Map<String, String> map = HttpUtil.wrapperParams(this.merchantId, this.merchantKey, jsonObject.toJSONString());
//        ResponseMessage<String> response = post(API.SUPPORT_COIN, map);
//        List<SupportCoin> supportCoinList = JSONObject.parseArray(response.getData(), SupportCoin.class);
//        return supportCoinList;
        return null;
    }

    /**
     * 获取商户支持币种信息====================================================================
     * @param merchantId 商户号
     * @param showBalance 是否查询余额，false不获取，true获取
     * @return
     * @throws Exception
     */
    public List<SupportCoin> getSupportCoin2(String merchantId, Boolean showBalance) throws Exception {
        JSONObject body = new JSONObject();
        body.put("merchantId", merchantId);
        body.put("showBalance", showBalance);
        String json = HttpUtil.wrapperParams(this.username, this.key, body);
        ResponseMessage<String> res = HttpUtil.doPostJson(this.gateway + "/mch/support-coins", json);
        ResponseMessage<SupportCoin> result = new ResponseMessage<>(res.getCode(), res.getMessage());
        List<SupportCoin> supportCoinList = JSONObject.parseArray(result.getMessage(), SupportCoin.class);
        if (result.getCode() == ResponseMessage.SUCCESS_CODE){
            result.setData(SupportCoin.parse(res.getData()));
        }
        return supportCoinList;
    }

    /**
     * 检查是否支持该币种
     *
     * @param coinName
     * @return
     * @throws Exception
     */
    public boolean checkSupport(Long merchantId, String coinName) throws Exception {
        boolean supported = false;
        List<SupportCoin> supportCoinList = getSupportCoin(false);
        for (SupportCoin supportCoin : supportCoinList) {
            if (supportCoin.getName().equals(coinName)) {
                supported = true;
                break;
            }
        }
        return supported;
    }


    /**========================
     * 获取商户支持的币种，以及余额
     *
     * @param merchantId
     * @param showBalance
     * @return
     */
    public List<SupportCoin> listSupportCoin(String merchantId, boolean showBalance) {
        JSONObject body = new JSONObject();
        body.put("merchantId", merchantId);
        body.put("showBalance", showBalance);
        String json = HttpUtil.wrapperParams(this.username, this.key, body);
        ResponseMessage<String> message = HttpUtil.doPostJson(this.gateway+"/mch/support-coins", json); //
        List<SupportCoin> list = JSONObject.parseArray(message.getData(), SupportCoin.class);
        return list;
    }


    /**
     * 是否支持代付条件
     *
     * @param amount
     * @param mainCoinType
     * @param subCoinType
     * @return
     * @throws Exception
     */
    public boolean checkProxy(BigDecimal amount, String mainCoinType, String subCoinType) throws Exception {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("mainCoinType", mainCoinType);
//        jsonObject.put("subCoinType", subCoinType);
//        jsonObject.put("amount", amount);
//        jsonObject.put("merchantId", merchantId);
//        Map<String, String> map = HttpUtil.wrapperParams(this.merchantId, this.merchantKey, jsonObject.toJSONString());
//        ResponseMessage<String> response = post(API.CHECK_PROXY, map);
//        return (boolean) JSONObject.parse(response.getData());
        return false;
    }

    /**
     * 查询交易记录
     *
     * @param mainCoinType
     * @param coinType
     * @param tradeId
     * @param tradeType
     * @param address
     * @param startTimestamp 时间戳 毫秒
     * @param endTimestamp   时间戳 毫秒
     * @return
     * @throws Exception
     */
    public List<Transaction> queryTransaction(String mainCoinType, String coinType, String tradeId,
                                              Integer tradeType, String address, String startTimestamp, String endTimestamp) throws Exception {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("mainCoinType", mainCoinType);
//        jsonObject.put("coinType", coinType);
//        jsonObject.put("tradeId", tradeId);
//        jsonObject.put("tradeType", tradeType);
//        jsonObject.put("address", address);
//        jsonObject.put("startTimestamp", startTimestamp);
//        jsonObject.put("endTimestamp", endTimestamp);
//        Map<String, String> map = HttpUtil.wrapperParams(this.merchantId, this.merchantKey, jsonObject.toJSONString());
//        ResponseMessage<String> response = post(API.TRANSACTION, map);
//        List<Transaction> trades = JSONObject.parseArray(response.getData(), Transaction.class);
//        return trades;
        return null;
    }

    /** =========================
     * 检查地址是否为该商户生产的地址。
     *
     * @param merchantId   商户号
     * @param mainCoinType 主币种编码
     * @param address      需校验的地址
     * @return
     */
    public boolean checkAddress(String merchantId, String mainCoinType, String address) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("merchantId", merchantId);
        jsonObject.put("mainCoinType", mainCoinType);
        jsonObject.put("address", address);
        String json = HttpUtil.wrapperParams(this.username, this.key, jsonObject);
        ResponseMessage<String> message = HttpUtil.doPostJson(this.gateway+"/mch/check-proxy", json);
        ResponseMessage<Transaction> result = new ResponseMessage<>(message.getCode(), message.getMessage());
        return result.getCode() == 200;
    }


    public static void setCookieStore(List<BasicClientCookie> cookielist) {
        for (BasicClientCookie cookie : cookielist) {
            BiPayClient2.cookieStore.addCookie(cookie);
        }
    }

    public static void createCookie(List<BasicClientCookie> cookielist) {
        for (BasicClientCookie cookie : cookielist) {
            BiPayClient2.cookieStore.addCookie(cookie);
        }
    }

    public static void main(String[] args) {
        //初始化
        UdunClient udunClient = new UdunClient("https://sig10.udun.io",
                "309751",
                "b8253374f33eaac3c043640e4ada2819",
                "http://192.168.2.223:8081/udun/notify");
        //获取商户支持币种
//        List<Coin> coinList = udunClient.listSupportCoin(false);
//        System.out.println(coinList);

        //创建地址
        com.uduncloud.sdk.domain.Address address3 = udunClient.createAddress("195", null, null, "http://demo.com/notify");
        System.out.println(address3);
    }

}
