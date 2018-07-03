package com.jkdys.doctor.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;

/**
 * Created by yanxin on 17/10/26.
 */

public class PhoneEditTextView extends AppCompatEditText {

    public PhoneEditTextView(Context context) {
        super(context);
        phoneNumAddSpace();
    }

    public PhoneEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        phoneNumAddSpace();
    }

    public PhoneEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        phoneNumAddSpace();
    }

    private InputFilter[] phoneFilters = new InputFilter[]{new InputFilter.LengthFilter(13)};

    private InputFilter[] telFilters = new InputFilter[]{new InputFilter.LengthFilter(100)};

    public String getTextString() {
        String text = getText().toString();
        text = text.replace(" ", "");
        return text;
    }

    /**
     * 手机号码格式设置
     * 如: 138 888888 88
     * <p>
     * 电话号码格式设置
     * 如: 0218 121334646446
     */
    private void phoneNumAddSpace() {
        setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        setFilters(phoneFilters);
        addTextChangedListener(new TextWatcher() {

            private StringBuilder mBefore = new StringBuilder("");
            private StringBuilder mBuilder = new StringBuilder("");

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mBefore.delete(0, mBefore.length());
                mBefore.append(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if(s != null && s.length() == 1) {
//                    setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
//                    if(s.toString().startsWith("1")) setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
//                    else setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
//                }

                if (s == null || s.length() <= 3) return;

                if (!TextUtils.isEmpty(mBefore.toString()) && s.length() <= mBefore.length())
                    return;//删除不用处理

                mBuilder.delete(0, mBuilder.length());

                mBuilder.append(s.toString());

                //if(s.toString().startsWith("1")) {

                int selectionIndex = getSelectionStart();
                boolean isLastSelection = (getSelectionStart() == s.length());
                int firstBlankIndex = s.toString().indexOf(" ");
                int lastBlankIndex = s.toString().lastIndexOf(" ");

                if (firstBlankIndex == lastBlankIndex) { //只有一个空格
                    if (mBuilder.length() >= 4 && mBuilder.charAt(3) != ' ') {
                        mBuilder.insert(3, " ");
                    }
                    if (mBuilder.length() >= 9 && mBuilder.charAt(8) != ' ') {
                        mBuilder.insert(8, " ");
                    }
                }

                if (mBuilder.length() != s.length()) {
                    setText(mBuilder.toString());
                    if(isLastSelection) {
                        setSelection(length());
                    } else {
                        setSelection(selectionIndex+1);
                    }
                }

//                } else {
//                    if(mBuilder.length() >= 5 && mBuilder.charAt(4) != ' ') {
//                        mBuilder.insert(4, " ");
//                        setText(mBuilder.toString());
//                        setSelection(length());
//                    }
//                }

            }

        });
    }
}
