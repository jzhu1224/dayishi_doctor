package com.jkdys.doctor.ui.chat;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.system.Os;
import android.text.TextUtils;
import com.chairoad.framework.util.ToastUtil;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseAppCompatActivity;
import com.jkdys.doctor.utils.AndroidBug5497Workaround;

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

        AndroidBug5497Workaround.assistActivity(this);
        initPhotoError();

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

        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());

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

    @TargetApi(Build.VERSION_CODES.N)
    private void initPhotoError(){
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

}
