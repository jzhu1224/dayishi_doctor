package com.chairoad.framework.push.huawei;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.chairoad.framework.push.PushManager;

/**
 * Created by yanxin on 17/9/21.
 */

public class HuaWeiReceive extends BroadcastReceiver {

    public final static String ACTION_CHECK_HUAWEI_PUSH_TOKEN = "com.chairoad.framework.push.huawei.ACTION_CHECK_HUAWEI_PUSH_TOKEN";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(ACTION_CHECK_HUAWEI_PUSH_TOKEN.equals(intent.getAction())) {
            PushManager.build().registHuaweiToken(context);
        }
    }
}
