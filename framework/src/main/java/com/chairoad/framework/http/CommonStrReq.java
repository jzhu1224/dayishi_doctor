package com.chairoad.framework.http;

import com.android.volley.AuthFailureError;
import com.chairoad.framework.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanxin
 */
public class CommonStrReq extends CommonReq {

    private static final String TAG = "HTTP";

    // 如何传参数
    public CommonStrReq(int method, String urlStr, HashMap<String, Object> paramsMap, MCListener mcListener) {
        super(method, urlStr,paramsMap,mcListener);
    }

    // 如何传参数
    public CommonStrReq(int method, String urlStr, Map<String, Object> paramsMap, MCListenerObj<String> mcListener, HashMap<String, String> headerInfo) {
        super(method, urlStr, paramsMap,mcListener,headerInfo);
    }

    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        StringBuilder paramStr = new StringBuilder();
        for(String key: paramsMap.keySet()) {
            if (paramStr.length() != 0) {
                paramStr.append('&');
            }
            paramStr.append(key).append('=').append(paramsMap.get(key).toString());
        }
        LogUtil.i(TAG, "request url:" + getUrl() + "  params:" + paramStr.toString());
        return paramStr.toString().getBytes();
    }

}
