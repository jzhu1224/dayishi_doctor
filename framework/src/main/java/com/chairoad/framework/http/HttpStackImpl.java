package com.chairoad.framework.http;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HurlStack;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.SSLSocketFactory;

/**
 * Created by yanxin on 17/11/16.
 */

public class HttpStackImpl extends HurlStack {

    private Request request;

    public HttpStackImpl() {
        super(null);
    }

    public HttpStackImpl(UrlRewriter urlRewriter) {
        super(urlRewriter,null);
    }

    public HttpStackImpl(UrlRewriter urlRewriter, SSLSocketFactory sslSocketFactory) {
        super(urlRewriter, sslSocketFactory);
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection connection = super.createConnection(url);
        if(request instanceof CommonObjReq) {
            CommonObjReq comRequest = (CommonObjReq) request;
            if(comRequest.getFollowRedirects() != null) {
                connection.setInstanceFollowRedirects(comRequest.getFollowRedirects());
            }
        }
        this.request = null;
        return connection;
    }

    @Override
    public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders) throws IOException, AuthFailureError {
        this.request = request;
        return super.performRequest(request, additionalHeaders);
    }
}
