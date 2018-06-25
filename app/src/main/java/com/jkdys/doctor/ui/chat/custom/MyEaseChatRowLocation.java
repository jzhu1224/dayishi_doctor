package com.jkdys.doctor.ui.chat.custom;

import android.content.Context;
import android.content.Intent;
import android.widget.BaseAdapter;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowLocation;
import com.jkdys.doctor.ui.chat.MyEaseBaiduMapActivity;

/**
 * Created by zhujiang on 2017/11/13.
 */

public class MyEaseChatRowLocation extends EaseChatRowLocation {

    private EMLocationMessageBody locBody;

    public MyEaseChatRowLocation(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onSetUpView() {
        super.onSetUpView();
        locBody = (EMLocationMessageBody) message.getBody();
    }

    @Override
    protected void onBubbleClick() {
        Intent intent = new Intent(context, MyEaseBaiduMapActivity.class);
        intent.putExtra("latitude", locBody.getLatitude());
        intent.putExtra("longitude", locBody.getLongitude());
        intent.putExtra("address", locBody.getAddress());
        activity.startActivity(intent);
    }
}
