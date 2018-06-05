package com.chairoad.framework.push;

import android.content.Context;

import com.chairoad.framework.push.huawei.HuaWeiPush;
import com.chairoad.framework.push.xiaomi.XiaoMiPush;
import com.chairoad.framework.util.LogUtil;
import com.chairoad.framework.util.PermissionUtil;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 手机厂商push管理
 * Created by yanxin on 17/9/21.
 */
public class PushManager {

    private static PushManager pushManager;

    private ExecutorService executorService;

    private HuaWeiPush huaWeiPush = null;
    private XiaoMiPush xiaoMiPush = null;

    private PushManager() {
        executorService = Executors.newFixedThreadPool(1);
        huaWeiPush = new HuaWeiPush();
        xiaoMiPush = new XiaoMiPush();
    }

    public static PushManager build() {
        if(pushManager == null) pushManager = new PushManager();
        return pushManager;
    }

    /**
     * 启动连接 各自手机厂商push
     * 如果需要连接华为push context必须是Activity
     */
    public void connect(final Context context) {
        LogUtil.i(PushConfig.TAG,"start connect xiami、huawei");
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                if(MiPushClient.shouldUseMIUIPush(context)) {
                    connectXiaoMi(context);
                } else if(PermissionUtil.isHuaWei()) {
                    connectHuaWei(context);
                }
            }
        });
    }

    /**
     * 连接华为push
     */
    public void connectHuaWei(Context context) {
        huaWeiPush.connect(context);
    }

    /**
     * 连接小米push
     */
    public void connectXiaoMi(Context context) {
        xiaoMiPush.connect(context);
    }

    /**
     * 重新连接小米push
     */
    public void reconnectXiaoMi(Context context) {
        xiaoMiPush.reconnect(context);
    }

    /**
     * 重新连接华为push
     */
    public void reconnectHuaWei(Context context) {
        huaWeiPush.reconnect(context);
    }

    /**
     * 重新注册华为token
     */
    public void registHuaweiToken(Context context) {
        huaWeiPush.registToken(context);
    }

    public void pushLog(String log) {
//        if(!TextUtils.isEmpty(log) && MqttConstants.mqttManager!=null) {
//            MqttConstants.mqttManager.pushLog(log);
//        }
    }
}
