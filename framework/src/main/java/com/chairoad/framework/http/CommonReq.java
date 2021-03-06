package com.chairoad.framework.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.chairoad.framework.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shejian on 2015/4/14.
 */
public class CommonReq extends Request<String> {

    private static final String TAG = "CommonReq";

    private final Response.Listener<String> mListener;
    protected Map<String, Object> paramsMap = new HashMap<>();
    private Object mObj;
    private HashMap<String, String> headerInfo;

    // 如何传参数
    public CommonReq(int method, String urlStr, HashMap<String, Object> paramsMap, MCListener mcListener) {
        super(method, urlStr, mcListener);
        this.setRetryPolicy(new DefaultRetryPolicy(20000, 0, 1f));
        mListener = mcListener;
        this.paramsMap = paramsMap;
    }
    public CommonReq(int method, String urlStr, Object obj, MCListener mcListener) {
        super(method, urlStr, mcListener);
        mListener = mcListener;
        mObj = obj;
    }

    // 如何传参数
    public CommonReq(int method, String urlStr,Map<String, Object> paramsMap, MCListenerObj<String> mcListener,HashMap<String, String> headerInfo) {
        super(method, urlStr, mcListener);
        mListener = mcListener;
        this.headerInfo = headerInfo;
        this.paramsMap = paramsMap;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        String str;
        if (null == mObj) {
            str = JSON.toJSONString(paramsMap, SerializerFeature.WriteMapNullValue);
        } else {
            str = JSON.toJSONString(mObj, SerializerFeature.WriteMapNullValue);
        }
        return str.getBytes();
    }

    @Override
    public String getUrl() {
        return super.getUrl();
    }

    @Override
    protected void deliverResponse(String response) {
        if (response != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=" + getParamsEncoding();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        LogUtil.i(TAG, "url:" + this.getUrl() + " parseNetworkResponse:" + jsonString);
        return Response.success(jsonString, getCacheEntry());
    }

}
