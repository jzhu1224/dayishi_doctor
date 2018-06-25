package com.jkdys.doctor.ui.chat.custom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.model.EaseImageCache;
import com.hyphenate.easeui.model.styles.EaseMessageListItemStyle;
import com.hyphenate.easeui.utils.EaseImageUtils;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowFile;
import com.jkdys.doctor.MyApplication;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.chat.ShowBigImageActivity;
import com.jkdys.doctor.utils.ImageUtils;
import com.jkdys.doctor.utils.ViewUtil;

import java.io.File;

/**
 * Created by zhujiang on 2017/11/13.
 */

public class MyEaseChatRowImage extends EaseChatRowFile {

    protected ImageView imageView;
    private EMImageMessageBody imgBody;

    public MyEaseChatRowImage(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? R.layout.ease_row_received_picture : R.layout.ease_row_sent_picture, this);
    }

    @Override
    protected void onFindViewById() {
        percentageView = (TextView) findViewById(R.id.percentage);
        imageView = (ImageView) findViewById(R.id.image);
    }

    @Override
    public void setUpView(EMMessage message, int position, EaseChatMessageList.MessageListItemClickListener itemClickListener, EaseMessageListItemStyle itemStyle) {
        super.setUpView(message, position, itemClickListener, itemStyle);
    }

    @Override
    protected void onSetUpView() {
        percentageView.setVisibility(View.GONE);
        imgBody = (EMImageMessageBody) message.getBody();
        // received messages
        if (message.direct() == EMMessage.Direct.RECEIVE) {
            if (imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING ||
                    imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {
                imageView.setImageResource(R.drawable.no_cash_images_message_bg);
                //set the receive message callback
                setMessageReceiveCallback();
            } else {
                ((PorterShapeImageView)imageView).hideMask();
                progressBar.setVisibility(View.GONE);
                //percentageView.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.no_cash_images_message_bg);
                String thumbPath = imgBody.thumbnailLocalPath();
                if (!new File(thumbPath).exists()) {
                    // to make it compatible with thumbnail received in previous version
                    thumbPath = EaseImageUtils.getThumbnailImagePath(imgBody.getLocalUrl());
                }
                showImageView(thumbPath, imgBody.getLocalUrl(), message);
            }
            return;
        }


        final String filePath = imgBody.getLocalUrl();
        String thumbPath = EaseImageUtils.getThumbnailImagePath(imgBody.getLocalUrl());
        showImageView(thumbPath, filePath, message);
        handleSendMessage();
    }

    /**
     * handle sending message
     */
    protected void handleSendMessage() {
        setMessageSendCallback();
        switch (message.status()) {
            case SUCCESS:
                progressBar.setVisibility(View.INVISIBLE);
                ((PorterShapeImageView) imageView).hideMask();
//                if(percentageView != null)
//                    percentageView.setVisibility(View.INVISIBLE);
                statusView.setVisibility(View.INVISIBLE);
                break;
            case FAIL:
                ((PorterShapeImageView) imageView).hideMask();
                progressBar.setVisibility(View.INVISIBLE);
//                if(percentageView != null)
//                    percentageView.setVisibility(View.INVISIBLE);
                statusView.setVisibility(View.VISIBLE);
                break;
            case INPROGRESS:
                ((PorterShapeImageView) imageView).showMask();
                progressBar.setVisibility(View.VISIBLE);
//                if(percentageView != null){
//                    percentageView.setVisibility(View.VISIBLE);
//                    percentageView.setText(message.progress() + "%");
//                }
                statusView.setVisibility(View.INVISIBLE);
                break;
            default:
                ((PorterShapeImageView) imageView).hideMask();
                progressBar.setVisibility(View.INVISIBLE);
//                if(percentageView != null)
//                    percentageView.setVisibility(View.INVISIBLE);
                statusView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onUpdateView() {
        super.onUpdateView();
    }

    @Override
    protected void onBubbleClick() {
        if (imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING ||
                imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {
            //thumbnail image downloading
            return;
        } else if(imgBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.FAILED){
            ((PorterShapeImageView)imageView).showMask();
            progressBar.setVisibility(View.VISIBLE);
//            percentageView.setVisibility(View.VISIBLE);
            // retry download with click event of user
            EMClient.getInstance().chatManager().downloadThumbnail(message);
        }


        Intent intent = new Intent(context, ShowBigImageActivity.class);
        File file = new File(imgBody.getLocalUrl());
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            intent.putExtra("uri", uri);
        } else {
            // The local full size pic does not exist yet.
            // ShowBigImage needs to download it from the server
            // first
            String msgId = message.getMsgId();
            intent.putExtra("messageId", msgId);
            intent.putExtra("localUrl", imgBody.getLocalUrl());
        }
        if (message != null && message.direct() == EMMessage.Direct.RECEIVE && !message.isAcked()
                && message.getChatType() == EMMessage.ChatType.Chat) {
            try {
                EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        context.startActivity(intent);
    }

    private static int picWidth = ViewUtil.dipToPx(MyApplication.getInstance(),120);
    private static int picHeight = ViewUtil.dipToPx(MyApplication.getInstance(),150);

    /**
     * load image into image view
     *
     */
    private void showImageView(final String thumbernailPath, final String localFullSizePath,final EMMessage message) {
        // first check if the thumbnail image already loaded into cache
        Bitmap bitmap = EaseImageCache.getInstance().get(thumbernailPath);
        if (bitmap != null) {
            // thumbnail image is already loaded, reuse the drawable
            imageView.setImageBitmap(bitmap);
        } else {
            new AsyncTask<Object, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Object... args) {
                    File file = new File(thumbernailPath);
                    if (file.exists()) {
                        return ImageUtils.decodeScaleImage(thumbernailPath, picWidth, picHeight);
                    } else if (new File(imgBody.thumbnailLocalPath()).exists()) {
                        return ImageUtils.decodeScaleImage(imgBody.thumbnailLocalPath(), picWidth, picHeight);
                    }
                    else {
                        if (message.direct() == EMMessage.Direct.SEND) {
                            if (localFullSizePath != null && new File(localFullSizePath).exists()) {
                                return EaseImageUtils.decodeScaleImage(localFullSizePath, picWidth, picHeight);
                            } else {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    }
                }

                protected void onPostExecute(Bitmap image) {
                    if (image != null) {
                        imageView.setImageBitmap(image);
                        EaseImageCache.getInstance().put(thumbernailPath, image);
                    } else {
                        imageView.setImageResource(R.drawable.no_cash_images_message_bg);
                    }
                }
            }.execute();
        }
    }

}
