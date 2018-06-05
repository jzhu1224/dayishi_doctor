package com.chairoad.framework.push.xiaomi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.chairoad.framework.push.AbstractPush;
import com.chairoad.framework.push.PushConfig;
import com.chairoad.framework.util.LogUtil;

/**
 * Created by yanxin on 17/9/21.
 */

public class XiaoMiPush extends AbstractPush {

    private int REQUEST_RESTART_PUSH_SERVICE_TIME = 60 * 60 * 1000 * 12;//每半天重新注册一下小米push，防止过期失效
    private int REQUEST_RESTART_PUSH_SERVICE_FIRST_TIME = 10 * 60 * 1000;//重新注册小米第一次等待10分钟
    //public MiPushBridgeServiceBeta miPushBridgeServiceBeta = null;

    @Override
    public void connect(Context context) {
//        if(MqttConstants.mqttManager ==null ||
//                MqttConstants.mqttManager.getAsyncMqttClient() == null) {
//            return;
//        }
//
//        if (null != miPushBridgeServiceBeta) {
//            LogUtil.i(PushConfig.TAG, "Close the old mi push");
//            try {
//                miPushBridgeServiceBeta.close();
//            } catch (Exception e) {
//                LogUtil.i(PushConfig.TAG, "Close the old mi push Exception: "+e);
//            }
//            miPushBridgeServiceBeta = null;
//        }
//
//        miPushBridgeServiceBeta = new MiPushBridgeServiceBeta(MqttConstants.mqttManager.getAsyncMqttClient(), PushConfig.MIPUSH_APP_NAME,
//                PushConfig.MIPUSH_APP_ID, PushConfig.MIPUSH_APP_KEY);
//        try {
//            miPushBridgeServiceBeta.start();
//            setRepeatCheck(context);
//        } catch (Exception e) {
//            LogUtil.i(PushConfig.TAG, "MiPushBridgeServiceBeta start Exception: "+e);
//        }
    }

    @Override
    public void reconnect(Context context) {
//        if(miPushBridgeServiceBeta == null) {
//            connect(context);
//        } else {
//            try {
//                miPushBridgeServiceBeta.close();
//                Thread.sleep(500);
//                miPushBridgeServiceBeta.start();
//            }catch (Exception e) {
//                LogUtil.e(PushConfig.TAG,"xiaomi push reconnect Exception: "+e);
//            }
//        }
    }

    /**
     * 设置轮训注册小米push 防止token过期
     */
    private void setRepeatCheck(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent startIntent = new Intent(context.getApplicationContext(), XiaoMiReceive.class);
        startIntent.setAction(XiaoMiReceive.ACTION_CHECK_XIAOMI_PUSH_TOKEN);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, startIntent, PendingIntent.FLAG_NO_CREATE);
        if(pendingIntent != null) {
            LogUtil.i(PushConfig.TAG, "xiaomi 取消上一个定时检查token注册广播");
            am.cancel(pendingIntent);
        }
        pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, startIntent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + REQUEST_RESTART_PUSH_SERVICE_FIRST_TIME, REQUEST_RESTART_PUSH_SERVICE_TIME, pendingIntent);
    }
}
