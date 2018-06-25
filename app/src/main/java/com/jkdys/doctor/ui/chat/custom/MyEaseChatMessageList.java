package com.jkdys.doctor.ui.chat.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.jkdys.doctor.R;

/**
 * Created by zhujiang on 2017/10/27.
 */

public class MyEaseChatMessageList extends EaseChatMessageList {

    public MyEaseChatMessageList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyEaseChatMessageList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEaseChatMessageList(Context context) {
        super(context);
    }

    @Override
    public void init(String toChatUsername, int chatType, EaseCustomChatRowProvider customChatRowProvider) {
        //super.init(toChatUsername, chatType, customChatRowProvider);

        this.chatType = chatType;
        this.toChatUsername = toChatUsername;

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername, EaseCommonUtils.getConversationType(chatType), true);
        messageAdapter = new MyEaseMessageAdapter(context, toChatUsername, chatType, listView);
        messageAdapter.setItemStyle(itemStyle);
        messageAdapter.setCustomChatRowProvider(customChatRowProvider);
        // set message adapter
        listView.setAdapter(messageAdapter);
        refreshSelectLast();
    }
}
