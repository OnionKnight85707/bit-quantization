package com.bte.bipay.utils;

import com.alibaba.fastjson.JSONObject;
import com.bte.bipay.http.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Author piao
 * @Date 2021/03/12
 */
@Slf4j
public class HttpUtil {

    private static String CONNECTION_EXCEPTION = "connect exception";

    /**
     * 带参数的get请求
     *
     * @param url
     * @param param
     * @return String
     */
    public static String doGet(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 不带参数的get请求
     *
     * @param url
     * @return String
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * 带参数的post请求
     *
     * @param url
     * @param param
     * @return String
     */
    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 不带参数的post请求
     *
     * @param url
     * @return String
     */
    public static String doPost(String url) {
        return doPost(url, null);
    }

    /**
     * 传送json类型的post请求
     *
     * @param url
     * @param param
     * @return String
     */
    public static ResponseMessage<String> doPostJson(String url, String param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(param, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            httpResponse = httpClient.execute(httpPost);
            String strResult;
            ResponseMessage<String> response;
            if (httpResponse != null) {
                log.info("httpResponse:{}", httpResponse.getStatusLine().toString());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    log.debug("strResult:,{}", strResult);
                    JSONObject json = JSONObject.parseObject(strResult);
                    response = ResponseMessage.success(json.getInteger("code"), json.getString("msg"));
                    if (json.getString("data") != null) {
                        response.setData(json.getString("data"));
                    }
                } else {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    JSONObject json = JSONObject.parseObject(strResult);
                    response = ResponseMessage.error(json.getInteger("code"), json.getString("msg"));
                }
                log.info("Response:,{}", response);
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("http client close exception:{}", e);
                }
            }
        }
        log.error(CONNECTION_EXCEPTION);
        return ResponseMessage.error(CONNECTION_EXCEPTION);
    }


    public static ResponseMessage<String> doPostText(String url, String param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(param, ContentType.TEXT_PLAIN);
            httpPost.setEntity(entity);
            // 执行http请求
            httpResponse = httpClient.execute(httpPost);
            String strResult;
            ResponseMessage<String> response;
            if (httpResponse != null) {
                log.info("httpResponse:{}", httpResponse.getStatusLine().toString());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    log.debug("strResult:,{}", strResult);
                    JSONObject json = JSONObject.parseObject(strResult);
                    response = ResponseMessage.success(json.getInteger("code"), json.getString("msg"));
                    if (json.getString("data") != null) {
                        response.setData(json.getString("data"));
                    }
                } else {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    JSONObject json = JSONObject.parseObject(strResult);
                    response = ResponseMessage.error(json.getInteger("code"), json.getString("msg"));
                }
                log.info("Response:,{}", response);
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("http client close exception:{}", e);
                }
            }
        }
        log.error(CONNECTION_EXCEPTION);
        return ResponseMessage.error(CONNECTION_EXCEPTION);
    }

    public static String wrapperParams(String username, String key, JSONObject parameter) {
        Long timestamp = System.currentTimeMillis();
        System.out.println("(parameter + username + key + timestamp):" + parameter.toJSONString() + username + key + timestamp);
        String sign = DigestUtils.md5Hex(parameter.toJSONString() + username + key + timestamp);
        System.out.println("sign:"+sign);
        JSONObject param = new JSONObject();
        param.put("username", username);
        param.put("timestamp", timestamp.toString());
        param.put("parameter", parameter);
        param.put("sign", sign);
        String json = param.toJSONString();
        System.out.println("请求参数"+json);
        return json;
    }
}