package com.jkdys.doctor.utils;

import android.support.annotation.NonNull;

import com.chairoad.framework.encrypt.MD5Util;
import com.chairoad.framework.util.LogUtil;
import com.google.gson.Gson;
import com.jkdys.doctor.BuildConfig;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

@Singleton
public class CommonHeaderInterceptor implements Interceptor {


    /**
     *
     * 请求header固定参数：
     token：令牌
     key：双方测试环境约定为D8539AA4AE8C431CABE2E8D5CD3C4585
     appversion：App版本号
     platform：移动终端名称，（ios，android）
     timestamp：时间戳
     signature：MD5加密结果
      
     signature  = (((key + appversion + packageId).ToMD5().ToUpper() + platform + data).ToMD5().ToUpper() + timestamp).ToMD5().ToUpper();
     data = “key1=value1&key2=value2”
      
     PackageId：
     iOS医生端="com.jkdys.doctor";
     iOS患者端="com.jkdys.patient";
      
     Android医生端 = "com.jkdys.doctor";
     Android患者端 = "com.jkdys.patient ";
      
     Platform：
     iOS = "ios";
     Android = "android";
      
     请示返回Json：
     {
     "code":1, ,
     "showmessage":false,// 表示是否需要以toast形式 提示message内容
     "showdialog":false,// 表示是否需要以dialog形式 提示message内容
     "msg":"返回提示信息",
     "data":null
     }
     如果showmessage、showdialog这两个字段都为false，不管接口code是多少pp都不需要提示弹出提示
     *
     */

    private LoginInfoUtil loginInfoUtil;

    private Gson gson;

    @Inject
    public CommonHeaderInterceptor(LoginInfoUtil loginInfoUtil, Gson gson) {
        this.loginInfoUtil = loginInfoUtil;
        this.gson = gson;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        //统一设置请求头
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder();
        requestBuilder.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");
        requestBuilder.header("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5");
        requestBuilder.header("Proxy-Connection", "keep-alive");
        requestBuilder.header("Cache-Control", "max-age=0");


        // requestBuilder.header("X-Forwarded-For","114.114.114.117")
        requestBuilder.method(original.method(), original.body());

        String method = original.method();

        HashMap<String, Object> rootMap = new HashMap<>();

        if ("GET".equals(method)) {
            HttpUrl mHttpUrl = original.url();
            Set<String> paramNames = mHttpUrl.queryParameterNames();
            for (String key : paramNames) {
                rootMap.put(key, mHttpUrl.queryParameter(key));
            }

        } else if ("POST".equals(method)) {
            RequestBody body = original.body();
            if (body instanceof FormBody) {
                for (int i = 0; i < ((FormBody) body).size(); i++) {
                    rootMap.put(((FormBody) body).encodedName(i), ((FormBody) body).encodedValue(i));
                }
            } else {
                Buffer buffer = new Buffer();
                body.writeTo(buffer);
                String oldJsonParams = buffer.readUtf8();
                try {
                    rootMap = gson.fromJson(oldJsonParams, HashMap.class); // 原始参数
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        if (rootMap == null)
            rootMap = new HashMap<>();

        List<Map.Entry<String, Object>> params = new ArrayList<>(rootMap.entrySet());//
        Collections.sort(params, (o1, o2) -> {
            if (o1.getKey() == null || o2.getKey() == null)
                return 0;

            return o1.getKey().compareTo(o2.getKey());
        });

        String secret = "";
        for (Map.Entry<String, Object> param : params) {
            String key = param.getKey();
            if("processRequestError".equals(key)) {
                continue;
            }
            Object value = param.getValue();
            secret = secret + key + "=" + value + "&";
        }
        if (secret.endsWith("&")) {
            secret = secret.substring(0, secret.length() - 1);
        }

        LogUtil.e("zj","secret:"+secret);

        String key = "D8539AA4AE8C431CABE2E8D5CD3C4585";

        String appversion = BuildConfig.VERSION_NAME;

        String packageId = BuildConfig.APPLICATION_ID;
        String platform = "android";

        long timestamp = System.currentTimeMillis();


        String signature = MD5Util.getMD5String((key + appversion + packageId).toUpperCase() +
                MD5Util.getMD5String(platform + secret).toUpperCase() + timestamp);

        signature = MD5Util.getMD5String(signature).toUpperCase();


        requestBuilder.header("token", loginInfoUtil.getToken());
        requestBuilder.header("packageId", packageId);
        requestBuilder.header("appversion", appversion);
        requestBuilder.header("platform", platform);
        requestBuilder.header("timestamp", timestamp+"");
        requestBuilder.header("signature", signature);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}