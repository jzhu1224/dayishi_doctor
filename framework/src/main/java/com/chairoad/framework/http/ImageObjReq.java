package com.chairoad.framework.http;

import com.alibaba.fastjson.JSON;
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
public class ImageObjReq<T> extends Request<T> {

    private static final String TAG = "HTTP";

    private final Response.Listener<T> mListener;
    private Class<T> targetClass;
    private HashMap<String, String> paramsMap = new HashMap<String, String>();


    // 如何传参数
    public ImageObjReq(int method, String urlStr, HashMap<String, String> paramsMap, MCListenerObj<T> mcListener, Class<T> t) {
        super(method, urlStr, mcListener);
        this.setRetryPolicy(new DefaultRetryPolicy(60000, 0, 1f));
        // this request would never use cache.
        setShouldCache(false);
        mListener = mcListener;
        targetClass = t;
        this.paramsMap = paramsMap;
    }


//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        headers.put("Content-Type","application/x-www-form-urlencoded");
//        return headers;
//    }


    @Override
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return paramsMap;
    };


//    @Override
//    public byte[] getBody() throws AuthFailureError {
//        String str;
//        if (null == mObj) {
//            str = JSON.toJSONString(paramsMap, SerializerFeature.WriteMapNullValue);
//        } else {
//            str = JSON.toJSONString(mObj, SerializerFeature.WriteMapNullValue);
//        }
//        return str.getBytes();
//    }

//    @Override
//    public String getBodyContentType() {
//        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
//    }

    @Override
    protected void deliverResponse(T response) {
        if (response != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        LogUtil.i(TAG, "url:" + this.getUrl() + " parseNetworkResponse:" + jsonString);
        T flickrResponse = JSON.parseObject(jsonString, targetClass);
        return Response.success(flickrResponse, getCacheEntry());
    }

}
