package com.jkdys.doctor.ui.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import java.util.Locale;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class EditPersonalIntroduceActivity extends MvpActivity<EditProfileView, EditProfilePresenter> implements EditProfileView {

    @Inject
    EditProfilePresenter presenter;

    @BindView(R.id.edt_content)
    EditText edtContent;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_text_length)
    TextView tvLength;

    @NonNull
    @Override
    public EditProfilePresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }

    @Override
    protected void afterMvpDelegateCreateInvoked() {
        super.afterMvpDelegateCreateInvoked();
        presenter.getProfile(2);
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("个人简介");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_personal_introduce;
    }

    @OnTextChanged(value = R.id.edt_content,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable editable) {
        tvLength.setText(String.format(Locale.CHINESE, "%d/200", editable.toString().length()));
        setSubmitBtnState();
    }

    /**
     * 按钮是否可操作
     */
    public void setSubmitBtnState() {
        if (TextUtils.isEmpty(edtContent.getText())) {
            btnSubmit.setEnabled(false);
        } else {
            btnSubmit.setEnabled(true);
        }
    }

    @OnClick(R.id.btn_submit)
    public void feedBackSubmit() {
        presenter.updateProfile(2,edtContent.getText().toString());
    }

    @Override
    public void onUpdateSuccess() {
        finish();
    }

    @Override
    public void onRequestProfileSuccess(String content) {
        if (!TextUtils.isEmpty(content)) {
            edtContent.setText(content);
            edtContent.setSelection(content.length());
        }
    }
}
