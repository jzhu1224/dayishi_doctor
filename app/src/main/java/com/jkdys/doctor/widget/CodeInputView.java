package com.jkdys.doctor.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.jkdys.doctor.R;
import com.jkdys.doctor.utils.ManyiUtils;

/**
 * Created by yanxin on 17/5/15.
 */

public class CodeInputView extends LinearLayout {

    private int length;
    private int width;
    private int divideWidth;
    private int height;
    private int textSize;
    private int textcolor;
    private int textbg;

    private EditText[] codeTxts;
    private EditText focusView;
    private CodeInputListener listener;
    private IOnFocusChangeListener iOnFocusChangeListener = new IOnFocusChangeListener();
    private ITextWatcher iTextWatcher = new ITextWatcher();
    private IOnKeyListener iOnKeyListener = new IOnKeyListener();

    public CodeInputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CodeInputView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return true;
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CodeItemView);
        length = ta.getInteger(R.styleable.CodeItemView_length, 0);
        width = ta.getDimensionPixelSize(R.styleable.CodeItemView_width, 0);
        height = ta.getDimensionPixelSize(R.styleable.CodeItemView_height, 0);
        textSize = ta.getInt(R.styleable.CodeItemView_textsize, 16);
        textbg = ta.getResourceId(R.styleable.CodeItemView_textbg, R.drawable.codesdk_input_item_bg_selector);
        textcolor = ta.getColor(R.styleable.CodeItemView_textcolor, getResources().getColor(R.color.code_text_color));
        divideWidth = ta.getDimensionPixelSize(R.styleable.CodeItemView_divideWidth, 0);
        ta.recycle();

        codeTxts = new EditText[length];
        for (int i = 0; i < length; i++) {
            EditText editText = new EditText(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            editText.setLayoutParams(params);

            InputFilter[] filters = {new InputFilter.LengthFilter(1)};
            editText.setFilters(filters);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            editText.setCursorVisible(false);
            editText.setGravity(Gravity.CENTER);
            editText.setTextSize(textSize);

            if (textbg > 0) {
                editText.setBackgroundResource(textbg);
            }
            editText.setTextColor(textcolor);
            editText.setClickable(false);

            editText.setOnFocusChangeListener(iOnFocusChangeListener);
            editText.addTextChangedListener(iTextWatcher);
            editText.setOnKeyListener(iOnKeyListener);

            codeTxts[i] = editText;

            if (divideWidth > 0 && i != 0) {
                params.leftMargin = divideWidth;
            }

            addView(editText);
        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (focusView == null) {
                    codeTxts[0].requestFocus();
                }
                ManyiUtils.showKeyBoard(getContext(), focusView);
            }
        });
    }

    public String getText() {
        StringBuilder code = new StringBuilder("");
        for (int i = 0; i < length; i++) {
            code.append(codeTxts[i].getText().toString());
        }
        return code.toString();
    }

    public void setTextBg(int bgId) {
        for (EditText editText : codeTxts) {
            editText.setBackgroundResource(bgId);
        }
        //codeTxts[0].requestFocus();
    }

    public void clear() {
        for (EditText editText : codeTxts) {
            editText.setText("");
        }
        codeTxts[0].requestFocus();
    }

    /**
     * 弹出软键盘
     */
    public void showSoftInput() {
        if (focusView == null) {
            codeTxts[0].requestFocus();
        }
        ManyiUtils.showKeyBoard(getContext(), focusView);
    }

    /**
     * 隐藏
     */
    public void hideSoftInput() {
        if (focusView == null) {
            codeTxts[0].requestFocus();
        }
        ManyiUtils.closeKeyBoard(getContext(), focusView);
    }

    public void setInputStyle(int inputtype) {
        for (EditText editText : codeTxts) {
            editText.setInputType(inputtype);
        }
    }

    public void showPwd() {
        final View mfocusView = focusView;
        for (EditText editText : codeTxts) {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        if (mfocusView != null) {
            mfocusView.requestFocus();
        }
    }

    public void hidePwd() {
        final View mfocusView = focusView;
        for (EditText editText : codeTxts) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        if (mfocusView != null) {
            mfocusView.requestFocus();
        }
    }

    public void setTextColor(@ColorInt int color) {
        for (EditText editText : codeTxts) {
            editText.setTextColor(color);
        }
    }

    public void setCursorVisible(boolean visible) {
        for (EditText editText : codeTxts) {
            editText.setCursorVisible(visible);
        }
    }

    public void setListener(CodeInputListener listener) {
        this.listener = listener;
    }

    /**
     * 选中下一个需要输入的
     * true: 选中 false:全部输入完了
     *
     * @return
     */
    private boolean focusNext() {

        boolean finish = false;
        if (focusView == null) {
            codeTxts[0].requestFocus();
        } else {
            for (int i = 0; i < codeTxts.length; i++) {
                if (focusView == codeTxts[i]) {
                    if (i != codeTxts.length - 1) {
                        focusView.clearFocus();
                        focusView = codeTxts[i + 1];
                        codeTxts[i + 1].requestFocus();
                    } else {
                        finish = true;
                    }
                    break;
                }
            }
        }

        return finish;

//        int first = -1;//第一个还没有输入的
//        int current = -1;//当前输入的
//        int index = -1;//当前输入框之后的下一个需要输入的
//
//        for (int i = 0; i < codeTxts.length; i++) {
//            if (first == -1 && TextUtils.isEmpty(codeTxts[i].getText().toString())) {
//                first = i;
//            }
//
//            if (codeTxts[i] == focusView) {
//                current = i;
//            }
//
//            if (index == -1 && current != -1 && i > current && TextUtils.isEmpty(codeTxts[i].getText().toString())) {
//                index = i;
//            }
//        }
//
//        if (index == -1) {
//            index = first;
//        }
//
//        if (index != -1) {
//            if (focusView != null) {
//                focusView.clearFocus();
//            }
//            codeTxts[index].requestFocus();
//        }

//        return index != -1;
    }

    /**
     * 删除
     *
     * @return
     */

    private void delete() {
        if (focusView != null) {
            for (int i = 1; i < codeTxts.length; i++) {
                if (codeTxts[i] == focusView) {
                    if (TextUtils.isEmpty(codeTxts[i].getText().toString())) {
                        focusView.clearFocus();
                        codeTxts[i - 1].setText("");
                        codeTxts[i - 1].requestFocus();
                    } else {
                        codeTxts[i].setText("");
                    }

                    return;
                }
            }
        }
    }

    public interface CodeInputListener {
        void finish();

        void input();
    }

    public class IOnKeyListener implements OnKeyListener {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    delete();
                }
                return true;
            }
            return false;
        }

    }

    public class IOnFocusChangeListener implements OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                focusView = (EditText) v;
            }
        }
    }

    public class ITextWatcher implements TextWatcher {

        private CharSequence beforCont = "";

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            beforCont = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(s.toString())) {//输入
                boolean finish = focusNext();
                if (finish && listener != null) {
                    listener.finish();
                }
            } else { //删除
                if (TextUtils.isEmpty(beforCont)) { //删除上一个
                    for (int i = 0; i < codeTxts.length; i++) {
                        if (focusView == codeTxts[i] && i != 0) {
                            focusView.clearFocus();
                            focusView = codeTxts[i - 1];
                            focusView.requestFocus();
                        }
                    }
                }
                if (listener != null) {
                    listener.input();
                }
            }
        }
    }

}
