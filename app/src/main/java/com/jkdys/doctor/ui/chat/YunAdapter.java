package com.jkdys.doctor.ui.chat;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chairoad.framework.util.DateUtil;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.chat.ChatHelper;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.widget.NumView;

public class YunAdapter extends AbstractAdapter<EMConversation> {

    private Context context;

    public YunAdapter(Context context) {
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_yun, parent, false);
        }
        YunAdapter.ViewHolder holder = (YunAdapter.ViewHolder) convertView.getTag();

        if (holder == null) {
            holder = new YunAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.unreadLabel = (NumView) convertView.findViewById(R.id.unread_msg_number);
            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.msgState = convertView.findViewById(R.id.msg_state);
            holder.list_itease_layout = (RelativeLayout) convertView.findViewById(R.id.list_itease_layout);
            holder.motioned = (TextView) convertView.findViewById(R.id.mentioned);
            holder.line = convertView.findViewById(R.id.line);

            convertView.setTag(holder);
        }

        EMConversation emConversation = getItem(i);
        EMMessage emMessage = emConversation.getLastMessage();
        String avatar = "";
        String nickname = "";

        EaseUser easeUser = ChatHelper.getInstance().getUserInfo(emConversation.conversationId());
        if (easeUser != null) {
            avatar = easeUser.getAvatar();
            nickname = easeUser.getNickname();
        }
        if (TextUtils.isEmpty(nickname)) {
            nickname = emConversation.conversationId();
        }

        ImageLoader.with(context).placeholder(R.drawable.img_doctor).load(avatar).into(holder.avatar);
        holder.name.setText(nickname);
        if (emConversation.getUnreadMsgCount() > 0) {
            // show unread message count
            holder.unreadLabel.setText(String.valueOf(emConversation.getUnreadMsgCount()));
            holder.unreadLabel.setVisibility(View.VISIBLE);
        } else {
            holder.unreadLabel.setVisibility(View.INVISIBLE);
        }

        String lastMessage = EaseCommonUtils.getMessageDigest(emMessage, getContext());
        if (!TextUtils.isEmpty(lastMessage)) {
            holder.message.setText(EaseSmileUtils.getSmiledText(getContext(), lastMessage),
                    TextView.BufferType.SPANNABLE);
        } else {
            holder.message.setText("");
        }

        holder.time.setText(DateUtil.displayDateAndTime(emConversation.getLastMessage().getMsgTime()));

        return convertView;
    }

    private static class ViewHolder {
        /**
         * who you chat with
         */
        TextView name;
        /**
         * unread message count
         */
        NumView unreadLabel;
        /**
         * content of last message
         */
        TextView message;
        /**
         * time of last message
         */
        TextView time;
        /**
         * avatar
         */
        ImageView avatar;
        /**
         * status of last message
         */
        View msgState;
        /**
         * layout
         */
        RelativeLayout list_itease_layout;
        TextView motioned;
        View line;
    }
}
