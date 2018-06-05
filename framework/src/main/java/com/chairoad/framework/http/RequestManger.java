package com.chairoad.framework.http;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by yanxin on 2015/11/27.
 */
public class RequestManger {

    private final String TAG = "HTTP";

    private final Context mContext;

    private RequestQueue mRequestQueue;

    private static RequestManger mRequestManager;

    private RequestManger(Context mContext) {
        this.mContext = mContext.getApplicationContext();
        mRequestQueue = Volley.newRequestQueue(this.mContext);
    }

    public static RequestManger getInstence(Context mContext) {
        if(mRequestManager == null) {
            mRequestManager = new RequestManger(mContext);
        }
        return mRequestManager;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }



}
