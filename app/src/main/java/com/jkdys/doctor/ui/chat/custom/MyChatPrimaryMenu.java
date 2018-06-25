package com.jkdys.doctor.ui.chat.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.chairoad.framework.util.SystemUtil;
import com.hyphenate.easeui.widget.EaseChatPrimaryMenu;
import com.jkdys.doctor.R;

/**
 * Created by zhujiang on 2017/10/26.
 */

public class MyChatPrimaryMenu extends EaseChatPrimaryMenu {
    private ImageView faceNormal;
    public MyChatPrimaryMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyChatPrimaryMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyChatPrimaryMenu(Context context) {
        super(context);
    }

    boolean isFaceClick = false;

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.rl_face) {
            isFaceClick = true;
            setModeKeyboard();
        } else {
            isFaceClick = false;
        }
    }

    @Override
    protected void setModeKeyboard() {
        super.setModeKeyboard();

        if (isFaceClick)
            return;

        postDelayed(() -> SystemUtil.showOrHideSoftInput(getContext()),200);
    }

    @Override
    protected void toggleFaceImage(){
        super.toggleFaceImage();

        if (faceNormal == null)
            faceNormal = (ImageView) findViewById(R.id.iv_face_normal);

        if(faceNormal.getVisibility() == View.VISIBLE){
            postDelayed(() -> SystemUtil.showOrHideSoftInput(getContext()),200);
        }
    }
}
