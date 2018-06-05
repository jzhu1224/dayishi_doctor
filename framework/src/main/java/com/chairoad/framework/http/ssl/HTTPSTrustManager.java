package com.chairoad.framework.http.ssl;

import android.util.Log;

import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by yanxin on 17/11/13.
 */

public class HTTPSTrustManager implements X509TrustManager {

    private static TrustManager[] trustManagers;

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    public static SSLSocketFactory allowAllSSL() {
        if (trustManagers == null) {
            trustManagers = new TrustManager[]{new HTTPSTrustManager()};
        }

        try {
            SSLContext sslc = SSLContext.getInstance("TLS");
            sslc.init(null, trustManagers, null);
            return sslc.getSocketFactory();
        } catch (Exception e) {
            Log.e("HTTPS",e.getMessage());
        }

        return null;
    }

}
