package com.chairoad.framework.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.chairoad.framework.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

public class CommonObjReq<T> extends Request<T> {

    private static final String TAG = "HSHTTP";

    private final Response.Listener<T> mListener;
    private Class<T> targetClass;
    private Object mObj;
    protected HashMap<String, String> paramsMap = new HashMap<String, String>();
    protected HashMap<String, String> headers = new HashMap<String, String>();
    private Boolean followRedirects;

    public void putHeadersInfo(HashMap<String, String> headers) {
        headers.putAll(headers);
    }

    // 如何传参数
    public CommonObjReq(int method, String urlStr, HashMap<String, String> paramsMap, MCListenerObj<T> mcListener, Class<T> t) {
        super(method, urlStr, mcListener);
        this.setRetryPolicy(new DefaultRetryPolicy(20000, 0, 1f));
        targetClass = t;
        mListener = mcListener;
        this.paramsMap = paramsMap;
    }

    public CommonObjReq(int method, String urlStr, Object obj, MCListenerObj<T> mcListener, Class<T> t) {
        super(method, urlStr, mcListener);
        targetClass = t;
        mListener = mcListener;
        mObj = obj;
    }

    public Boolean getFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(Boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        String str;
        if (null == mObj) {
            str = JSON.toJSONString(paramsMap, SerializerFeature.WriteMapNullValue);
        } else {
            str = JSON.toJSONString(mObj, SerializerFeature.WriteMapNullValue);
        }
        LogUtil.i(TAG, "request---url:" + getUrl() + "  params:" + str);
        return str.getBytes();
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=" + getParamsEncoding();
    }

    @Override
    protected void deliverResponse(T response) {
        if (response != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        LogUtil.i(TAG, "response---url:" + this.getUrl() + " "+error.getMessage());
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        LogUtil.i(TAG, "response---url:" + this.getUrl() + " json: ");
        LogUtil.i(TAG, jsonString);
        if (mListener instanceof MCListenerObj) {
            MCListenerObj mcListenerObj = (MCListenerObj) mListener;
            mcListenerObj.onResponseHeaders(response.headers);
            mcListenerObj.onResponseJson(jsonString);
        }
        T flickrResponse = JSON.parseObject(jsonString, targetClass);
        return Response.success(flickrResponse, getCacheEntry());
    }

}
