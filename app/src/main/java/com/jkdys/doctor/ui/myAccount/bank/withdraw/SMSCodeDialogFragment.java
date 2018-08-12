package com.jkdys.doctor.ui.myAccount.bank.withdraw;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.jkdys.doctor.R;
import com.jkdys.doctor.event.RequestSmsCodeEvent;
import com.jkdys.doctor.event.WithdrawEvent;
import com.jkdys.doctor.utils.ManyiUtils;
import com.jkdys.doctor.widget.CountdownView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SMSCodeDialogFragment extends DialogFragment {

    @BindView(R.id.edt_sms_code)
    EditText edtSMSCode;

    @BindView(R.id.countdownView)
    CountdownView countdownView;

    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_sms_code, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        btnConfirm.setEnabled(false);
        countdownView.start();
        countdownView.setOnClickListener(view -> EventBus.getDefault().post(new RequestSmsCodeEvent()));
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable( new ColorDrawable(getResources().getColor(R.color.color_transparent)));
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width =  ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);
        edtSMSCode.requestFocus();
        ManyiUtils.showKeyBoard(getContext(), edtSMSCode);
        getDialog().setCanceledOnTouchOutside(false);
    }

    @Override
    public void onDestroyView() {
        ManyiUtils.closeKeyBoard(getContext(), edtSMSCode);
        super.onDestroyView();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        ManyiUtils.closeKeyBoard(getContext(), edtSMSCode);
    }

    @OnClick(R.id.tv_cancel)
    void onCancelClick() {
        ManyiUtils.closeKeyBoard(getContext(), edtSMSCode);
        dismissAllowingStateLoss();
    }

    @OnClick(R.id.btn_confirm)
    void onBtnConfirmClick() {
        EventBus.getDefault().post(new WithdrawEvent(edtSMSCode.getText().toString()));
        ManyiUtils.closeKeyBoard(getContext(), edtSMSCode);
        dismissAllowingStateLoss();
    }

    @OnTextChanged(value = R.id.edt_sms_code, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onSMSCodeChanged(Editable editable) {
        btnConfirm.setEnabled(editable.toString().length() > 0);
    }
}
