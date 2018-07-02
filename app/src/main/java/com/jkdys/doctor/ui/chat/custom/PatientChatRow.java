package com.jkdys.doctor.ui.chat.custom;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.chat.ChatConstant;

public class PatientChatRow extends EaseChatRow {

    ImageView imgHeader;
    TextView tvName;

    public PatientChatRow(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE?
                R.layout.ease_patient_row_sent:R.layout.ease_patient_row_sent, this);
    }

    @Override
    protected void onFindViewById() {
        imgHeader = findViewById(R.id.img_header);
        tvName = findViewById(R.id.tv_name);
    }

    @Override
    protected void onUpdateView() {

    }

    @Override
    protected void onSetUpView() {
        tvName.setText(message.getStringAttribute(ChatConstant.MESSAGE_ATTR_PATIENT_NAME,""));
    }

    @Override
    protected void onBubbleClick() {

    }
}
