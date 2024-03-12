package com.binance.client.future.impl;

import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

public class ProxyUtils {


    public static  X509TrustManager x509TrustManager() {

        return new X509TrustManager() {

            @Override

            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}

            @Override

            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}

            @Override

            public X509Certificate[] getAcceptedIssuers() {

                return new X509Certificate[0];

            }

        };

    }

    public static  SSLSocketFactory sslSocketFactory() {

        try {

            SSLContext sslContext = SSLContext.getInstance("TLS");

            sslContext.init(null, new TrustManager[] {
                    x509TrustManager()}, new SecureRandom());

            return sslContext.getSocketFactory();

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

        } catch (KeyManagementException e) {

            e.printStackTrace();

        }

        return null;

    }

    public static OkHttpClient getClient()
    {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8580));

        Authenticator proxyAuthenticator = new Authenticator() {

            @Override

            public Request authenticate(Route route, Response response) throws IOException {

         //       String credential = Credentials.basic(username, password);

                return response.request().newBuilder()

        //                .header("Proxy-Authorization", credential)

                        .build();

            }

        };

        OkHttpClient  client = new OkHttpClient.Builder()

     //           .addInterceptor(new CommonInterceptor())

                .retryOnConnectionFailure(false)//是否开启缓存

    //            .connectionPool(pool())//连接池

                .connectTimeout(10L, TimeUnit.SECONDS)

                .readTimeout(10L, TimeUnit.SECONDS)

                .sslSocketFactory(sslSocketFactory(), x509TrustManager())
                  .pingInterval(30,TimeUnit.SECONDS)

   //             .proxy(proxy)

  //              .proxyAuthenticator(proxyAuthenticator)

    //            .authenticator(proxyAuthenticator)

                .build();

        return  client;

    }
}
