package com.jkdys.doctor.ui.chat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.chairoad.framework.util.ToastUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.PathUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.chat.ChatHelper;
import com.jkdys.doctor.ui.chat.custom.MyEaseChatExtendMenu;
import com.jkdys.doctor.ui.main.MainActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * @author zhujiang
 */
public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_SELECT_AT_USER = 15;


    /**
     * 照片 拍照 位置
     *
     *
     */
    static final int ITEM_TAKE_PICTURE = 1;
    static final int ITEM_PICTURE = 2;
    static final int ITEM_LOCATION = 3;
    static final int ITEM_PATIENT = 4;

    protected int[] itemStrings = { R.string.attach_picture, R.string.attach_take_pic, R.string.attach_location };
    protected int[] itemIconFonts = {R.drawable.img_doctor,R.drawable.img_doctor,R.drawable.img_doctor, R.drawable.img_doctor};
    protected int[] itemIds = { ITEM_PICTURE, ITEM_TAKE_PICTURE, ITEM_LOCATION, ITEM_PATIENT};

    private MyEaseChatExtendMenu.EaseChatExtendMenuItemClickListener myExtendMenuItemClickListener;

    private static final int REQUEST_CODE_LOCAL_IMAGE = 4;
    protected static final int REQUEST_CODE_PATIENT = 4;


    @Override
    protected void setUpView() {
        setChatFragmentHelper(this);
        super.setUpView();
    }

    @Override
    protected void initView() {
        myExtendMenuItemClickListener = new MyItemClickListener();
        super.initView();
    }

    @Override
    protected void registerExtendMenuItem() {
        for(int i = 0; i < itemStrings.length; i++){
            inputMenu.registerExtendMenuItem(itemStrings[i], itemIconFonts[i], itemIds[i], myExtendMenuItemClickListener);
        }
    }


    /**
     * handle the click event for extend menu
     *
     */
    class MyItemClickListener implements MyEaseChatExtendMenu.EaseChatExtendMenuItemClickListener{

        @Override
        public void onClick(int itemId, View view) {
            if(chatFragmentHelper != null){
                if(chatFragmentHelper.onExtendMenuItemClick(itemId, view)){
                    return;
                }
            }
            switch (itemId) {
                case ITEM_TAKE_PICTURE:
                    selectPicFromCamera();
                    break;
                case ITEM_PICTURE:
                    selectPicFromLocal();
                    break;
                case ITEM_LOCATION:
                    startActivityForResult(new Intent(getActivity(), MyEaseBaiduMapActivity.class), REQUEST_CODE_MAP);
                    break;
                case ITEM_PATIENT:
                    //REQUEST_CODE_PATIENT请求患者数据
                    break;
                default:
                    break;
            }
        }

    }

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
    protected void selectPicFromCamera() {
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (!EaseCommonUtils.isSdcardExist()) {
                            Toast.makeText(getActivity(), com.hyphenate.easeui.R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        cameraFile = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser()
                                + System.currentTimeMillis() + ".jpg");
                        //noinspection ResultOfMethodCallIgnored
                        cameraFile.getParentFile().mkdirs();
                        startActivityForResult(
                                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                                REQUEST_CODE_CAMERA);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        ToastUtil.show(getActivity(),"访问相机或者读取媒体权限被拒绝");
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        ToastUtil.show(getActivity(),"访问相机或者读取媒体权限被拒绝");
                    }
                }).check();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case REQUEST_CODE_SELECT_VIDEO: //send the video
                    if (data != null) {
                        int duration = data.getIntExtra("dur", 0);
                        String videoPath = data.getStringExtra("path");
                        File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                            ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.close();
                            sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_FILE: //send the file
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            sendFileByUri(uri);
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_AT_USER:
                    if(data != null){
                        String username = data.getStringExtra("username");
                        inputAtUsername(username, false);
                    }
                    break;

                case REQUEST_CODE_LOCAL_IMAGE:
                    // 获取返回的图片列表
                    List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    for (String path:paths) {
                        final String p = path;
                        listView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                sendImageMessage(p);
                            }
                        },100);
                    }
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {

    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return new CustomChatRowProvider();
    }


    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {
        if (null != chatFragmentClickListener) {
            chatFragmentClickListener.onAvatarClick(username);
        }
    }

    @Override
    public void onAvatarLongClick(String username) {
        inputAtUsername(username);
    }


    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }
    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        super.onCmdMessageReceived(messages);
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        //keep exist extend menu
        return false;
    }

    /**
     * chat row provider
     *
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //here the number is the message type in EMMessage::Type
            //which is used to count the number of different chat row
            return 0;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
//            if(message.getType() == EMMessage.Type.TXT){
//                //自定义类型
//                if (message.getStringAttribute(ChatConstant.MESSAGE_ATTR_IS_MISSED_CALL,"").equals("1") ||
//                        message.getStringAttribute(ChatConstant.MESSAGE_ATTR_IS_CALL_RECORD,"").equals("1")) {
//                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_CALL_RECORD : MESSAGE_TYPE_SENT_CALL_RECORD;
//                }
//            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
//            if(message.getType() == EMMessage.Type.TXT){
//                if (message.getStringAttribute(ChatConstant.MESSAGE_ATTR_IS_MISSED_CALL,"").equals("1") ||
//                        message.getStringAttribute(ChatConstant.MESSAGE_ATTR_IS_CALL_RECORD,"").equals("1")) {
//                    return new CallRecordChatRow(getActivity(), message, position, adapter);
//                }
//            }
            return null;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    ChatFragmentClickListener chatFragmentClickListener;

    public void setChatFragmentClickListener(ChatFragmentClickListener chatFragmentClickListener) {
        this.chatFragmentClickListener = chatFragmentClickListener;
    }

    public interface ChatFragmentClickListener {
        void onAvatarClick(String username);
    }

    @Override
    protected void setListItemClickListener() {
        messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
                if(chatFragmentHelper != null){
                    chatFragmentHelper.onAvatarClick(username);
                }
            }

            @Override
            public void onUserAvatarLongClick(String username) {
                if(chatFragmentHelper != null){
                    chatFragmentHelper.onAvatarLongClick(username);
                }
            }

            @Override
            public void onResendClick(final EMMessage message) {
                new EaseAlertDialog(getActivity(), R.string.blank, R.string.is_resend, null, (EaseAlertDialog.AlertDialogUser) (confirmed, bundle) -> {
                    if (!confirmed) {
                        return;
                    }
                    resendMessage(message);
                }, true).show();
            }

            @Override
            public void onBubbleLongClick(EMMessage message) {
                contextMenuMessage = message;
                if(chatFragmentHelper != null){
                    chatFragmentHelper.onMessageBubbleLongClick(message);
                }
            }

            @Override
            public boolean onBubbleClick(EMMessage message) {
                if(chatFragmentHelper == null){
                    return false;
                }
                return chatFragmentHelper.onMessageBubbleClick(message);
            }

        });
    }
}
