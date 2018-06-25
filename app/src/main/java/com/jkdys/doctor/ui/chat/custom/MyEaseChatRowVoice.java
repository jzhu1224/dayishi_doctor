package com.jkdys.doctor.ui.chat.custom;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowVoice;
import com.hyphenate.util.EMLog;
import com.jkdys.doctor.R;
import com.jkdys.doctor.utils.ViewUtil;

/**
 * Created by zhujiang on 2017/11/15.
 */

public class MyEaseChatRowVoice extends EaseChatRowVoice {

    private ImageView voiceImageView;
    private TextView voiceLengthView;
    private ImageView readStatusView;
    private TextView voiceHintTextView;


    public MyEaseChatRowVoice(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_voice : R.layout.ease_row_sent_voice, this);
    }

    @Override
    protected void onFindViewById() {
        voiceImageView = ((ImageView) findViewById(R.id.iv_voice));
        voiceLengthView = (TextView) findViewById(R.id.tv_length);
        readStatusView = (ImageView) findViewById(R.id.iv_unread_voice);
        voiceHintTextView  = (TextView) findViewById(R.id.text_hint);
    }

    @Override
    protected void onSetUpView() {
        EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) message.getBody();
        int len = voiceBody.getLength();
        if(len>0){
            voiceLengthView.setText(voiceBody.getLength() + "\"");
            setViewWidthByVoiceLength(len);
            voiceLengthView.setVisibility(View.VISIBLE);
        }else{
            voiceLengthView.setVisibility(View.INVISIBLE);
        }
        if (MyEaseChatRowVoicePlayClickListener.playMsgId != null
                && MyEaseChatRowVoicePlayClickListener.playMsgId.equals(message.getMsgId()) && MyEaseChatRowVoicePlayClickListener.isPlaying) {
            AnimationDrawable voiceAnimation;
            if (message.direct() == EMMessage.Direct.RECEIVE) {
                voiceImageView.setImageResource(R.drawable.voice_from_animation);
            } else {
                voiceImageView.setImageResource(R.drawable.voice_to_animation);
            }
            voiceAnimation = (AnimationDrawable) voiceImageView.getDrawable();
            voiceAnimation.start();
        } else {
            if (message.direct() == EMMessage.Direct.RECEIVE) {
                voiceImageView.setImageResource(R.drawable.get_voice_03);
            } else {
                voiceImageView.setImageResource(R.drawable.send_voice_03);
            }
        }

        if (message.direct() == EMMessage.Direct.RECEIVE) {
            if (message.isListened()) {
                // hide the unread icon
                readStatusView.setVisibility(View.INVISIBLE);
            } else {
                readStatusView.setVisibility(View.VISIBLE);
            }
            EMLog.d(TAG, "it is receive msg");
            if (voiceBody.downloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING ||
                    voiceBody.downloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {
                progressBar.setVisibility(View.VISIBLE);
                setMessageReceiveCallback();
            } else {
                progressBar.setVisibility(View.INVISIBLE);

            }
            return;
        }

        // until here, handle sending voice message
        handleSendMessage();
    }

    @Override
    protected void onUpdateView() {
        super.onUpdateView();
    }

    @Override
    protected void onBubbleClick() {
        new MyEaseChatRowVoicePlayClickListener(message, voiceImageView, readStatusView, adapter, activity).onClick(bubbleLayout);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (MyEaseChatRowVoicePlayClickListener.currentPlayListener != null && MyEaseChatRowVoicePlayClickListener.isPlaying) {
            MyEaseChatRowVoicePlayClickListener.currentPlayListener.stopPlayVoice();
        }
    }

    private void setViewWidthByVoiceLength(int voiceLength) {
        float maxWidthDpi = 153;//dpi
        int maxWidthPx = (int)ViewUtil.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                maxWidthDpi,
                context.getResources().getDisplayMetrics());
        if (null != voiceHintTextView) {
            int pxWidth = voiceLength * 10;
            voiceHintTextView.setWidth(pxWidth > maxWidthPx ? maxWidthPx : pxWidth);
        }
    }
}
