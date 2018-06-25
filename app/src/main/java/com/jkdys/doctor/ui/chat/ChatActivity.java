package com.jkdys.doctor.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.chairoad.framework.util.ToastUtil;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseAppCompatActivity;

/**
 * Created by zhujiang on 2017/10/26.
 */
public class ChatActivity extends BaseAppCompatActivity {

    //保持和EaseConstant.EXTRA_USER_ID值一样
    public static final String PARAM_USERID = "userId";

    private EaseChatFragment chatFragment;
    private String toChatUsername;

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toChatUsername = getIntent().getExtras().getString(PARAM_USERID);
        if (TextUtils.isEmpty(toChatUsername)) {
            ToastUtil.show(this, "参数错误");
            finish();
            return;
        }
        EaseUser easeUser = EaseUserUtils.getUserInfo(toChatUsername);
        if (easeUser != null) {
            toolbar.setTitle(easeUser.getNickname());
        } else {
            toolbar.setTitle(toChatUsername);
        }

        chatFragment = new ChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String username = intent.getStringExtra(PARAM_USERID);
        if (toChatUsername.equals(username)) {
            super.onNewIntent(intent);
        } else {
            finish();
            startActivity(intent);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.em_activity_chat;
    }

    @Override
    public void onBackPressed() {
        if (chatFragment != null) {
            chatFragment.onBackPressed();
        }
        super.onBackPressed();
    }

}
