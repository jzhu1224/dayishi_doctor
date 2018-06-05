package com.chairoad.framework.http;

import java.util.HashMap;

/**
 * Created by shuaijiman on 2015/6/26.
 */
public interface IAppAuthInterceptor {
    HashMap<String, String> intercept(HashMap<String, Object> paramsMap);
}
