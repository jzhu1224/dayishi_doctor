package com.chairoad.framework.push.huawei;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.chairoad.framework.push.AbstractPush;
import com.chairoad.framework.push.PushConfig;
import com.chairoad.framework.push.event.HuaWeiPushEvent;
import com.chairoad.framework.util.LogUtil;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.api.HuaweiApiClient;
import com.huawei.hms.support.api.client.PendingResult;
import com.huawei.hms.support.api.client.ResultCallback;
import com.huawei.hms.support.api.push.HuaweiPush;
import com.huawei.hms.support.api.push.TokenResult;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by yanxin on 17/9/21.
 */

public class HuaWeiPush extends AbstractPush {

    private int REQUEST_RESTART_PUSH_SERVICE_TIME = 60 * 60 * 1000 * 12;//每半天重新注册一下华为push，防止过期失效
    private int REQUEST_RESTART_PUSH_SERVICE_FIRST_TIME = 10 * 60 * 1000;//重新注册华为第一次等待10分钟
    private HuaweiApiClient client;

    @Override
    public void connect(final Context mContext) {
        if(client != null) {
            if (!client.isConnecting() && !client.isConnected()) {
                client.connect();
            }
            return;
        }
        client = new HuaweiApiClient.Builder(mContext.getApplicationContext())
                .addApi(HuaweiPush.PUSH_API)
                .addConnectionCallbacks(new HuaweiApiClient.ConnectionCallbacks() {

                    @Override
                    public void onConnected() {
                        LogUtil.i(PushConfig.TAG,"huawei onConnected");
                        getTokenAsyn();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        LogUtil.e(PushConfig.TAG,"huwei onConnectionSuspended: "+i);
                    }
                })
                .addOnConnectionFailedListener(new HuaweiApiClient.OnConnectionFailedListener() {

                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        LogUtil.e(PushConfig.TAG,"huawei onConnectionFailed: "+connectionResult.getErrorCode());
                        if(HuaweiApiAvailability.getInstance().isUserResolvableError(connectionResult.getErrorCode())) {
                            if(mContext instanceof Activity) {
                                HuaweiApiAvailability.getInstance().resolveError((Activity) mContext, connectionResult.getErrorCode(), PushConfig.REQUEST_CODE_HUAWEI);
                            } else {
                                EventBus.getDefault().post(new HuaWeiPushEvent(connectionResult.getErrorCode()));
                            }
                        }
                    }
                })
                .build();

        client.connect();
        setRepeatCheck(mContext);
    }

    @Override
    public void registToken(Context context) {
        if(client != null && client.isConnected()) {
            getTokenAsyn();
        } else {
            LogUtil.e(PushConfig.TAG, "huawei 重新注册token失败，原因: HuaweiApiClient未连接");
        }
    }

    private void getTokenAsyn() {
        if(client == null || !client.isConnected()) {
            LogUtil.e(PushConfig.TAG, "huawei 获取token失败，原因: HuaweiApiClient未连接");
            return;
        }

        //需要在子线程中调用函数
//        new Thread() {
//            public void run() {
//                LogUtil.d("HUAWEI", "同步接口获取push token");
//                PendingResult<TokenResult> tokenResult = HuaweiPush.HuaweiPushApi.getToken(client);
//                TokenResult result = tokenResult.await();
//                LogUtil.d("HUAWEI", "Retcode: "+result.getTokenRes().getRetCode());
//            };
//        }.start();

        LogUtil.i(PushConfig.TAG, "huawei 异步接口获取push token");
        PendingResult<TokenResult> tokenResult = HuaweiPush.HuaweiPushApi.getToken(client);
        tokenResult.setResultCallback(new ResultCallback<TokenResult>() {

            @Override
            public void onResult(TokenResult result) {
                LogUtil.i(PushConfig.TAG, "huawei 异步接口获取结束 Retcode: "+result.getTokenRes().getRetCode());
                //这边的结果只表明接口调用成功，是否能收到响应结果只在广播中接收
            }
        });
    }

    /**
     * 设置轮训注册华为push 防止token过期
     */
    private void setRepeatCheck(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent startIntent = new Intent(context.getApplicationContext(), HuaWeiReceive.class);
        startIntent.setAction(HuaWeiReceive.ACTION_CHECK_HUAWEI_PUSH_TOKEN);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, startIntent, PendingIntent.FLAG_NO_CREATE);
        if(pendingIntent != null) {
            LogUtil.i(PushConfig.TAG, "huawei 取消上一个定时检查token注册广播");
            am.cancel(pendingIntent);
        }
        pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, startIntent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + REQUEST_RESTART_PUSH_SERVICE_FIRST_TIME, REQUEST_RESTART_PUSH_SERVICE_TIME, pendingIntent);
    }
}
