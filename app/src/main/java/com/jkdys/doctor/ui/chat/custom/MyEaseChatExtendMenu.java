package com.jkdys.doctor.ui.chat.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.jkdys.doctor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhujiang on 2017/11/13.
 */

public class MyEaseChatExtendMenu extends EaseChatExtendMenu {

    private List<ChatMenuItemModel> itemModels = new ArrayList<>();

    public MyEaseChatExtendMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyEaseChatExtendMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEaseChatExtendMenu(Context context) {
        super(context);
    }

    @Override
    public void init() {
        setAdapter(new ItemAdapter(context,itemModels));
    }

    public void registerMenuItem(String name, int drawableRes, int itemId, EaseChatExtendMenuItemClickListener listener) {
        ChatMenuItemModel item = new ChatMenuItemModel();
        item.name = name;
        item.image = drawableRes;
        item.id = itemId;
        item.clickListener = listener;
        itemModels.add(item);
    }

    private class ItemAdapter extends ArrayAdapter<ChatMenuItemModel> {

        private Context context;

        public ItemAdapter(Context context, List<ChatMenuItemModel> objects) {
            super(context, 0, objects);
            this.context = context;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ChatMenuItem menuItem = null;
            if(convertView == null){
                convertView = new ChatMenuItem(context);
            }
            menuItem = (ChatMenuItem) convertView;
            menuItem.setImage(getItem(position).image);
            menuItem.setText(getItem(position).name);
            menuItem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(getItem(position).clickListener != null){
                        getItem(position).clickListener.onClick(getItem(position).id, v);
                    }
                }
            });
            return convertView;
        }
    }


    class ChatMenuItemModel{
        String name;
        int image;
        int id;
        EaseChatExtendMenu.EaseChatExtendMenuItemClickListener clickListener;
    }

    class ChatMenuItem extends LinearLayout {
        private ImageView imageView;
        private TextView textView;

        public ChatMenuItem(Context context, AttributeSet attrs, int defStyle) {
            this(context, attrs);
        }

        public ChatMenuItem(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context, attrs);
        }

        public ChatMenuItem(Context context) {
            super(context);
            init(context, null);
        }

        private void init(Context context, AttributeSet attrs) {
            LayoutInflater.from(context).inflate(R.layout.ease_chat_menu_item, this);
            imageView = (ImageView) findViewById(R.id.image);
            textView = (TextView) findViewById(R.id.text);
        }

        public void setImage(int resid) {
            imageView.setImageResource(resid);
        }

        public void setText(int resid) {
            textView.setText(resid);
        }

        public void setText(String text) {
            textView.setText(text);
        }
    }
}
