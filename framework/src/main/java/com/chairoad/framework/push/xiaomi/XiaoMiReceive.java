package com.chairoad.framework.push.xiaomi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.chairoad.framework.push.PushManager;

/**
 * Created by yanxin on 17/9/21.
 */

public class XiaoMiReceive extends BroadcastReceiver {

    public final static String ACTION_CHECK_XIAOMI_PUSH_TOKEN = "com.chairoad.framework.push.xiaomi.ACTION_CHECK_XIAOMI_PUSH_TOKEN";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(ACTION_CHECK_XIAOMI_PUSH_TOKEN.equals(intent.getAction())) {
            PushManager.build().reconnectXiaoMi(context);
        }
    }
}
