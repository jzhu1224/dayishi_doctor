package com.jkdys.doctor.ui.verify.userVerify;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.widget.EditText;
import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.verify.personalInfo.PersonalInfoActivity;
import com.jkdys.doctor.utils.ManyiUtils;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;

public class IdentityActivity extends MvpActivity<IdentityView,IdentityPresenter> implements IdentityView  {

    @Inject
    IdentityPresenter identityPresenter;

    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_id_number)
    EditText edtIDNumber;

    @Inject
    LoginInfoUtil loginInfoUtil;

    @NonNull
    @Override
    public IdentityPresenter createPresenter() {
        return identityPresenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_verify_user;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        getActivityComponent().inject(this);

        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
        toolbar.setTitle("实名认证");

        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                if (!ManyiUtils.isChinese(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        };

        edtName.setFilters(new InputFilter[]{filter});
    }

    @OnClick(R.id.btn_next)
    public void onNextStepClick() {
        identityPresenter.verifyUser(edtName.getText().toString(), edtIDNumber.getText().toString());
    }

    @Override
    public void onIdentitySuccess() {
        loginInfoUtil.updateRedirecttopage(2);
        Intent intent = new Intent(mActivity, PersonalInfoActivity.class);
        startActivity(intent);
    }
}
