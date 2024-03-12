package com.mzwise.common.provider.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 麦讯通http工具
 * @author: David Liang
 * @create: 2022-07-22 23:23
 */
@Slf4j
public class MXTHttpSender {

    private static String[] HexCode = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public MXTHttpSender() {
    }

    public static String send(String uri, String account, String pswd, String mobile, String msg, boolean needstatus, String product, String extno, String resptype, boolean encrypt) throws Exception {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod();

        String var16;
        try {
            method.setURI(new URI(uri, false));
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//            String ts = df.format(new Date(System.currentTimeMillis()));
            String ts = df.format(getGMT8Time());
            log.info("麦讯通ts={}", ts);
            method.getParams().setParameter("http.method.retry-handler", new DefaultHttpMethodRetryHandler());
            method.getParams().setParameter("http.protocol.content-charset", "UTF-8");
            method.setRequestBody(new NameValuePair[]{new NameValuePair("account", account), new NameValuePair("mobile", mobile), new NameValuePair("msg", msg), new NameValuePair("needstatus", String.valueOf(needstatus)), new NameValuePair("product", product), new NameValuePair("extno", extno), new NameValuePair("resptype", resptype)});
            if (encrypt) {
                method.addParameter(new NameValuePair("ts", ts));
                method.addParameter(new NameValuePair("pswd", getMd5Str(account + pswd + ts)));
            } else {
                method.addParameter(new NameValuePair("pswd", pswd));
            }

            int result = client.executeMethod(method);
            if (result != 200) {
                throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
            }

            var16 = new String(method.getResponseBody(), "UTF-8");
        } finally {
            method.releaseConnection();
        }

        return var16;
    }

    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date gmt8Time = getGMT8Time();
        System.out.println(gmt8Time);
        String ts = df.format(gmt8Time);
        System.out.println(ts);
    }

    // 获取东八区时间
    public static Date getGMT8Time(){
        Date gmt8 = null;
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"), Locale.CHINESE);
            Calendar day = Calendar.getInstance();
            day.set(Calendar.YEAR, cal.get(Calendar.YEAR));
            day.set(Calendar.MONTH, cal.get(Calendar.MONTH));
            day.set(Calendar.DATE, cal.get(Calendar.DATE));
            day.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
            day.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
            day.set(Calendar.SECOND, cal.get(Calendar.SECOND));
            gmt8 = day.getTime();
        } catch (Exception e) {
            log.error("获取GMT8时间 getGMT8Time() error !");
            e.printStackTrace();
        }
        return  gmt8;
    }

    private static String getMd5Str(String password) {
        try {
            return getMd5Str(password.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException var2) {
            return getMd5Str(password.getBytes());
        }
    }

    private static String getMd5Str(byte[] data) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var3) {
            throw new RuntimeException(var3.getMessage());
        }

        md.update(data);
        return byteArrayToHexString(md.digest());
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (b < 0) {
            n = b + 256;
        }

        int d1 = n / 16;
        int d2 = n % 16;
        return HexCode[d1] + HexCode[d2];
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer result = new StringBuffer();

        for(int i = 0; i < b.length; ++i) {
            result = result.append(byteToHexString(b[i]));
        }

        return result.toString();
    }

}
