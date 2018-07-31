package com.chairoad.framework.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by zhujiang on 2018/3/15.
 */

public class SystemUtil {

    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
    }

    //隐藏虚拟键盘
    public static void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    public static void hideSoftInput(Activity activity) {
        if (activity.getCurrentFocus() == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService
                (Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示或隐藏软键盘， 若软键盘处于显示状态，则执行隐藏； 若软键盘处于隐藏状态,则执行显示
     */
    public static void showOrHideSoftInput(Context activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 调用系统拨号功能，跳到拨号界面
     *
     * @param context
     * @param phoneNumber 电话号码
     */
    public static void callPhone(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 调用拨号功能
     * @param phone 电话号码
     */
    @SuppressLint("MissingPermission")
    public static void call(Activity context, String phone) {
        Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));
        context.startActivity(intent);
    }


    public static String getMobileFormat(String mobile) {
        if(TextUtils.isEmpty(mobile)) {
            return "";
        }

        if(mobile.length() < 11) {
            return mobile;
        }

        return mobile.substring(0,3)+" **** " + mobile.substring(mobile.length()-4);
    }

    public static void copy(Context context,String cont) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null,cont));
    }

}
