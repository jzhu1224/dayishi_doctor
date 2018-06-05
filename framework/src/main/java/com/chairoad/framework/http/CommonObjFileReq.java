package com.chairoad.framework.http;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Irene on 2015/7/30.
 */

public class CommonObjFileReq extends Request<byte[]> {

    private final Response.Listener<byte[]> mListener;
    private Map<String, String> mParams;

    public CommonObjFileReq(int post, String mUrl, MCListenerObj<byte[]> mcListener, HashMap<String, String> params) {
        super(post, mUrl, mcListener);
        this.setRetryPolicy(new DefaultRetryPolicy(2500, 1, 1f));
        setShouldCache(false);
        mListener = mcListener;
        mParams=params;
    }

    @Override
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return mParams;
    };


    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}