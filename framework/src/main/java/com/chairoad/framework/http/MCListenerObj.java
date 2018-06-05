package com.chairoad.framework.http;

import com.alibaba.fastjson.JSONException;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.chairoad.framework.exception.ClientException;

import java.util.Map;

public class MCListenerObj<T> implements Response.ErrorListener, Response.Listener<T> {

    public IObjResListener<T> mIObjResListener;

    public MCListenerObj(IObjResListener<T> mIObjResListener) {
        this.mIObjResListener = mIObjResListener;
    }

    public String urlStr;

    public MCListenerObj(IObjResListener<T> mIObjResListener, String url) {
        this.mIObjResListener = mIObjResListener;
        this.urlStr = url;
    }

    private MCListenerObj() {
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if (mIObjResListener != null)
            mIObjResListener.onFail(convertException(volleyError), urlStr);
    }

    private ClientException convertException(VolleyError volleyError) {
        if (volleyError instanceof com.android.volley.NoConnectionError) {
            return new ClientException("当前网络不可用", ClientException.REQUEST_NETWORK, volleyError);
        } else if (volleyError instanceof com.android.volley.TimeoutError) {
            return new ClientException("请求超时，请稍后重试！", ClientException.REQUEST_TIMEOUT, volleyError);
        } else if (volleyError instanceof com.android.volley.ServerError) {
            if (volleyError.networkResponse != null) {
                if (volleyError.networkResponse.statusCode == 404) {
                    return new ClientException("服务器找不到，请稍后再试！", ClientException.REQUEST_EXCEPTION, volleyError);
                }
            }
            //服务器5xx
            return new ClientException("服务器异常，请稍后再试！", ClientException.REQUEST_EXCEPTION, volleyError);
        } else if (volleyError instanceof com.android.volley.NetworkError) {
            //没有拿到返回的body。有可能是网络不稳定造成的
            return new ClientException("当前网络状态不稳定，请稍后再试！", ClientException.REQUEST_EXCEPTION, volleyError);
        } else if (volleyError instanceof AuthFailureError) {
            return new ClientException("请求认证异常，请稍后再试！", ClientException.REQUEST_EXCEPTION, volleyError);
        } else if (volleyError.getCause() instanceof JSONException) {
            return new ClientException("数据格式错误，请稍后再试！", ClientException.REQUEST_EXCEPTION, volleyError);
        } else {
            return new ClientException("请求异常，请稍后再试！", ClientException.REQUEST_EXCEPTION, volleyError);
        }
    }

    @Override
    public void onResponse(T s) {
        if (mIObjResListener != null) mIObjResListener.onSuccess(s, urlStr);
    }

    public void onResponseHeaders(Map<String, String> headers) {
        if (mIObjResListener != null) {
            if (mIObjResListener instanceof IObjResListenerExt) {
                IObjResListenerExt ext = (IObjResListenerExt) mIObjResListener;
                ext.onResponseHeaders(headers);
            }
        }
    }

    public void onResponseJson(String json) {
        if (mIObjResListener != null) {
            if (mIObjResListener instanceof IObjResListenerExt) {
                IObjResListenerExt ext = (IObjResListenerExt) mIObjResListener;
                ext.onResponseJson(json);
            }
        }
    }

    public static interface IObjResListener<T> {

        void onSuccess(T t, String url);

        void onFail(Exception exception, String url);
    }

    public static interface IObjResListenerExt<T> extends IObjResListener<T> {

        void onResponseHeaders(Map<String, String> headers);

        void onResponseJson(String json);
    }

}
