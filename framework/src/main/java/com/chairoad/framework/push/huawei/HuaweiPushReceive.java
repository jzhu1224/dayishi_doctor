package com.chairoad.framework.push.huawei;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.chairoad.framework.push.PushConfig;
import com.chairoad.framework.push.event.PushEvent;
import com.chairoad.framework.util.LogUtil;
import com.hyphenate.chat.EMHWPushReceiver;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by yanxin on 17/9/8.
 */

public class HuaweiPushReceive extends EMHWPushReceiver {

    @Override
    public void onPushMsg(Context context, byte[] bytes, String s) {
        LogUtil.i(PushConfig.TAG,"huawei msg: "+new String(bytes));
    }

    @Override
    public void onPushState(Context context, boolean b) {
        LogUtil.i(PushConfig.TAG,"huawei onPushState: "+b);
    }

    @Override
    public void onToken(Context context, String token, Bundle var3) {
        LogUtil.i(PushConfig.TAG,"huawei token: "+token);
        super.onToken(context,token,var3);
        //subscribeToken(token);
    }

    @Override
    public void onEvent(Context context, Event event, Bundle bundle) {
        LogUtil.i(PushConfig.TAG,"huawei onEvent");
        String message = bundle.getString(BOUND_KEY.pushMsgKey);
        if(!TextUtils.isEmpty(message)) {
            JSONArray jsonArray = JSONArray.parseArray(message);
            if(jsonArray.size() > 0 && !TextUtils.isEmpty(jsonArray.getString(0))) {
                EventBus.getDefault().post(new PushEvent(jsonArray.getString(0)));
            }
        }

    }

}
