package com.jkdys.doctor.ui.profile.changeMobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.widget.CountdownView;
import com.jkdys.doctor.widget.PhoneEditTextView;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnTextChanged;

public class ChangeMobileActivity extends MvpActivity<ChangeMobileView, ChangeMobilePresenter> implements ChangeMobileView{
    @BindView(R.id.clearBtn)
    public View clearBtn;
    @BindView(R.id.phoneTxt)
    public PhoneEditTextView phoneTxt;
    @BindView(R.id.commitBtn)
    public Button commitBtn;
    @BindView(R.id.codeTxt)
    EditText codeTxt;
    @BindView(R.id.codeBtn)
    CountdownView countDownBtn;

    @Inject
    ChangeMobilePresenter presenter;

    @NonNull
    @Override
    public ChangeMobilePresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("变更电话");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
        countDownBtn.setEnabled(false);

        //获取验证码
        countDownBtn.setCountDownListener(new CountdownView.CountDownListener() {
            @Override
            public boolean before() {
                if (TextUtils.isEmpty(phoneTxt.getTextString()) || phoneTxt.getTextString().length() < 11 || !phoneTxt.getTextString().startsWith("1")) {
                    ToastUtil.show(mActivity,"手机号不合法");
                    phoneTxt.requestFocus();
                    return false;
                }
                codeTxt.requestFocus();
                return true;
            }

            @Override
            public void start() {
                //请求验证码
            }

            @Override
            public void finish() {

            }
        });

    }

    @OnTextChanged(value = R.id.phoneTxt, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterMoibleTextChanged(Editable s) {
        if (TextUtils.isEmpty(phoneTxt.getText().toString())) {
            clearBtn.setVisibility(View.GONE);
            commitBtn.setEnabled(false);
            countDownBtn.setEnabled(false);
        } else {
            clearBtn.setVisibility(View.VISIBLE);
            if (phoneTxt.getTextString().length() == 11 && phoneTxt.getTextString().startsWith("1")) {
                countDownBtn.setEnabled(true);
                if (!TextUtils.isEmpty(codeTxt.getText())) {
                    commitBtn.setEnabled(true);
                } else {
                    commitBtn.setEnabled(false);
                }
            } else {
                countDownBtn.setEnabled(false);
                commitBtn.setEnabled(false);
            }
        }
    }

    @OnTextChanged(value = R.id.codeTxt, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterCodeTextChanged(Editable s) {
        if (TextUtils.isEmpty(phoneTxt.getText().toString())) {
            commitBtn.setEnabled(false);
        } else {
            if (!TextUtils.isEmpty(codeTxt.getText())) {
                if (phoneTxt.getTextString().length() == 11 && phoneTxt.getTextString().startsWith("1")) {
                    commitBtn.setEnabled(true);
                } else {
                    commitBtn.setEnabled(false);
                }
            } else {
                commitBtn.setEnabled(false);
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_change_mobile;
    }

    @Override
    public void onChangeMobileSuccess() {
        finish();
    }
}
