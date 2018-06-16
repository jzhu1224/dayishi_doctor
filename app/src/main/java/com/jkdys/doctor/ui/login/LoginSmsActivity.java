package com.jkdys.doctor.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.TextView;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.utils.StringUtils;
import com.jkdys.doctor.widget.CodeInputView;
import com.jkdys.doctor.widget.CountdownView;
import com.jkdys.doctor.widget.CountdownView1;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by yanxin on 17/10/25.
 */
public class LoginSmsActivity extends MvpActivity<LoginView,LoginPresenter> implements LoginView {
    public static final String PARAM_MOBILE = "PARAM_MOBILE";

    @BindView(R.id.tipTxt)
    public TextView tipTxt;
    @BindView(R.id.codeInputView)
    public CodeInputView codeInputView;
    @BindView(R.id.requestSmsBtn)
    public CountdownView1 requestSmsBtn;

    private String mobile;

    @Inject
    LoginPresenter loginPresenter;

    @Override
    public void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
        mobile = getIntent().getStringExtra(PARAM_MOBILE);
        if(TextUtils.isEmpty(mobile)) {
            finish();
            return;
        }

        tipTxt.setText("验证码已发送至 "+ StringUtils.formatPhoneWithSpace(mobile));

        codeInputView.setListener(new CodeInputView.CodeInputListener() {
            @Override
            public void finish() {
                codeInputView.hideSoftInput();
                //mPresenter.login(mobile,codeInputView.getText());
            }

            @Override
            public void input() {

            }
        });
        codeInputView.setInputStyle(InputType.TYPE_CLASS_NUMBER);
        codeInputView.showSoftInput();

        requestSmsBtn.setCountDownListener(new CountdownView.CountDownListener() {
            @Override
            public boolean before() {
                return true;
            }

            @Override
            public void start() {
                //mPresenter.requestLoginSms(mobile);
            }

            @Override
            public void finish() {

            }
        });
        requestSmsBtn.start();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login_sms;
    }

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showError(String message) {

    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        getActivityComponent().inject(this);
        return loginPresenter;
    }
}