package com.chairoad.framework.push.xiaomi;

/**
 * Created by lxl on 2016/1/26.
 */

import android.content.Context;
import android.text.TextUtils;

import com.chairoad.framework.util.LogUtil;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

public class MiPushMessageReceiverBeta extends PushMessageReceiver {
    public static final String TAG = "PUSH";

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        LogUtil.i(TAG, "Successfully get MiPush Message from server:" + message.getMessageId());
//        Intent intent = new Intent();
//        intent.setAction(MiPushBridgeServiceBeta.INTERNAL_ACTION_BRIDGED_MESSAGE_ARRIVED);
//        intent.putExtra(MiPushBridgeServiceBeta.INTERNAL_KEY_BRIDGED_MESSAGE_ARRIVED, message.toBundle());
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        LogUtil.i(TAG, "xiaomi push onReceiveRegisterResult");
        String command = message.getCommand();
        if (!MiPushClient.COMMAND_REGISTER.equals(command)) {
            LogUtil.e(TAG, "Unable to register MiPush Token! command message: " + message.toString());
            return;
        }

        if (message.getResultCode() != ErrorCode.SUCCESS) {
            LogUtil.e(TAG, "Fail to register MiPush Token! error code: [" + message.getResultCode() + "] error message: " + message.getReason());
            return;
        }

        List<String> arguments = message.getCommandArguments();
        String regID = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        if(TextUtils.isEmpty(regID)) {
            LogUtil.i(TAG, "Successfully get MiPush token but token is empty");
            return;
        }
        LogUtil.i(TAG, "Successfully get MiPush token [" + regID + "]");
        callbackMessageRegister(context, regID);
    }

//    @Override
//    public void onCommandResult(Context var1, MiPushCommandMessage var2) {
//        LogUtil.i(TAG, "xiaomi push onCommandResult resultcode:"+var2.getResultCode());
//        if(var2.getCommand().equals(MiPushClient.COMMAND_REGISTER)) {
//            if(var2.getResultCode() == ErrorCode.SUCCESS) {
//                String token = var2.getCommandArguments().get(0);
//                LogUtil.i(TAG, "xiaomi push token is "+token);
//                callbackMessageRegister(var1, token);
//            }
//        }
//    }

    private void callbackMessageRegister(Context context, String regID) {
//        Intent intent = new Intent();
//        intent.setAction(MiPushBridgeServiceBeta.INTERNAL_ACTION_BRIDGED_REGISTER_TOKEN);
//        intent.putExtra(MiPushBridgeServiceBeta.INTERNAL_KEY_BRIDGED_REGISTER_TOKEN, regID);
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
