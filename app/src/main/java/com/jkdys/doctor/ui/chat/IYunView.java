package com.jkdys.doctor.ui.chat;

import com.hyphenate.chat.EMConversation;
import com.jkdys.doctor.ui.BaseView;
import java.util.List;

public interface IYunView extends BaseView {
    void onLoadSuccess(List<EMConversation> list);
}