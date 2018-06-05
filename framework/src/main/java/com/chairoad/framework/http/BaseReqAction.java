package com.chairoad.framework.http;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.chairoad.framework.http.ssl.HTTPSTrustManager;
import com.chairoad.framework.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * 发起网络请求的基本类
 * public static Request<?> getAllTab(MCListener.IStrResListener iStrResListener) {
 * HashMap<String,String> paramsMap=new HashMap<>();
 * return createCommonReq(AppConstants.BASE_URL, paramsMap, iStrResListener);
 * }
 * Created by shejian on 2015/4/14.
 */
public class BaseReqAction {

    private static RequestQueue mQueue;

    /**
     * Get获取Str请求函数
     *
     * @param mContext
     * @param url
     * @param paramsMap
     * @param iStrResListener
     * @return
     */
    public static Request<?> createGetReqToQu(Context mContext, String url, HashMap<String, Object> paramsMap, MCListener.IStrResListener iStrResListener, final HashMap<String, String> headerInfo) {
        return createReqToQue(mContext, getCommStrReq(Request.Method.GET, url, paramsMap, iStrResListener, headerInfo));
    }

    /**
     * Post获取Str请求函数
     *
     * @param mContext
     * @param url
     * @param paramsMap
     * @param iStrResListener
     * @return
     */
    public static Request<?> createPostReqToQu(Context mContext, String url, HashMap<String, Object> paramsMap, MCListener.IStrResListener iStrResListener, final HashMap<String, String> headerInfo) {
        return createReqToQue(mContext, getCommStrReq(Request.Method.POST, url, paramsMap, iStrResListener, headerInfo));
    }

    public static CommonReq getCommStrReq(int method, String urlStr, HashMap<String, Object> paramsMap, MCListener.IStrResListener iStrResListener, final HashMap<String, String> headerInfo) {
        return new CommonReq(method, urlStr, paramsMap, new MCListener(iStrResListener, urlStr)) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerInfo;
            }
        };
    }

    /**
     * Get请求获取对象的函数
     *
     * @param mContext
     * @param url
     * @param paramsMap
     * @param iStrResListener
     * @param responseClass
     * @param <T>
     * @return
     */
    public static <T> Request<?> createGetReqToQu(Context mContext, String url, HashMap<String, Object> paramsMap, MCListenerObj.IObjResListener<T> iStrResListener, Class<T> responseClass, final HashMap<String, String> headerInfo) {
        return createReqToQue(mContext, getCommObjReq(Request.Method.GET, url, paramsMap, iStrResListener, responseClass, headerInfo));
    }

    /**
     * Post请求获取对象的接口
     *
     * @param mContext
     * @param url
     * @param paramsMap
     * @param iStrResListener
     * @param responseClass
     * @param <T>
     * @return
     */
    public static <T> Request<?> createPostReqToQu(Context mContext, String url, HashMap<String, Object> paramsMap, MCListenerObj.IObjResListener<T> iStrResListener, Class<T> responseClass, final HashMap<String, String> headerInfo) {
        return createReqToQue(mContext, getCommObjReq(Request.Method.POST, url, paramsMap, iStrResListener, responseClass, headerInfo));
    }

    public static <T> CommonObjReq getCommObjReq(int method, String urlStr, HashMap<String, Object> paramsMap, MCListenerObj.IObjResListener<T> iStrResListener, Class<T> responseClass, final HashMap<String, String> headerInfo) {

        return new CommonObjReq(method, urlStr, paramsMap, new MCListenerObj(iStrResListener, urlStr), responseClass) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerInfo;
            }
        };
    }

    public static <T> ImageObjReq getImageObjReq(int method, String urlStr, HashMap<String, Object> paramsMap, MCListenerObj.IObjResListener<T> iStrResListener, Class<T> responseClass, final HashMap<String, String> headerInfo) {

        return new ImageObjReq(method, urlStr, paramsMap, new MCListenerObj(iStrResListener, urlStr), responseClass) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerInfo;
            }
        };
    }

    public static <T> CommonObjFileReq getFileReq(int method, String urlStr, MCListenerObj.IObjResListener<T> iStrResListener, HashMap<String, String> params) {
        return new CommonObjFileReq(method, urlStr, new MCListenerObj(iStrResListener, urlStr), params);
    }

    public static <T> ManyiObjReq getObjReq(int method, String urlStr, Object object, MCListenerObj.IObjResListener<T> iStrResListener, Class<T> responseClass, IAppAuthInterceptor iAppAuthInterceptor) {
        return new ManyiObjReq(method, urlStr, responseClass,
                new MCListenerObj(iStrResListener, urlStr), object, iAppAuthInterceptor);
    }

    public static Request<?> createReqToQue(Context mContext, Request commonReq) {
        commonReq.setTag(mContext.getClass().getName());
        return getQueue(mContext.getApplicationContext()).add(commonReq);
    }

    private static RequestQueue getQueue(Context mContext) {
        if (mQueue == null) {
            //HttpsURLConnection.setFollowRedirects(false);
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            HttpStackImpl hurlStack = new HttpStackImpl(null, HTTPSTrustManager.allowAllSSL());
            mQueue = Volley.newRequestQueue(mContext, hurlStack);
        }
        return mQueue;
    }

    public static void cancelAllRequest() {
        if (mQueue != null) {
            mQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
        }
    }

    public static void cancelAllRequest(Context mContext) {
        if (mQueue != null) {
            mQueue.cancelAll(mContext);
        }
    }

    public static void cancelAllRequest(com.android.volley.RequestQueue.RequestFilter filter) {
        if (mQueue != null) {
            mQueue.cancelAll(filter);
        }
    }

    public static void cancelAllRequest(final String cancelTag) {
        if (mQueue != null) {
            mQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    String tag = (String) request.getTag();
                    if (!TextUtils.isEmpty(tag) && tag.equals(cancelTag)) {
                        LogUtil.e("HTTP", "cancel http request url:" + request.getUrl() + " from:" + tag);
                        return true;
                    }
                    return false;
                }
            });
        }
    }
}
