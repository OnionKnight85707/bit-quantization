package com.mzwise;

import com.alibaba.fastjson.JSONObject;
import com.mzwise.common.util.DateUtil;
import com.mzwise.common.util.HttpClientUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.http.HttpMethod;

import java.math.BigDecimal;
import java.util.*;

public class CommonTests {

    public static void main(String[] args) {
        String username = "nodeWallet";
        String key = "DENVKpIxHqoQn86UbjrXPCTH4pVqpJVB";
        Long timestamp = System.currentTimeMillis();
        JSONObject body = new JSONObject();
        body.put("url", "http://www.xxx.com");
        String parameter = body.toJSONString();
        System.out.println("(parameter + username + key + timestamp):" + parameter + username + key + timestamp);
        String sign = DigestUtils.md5Hex(parameter + username + key + timestamp);
        System.out.println("sign:"+sign);
        JSONObject param = new JSONObject();
        param.put("username", username);
        param.put("timestamp", timestamp.toString());
        param.put("parameter", body);
        param.put("sign", sign);
        String json = param.toJSONString();
        System.out.println(json);
        String s = HttpClientUtil.doPostJson("http://wallet.ig-aus.com/trx/createAccount", json);
        System.out.println(s);
    }

    @Test
    public void testNull() throws InterruptedException {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(DateUtil.getCurrentDate());
            }
        }, 0, 60000);
    }

    class Node {
        public Long userId;
        public BigDecimal rate;
        List<teamShare> share = new ArrayList<>();
        public BigDecimal orderAmount;
        public List<Node> children = new ArrayList<>();

        public Node(Long userId, BigDecimal rate, BigDecimal orderAmount) {
            this.userId = userId;
            this.rate = rate;
            this.orderAmount = orderAmount;
        }

        public void addChild(Node node) {
            children.add(node);
        }
    }

    class teamShare {
        public int teamSize;
        public BigDecimal share;

        public teamShare(int teamSize, BigDecimal share) {
            this.teamSize = teamSize;
            this.share = share;
        }
    }

    class UserProfitBox {
        public BigDecimal rate;
        public BigDecimal profit;

        public UserProfitBox(BigDecimal rate, BigDecimal profit) {
            this.rate = rate;
            this.profit = profit;
        }
    }

    @Test
    public void test() {
    }

    @Test
    public void getProfit() {
        String name = HttpMethod.GET.name();
        String name1 = HttpMethod.POST.name();
        System.out.println(name + name1);
    }

    /**
     * 测试账户
     */
//    @Test
//    public void accountInfo() {
//        ExTradingServiceFactory.TradingServiceSPI spi = ExTradingServiceFactory.getInstance(PlatformToOtherExchangeAdapter.getExchangeName(PlatformEnum.BINANCE));
////        Order order = new Order();
//        String accessKey4 = "";
//        String secretkey4 = "";
////
//        accessKey4 = "XubgEPLGYJSQc6WljPFOE2TDfLizV2A25DiPVhJYrpvN6mSXwRvGKtzCPyjTzQf2";
//        secretkey4 = "yqZHeYU0Ncs2vRzAbcY8OhPyR7JqTvig2FXs6EqZVV8n3XCUYNuolkbzxzR7HcfO";
//        AccountInfo accountInfo = ExTradingServiceFactory.getInstance(Constants.EXCHANGE_NAME.EXCHANGE_BINANCE).accountInfo(accessKey4, secretkey4);
//
//        System.out.println(accountInfo);
//    }

    /**
     * 测试下单
     */
    @Test
    public void order() {
//        ExTradingServiceFactory.TradingServiceSPI spi = ExTradingServiceFactory.getInstance(PlatformToOtherExchangeAdapter.getExchangeName(PlatformEnum.BINANCE));
//        Order order = new Order();
//        order.setAccessKey("XubgEPLGYJSQc6WljPFOE2TDfLizV2A25DiPVhJYrpvN6mSXwRvGKtzCPyjTzQf2");
//        order.setSecretkey("yqZHeYU0Ncs2vRzAbcY8OhPyR7JqTvig2FXs6EqZVV8n3XCUYNuolkbzxzR7HcfO");
//        order.setSymbol("ZILUSDT");
//        order.setVolume("1");
//        order.setDirection(Constants.TRADING_DIRECTION.BUY);
//        order.setType(Constants.ORDER_TYPE.MARKET);
//        order.setRequestId("123");
//        order.setAccountId("1");
//        Order.Response response = spi.postOrder(order);
//        System.out.println(response);
    }

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

//    jwt:
//    tokenHeader: Authorization #JWT存储的请求头
//    secret: mall-admin-secret #JWT加解密使用的密钥
//    expiration: 604800 #JWT的超期限时间(60*60*24*7)
//    tokenHead: 'Bearer '  #JWT负载中拿到开头

    private String secret = "bit-ucenter-secret";

    private Long expiration = 604800L;

    private String tokenHead = "Bearer ";

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    @Test
    public void token() {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, 1l);
        claims.put(CLAIM_KEY_CREATED, new Date());
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        System.out.println(token);
    }

    @Test
    public void getToken() {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOjEsImNyZWF0ZWQiOjE2MjIwMzU1OTUwNDcsImV4cCI6MTYyMjY0MDM5NX0.Pj36qQprc1WAMG4vFi42uydpa4JL8fmdVFMIfBbauSH4EGvQBYXUBXtbER0lfmq74KM1Bb0Itr139_sN8sIT4A")
                .getBody();
        System.out.println(claims.getSubject());
    }
}
