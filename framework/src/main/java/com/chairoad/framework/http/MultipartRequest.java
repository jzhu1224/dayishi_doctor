package com.chairoad.framework.http;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.chairoad.framework.util.LogUtil;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhujiang on 2016/3/22.
 */
public class MultipartRequest<T> extends Request<T> {

    private static final String TAG = "HTTP";
    MultipartEntity entity = new MultipartEntity();
    private List<File> mFileParts;
    private String mFilePartName;

    private MCListenerObj<T> mListener;
    private Class<T> targetClass;

    private HashMap<String, String> mParams;
    private HashMap<String, String> headerInfo;


    public void setHeaderInfo(HashMap<String,String> headerInfo) {
        this.headerInfo = headerInfo;
    }

    /**
     * 单个文件
     *
     * @param url
     * @param filePartName
     * @param file
     * @param params
     */
    public MultipartRequest(String url,MCListenerObj<T> listenerObj, String filePartName, File file,
                            HashMap<String, String> params,Class<T> targetClass) {
        super(Method.POST, url, listenerObj);
        this.setRetryPolicy(new DefaultRetryPolicy(20000, 0, 1f));
        mFileParts = new ArrayList<>();
        if (file != null) {
            mFileParts.add(file);
        }
        mFilePartName = filePartName;
        mListener = listenerObj;
        mParams = params;
        this.targetClass = targetClass;
        buildMultipartEntity();
    }

    /**
     * 多个文件，对应一个key
     *
     * @param url
     * @param filePartName
     * @param files
     * @param params
     */
    public MultipartRequest(String url,MCListenerObj<T> listenerObj , String filePartName,
                            List<File> files, HashMap<String, String> params,Class<T> targetClass) {
        super(Method.POST, url, listenerObj);
        this.setRetryPolicy(new DefaultRetryPolicy(20000, 0, 1f));
        mFilePartName = filePartName;
        mListener = listenerObj;
        mFileParts = files;
        mParams = params;
        this.targetClass = targetClass;
        buildMultipartEntity();
    }

    private void buildMultipartEntity() {
        //entityBuilder = MultipartEntityBuilder.create();
        //add files
        if (mFileParts != null && mFileParts.size() > 0) {
            for (File file : mFileParts) {
                entity.addPart(mFilePartName, new FileBody(file));
            }
            long l = entity.getContentLength();
            Log.d(TAG, mFileParts.size() + "个，长度：" + l);
        }
        // add params
        try {
            if (mParams != null && mParams.size() > 0) {
                for (Map.Entry<String, String> entry : mParams.entrySet()) {
                    entity.addPart(
                            entry.getKey(),
                            new StringBody(entry.getValue(), Charset
                                    .forName("UTF-8")));
                }
            }
            //entity = entityBuilder.build();
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "UnsupportedEncodingException");
        }
    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            Log.e(TAG, "IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        Log.d(TAG, "parseNetworkResponse");
        if (response.headers != null) {
            for (Map.Entry<String, String> entry : response.headers
                    .entrySet()) {
                Log.d(TAG, entry.getKey() + "=" + entry.getValue());
            }
        }
        String parsed;
        try {
            parsed = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        LogUtil.i(TAG, "url:" + this.getUrl() + " parseNetworkResponse:" + parsed);
        T flickrResponse = JSON.parseObject(parsed, targetClass);
        return Response.success(flickrResponse, getCacheEntry());
    }


    /*
     * (non-Javadoc)
     *
     * @see com.android.volley.Request#getHeaders()
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Log.d(TAG, "getHeaders");

        if (headerInfo == null || headerInfo.equals(Collections.emptyMap())) {
            headerInfo = new HashMap<>();
        }
        return headerInfo;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
