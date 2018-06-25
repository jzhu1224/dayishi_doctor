package com.jkdys.doctor.ui.chat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chairoad.framework.util.ToastUtil;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * @author zhujiang
 */
public class ChatFragment extends EaseChatFragment {

    private final int REQUEST_CODE_LOCAL_IMAGE = 10001;

    @Override
    protected void setUpView() {
        setChatFragmentHelper(new MyFragmentHelper());
        super.setUpView();
    }

    //从本地选择图片
    @Override
    protected void selectPicFromLocal() {

        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    Intent intent = new Intent(getContext(), MultiImageSelectorActivity.class);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                    startActivityForResult(intent, REQUEST_CODE_LOCAL_IMAGE);
                } else {
                    ToastUtil.show(getActivity(),"访问相机或者读取媒体权限被拒绝");
                }
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                ToastUtil.show(getActivity(),"访问相机或者读取媒体权限被拒绝");
            }

        }).withErrorListener(error -> ToastUtil.show(getActivity(),"error:"+error.name())).check();
    }

    @Override
    protected void selectLocation() {
        startActivityForResult(new Intent(getActivity(), MyEaseBaiduMapActivity.class), REQUEST_CODE_MAP);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_LOCAL_IMAGE:
                    List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    for (String path : paths) {
                        final String p = path;
                        listView.postDelayed(() -> sendImageMessage(p), 100);
                    }
                    break;
            }
        }

    }

    public class MyFragmentHelper implements EaseChatFragmentHelper {

        @Override
        public void onAvatarClick(String username) {

        }

        @Override
        public void onAvatarLongClick(String username) {

        }

        @Override
        public boolean onMessageBubbleClick(EMMessage message) {
            //消息框点击事件，demo这里不做覆盖，如需覆盖，return true
            return false;
        }

        @Override
        public void onMessageBubbleLongClick(EMMessage message) {

        }

        @Override
        public boolean onExtendMenuItemClick(int itemId, View view) {
            //keep exist extend menu
            return false;
        }

        @Override
        public void onSetMessageAttributes(EMMessage message) {
//            UserInfo userInfo = UserInfoUtil.instence().get();
//            if (userInfo != null) {
//                message.setAttribute("avatar" + userInfo.getHxUserName(), userInfo.getHeadImgUrl());
//                message.setAttribute("nickname" + userInfo.getHxUserName(), userInfo.getNickName());
//                message.setAttribute("uid" + userInfo.getHxUserName(), userInfo.getUid());
//                message.setAttribute("avatar" + toChatUsername, avatar);
//                message.setAttribute("nickname" + toChatUsername, nickname);
//                message.setAttribute("uid"+toChatUsername, userInfo.getUid());
//            }
        }

        @Override
        public void onEnterToChatDetails() {

        }

        @Override
        public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
            return null;
        }

    }

}
