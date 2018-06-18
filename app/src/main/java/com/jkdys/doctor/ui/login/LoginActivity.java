package com.jkdys.doctor.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.utils.AddSpaceTextWatcher;
import com.jkdys.doctor.utils.ManyiUtils;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView{

    @Inject
    LoginPresenter loginPresenter;

    @BindView(R.id.edt_phone)
    EditText edtPhone;

    @BindView(R.id.btn_next_step)
    Button btnNextStep;

    @BindView(R.id.img_close)
    ImageView imgClose;

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        getActivityComponent().inject(this);
        return loginPresenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());

        AddSpaceTextWatcher addSpaceTextWatcher = new AddSpaceTextWatcher(edtPhone,13);
        addSpaceTextWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);

        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString().trim())) {
                    btnNextStep.setEnabled(false);
                    imgClose.setVisibility(View.GONE);
                } else {
                    btnNextStep.setEnabled(true);
                    imgClose.setVisibility(View.VISIBLE);
                }
                if (editable.toString().length() == 13) {
                    ManyiUtils.closeKeyBoard(mActivity, edtPhone);
                }
            }
        });
        ManyiUtils.showKeyBoard(mActivity,edtPhone);
    }
    @OnClick(R.id.btn_next_step)
    void onNextBtnClicked() {

        loginPresenter.login(edtPhone.getText().toString().replace(" ",""));

        //Toast.makeText(LoginActivity.this,"click",Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(this,LoginSmsActivity.class);
//        intent.putExtra(LoginSmsActivity.PARAM_MOBILE,edtPhone.getText().toString());
//        startActivity(intent);
    }

    @OnClick(R.id.img_close)
    void onCloseClicked() {
        edtPhone.setText("");
    }

    @Override
    public void sendVerifyCodeSuccess() {
        Intent intent = new Intent(this,LoginSmsActivity.class);
        intent.putExtra(LoginSmsActivity.PARAM_MOBILE,edtPhone.getText().toString().replace(" ",""));
        startActivity(intent);
        finish();
    }
}
