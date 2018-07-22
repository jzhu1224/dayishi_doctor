package com.jkdys.doctor.ui.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.widget.Button;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.widget.DoctorGoodAtLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import javax.inject.Inject;
import butterknife.BindView;

public class EditGoodAtTagActivity extends MvpActivity<EditProfileView, EditProfilePresenter> implements EditProfileView {

    @Inject
    EditProfilePresenter presenter;

    @BindView(R.id.dg_layout)
    DoctorGoodAtLayout doctorGoodAtLayout;

    Button rightBtn;


    @NonNull
    @Override
    public EditProfilePresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("擅长疾病");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());

        rightBtn = toolbar.addRightTextButton("编辑",R.id.id_right_btn);
        rightBtn.setTextColor(getResources().getColor(R.color.color_6196FF));
        rightBtn.setOnClickListener(view -> {
            rightBtn.setText("保存");
            rightBtn.setOnClickListener(view1 -> presenter.updateProfile(1, doctorGoodAtLayout.getData()));
            doctorGoodAtLayout.editMode(true);
        });

        doctorGoodAtLayout.setAddTagClickListener(view -> {
            QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(mActivity);
            builder.setPlaceholder("请输入擅长疾病").setInputType(InputType.TYPE_CLASS_TEXT)
                    .addAction("取消", (dialog, index) -> dialog.dismiss())
                    .addAction("确定", (dialog, index) -> {
                        dialog.dismiss();
                        String tag = builder.getEditText().getText().toString();
                        doctorGoodAtLayout.addTag(tag);
                    }).show();
        });
    }

    @Override
    protected void afterMvpDelegateCreateInvoked() {
        super.afterMvpDelegateCreateInvoked();
        presenter.getProfile(1);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_edit_good_at_tag;
    }

    @Override
    public void onUpdateSuccess() {
        doctorGoodAtLayout.editMode(false);
        rightBtn.setText("编辑");
        rightBtn.setOnClickListener(view -> {
            rightBtn.setText("保存");
            rightBtn.setOnClickListener(view1 -> presenter.updateProfile(1, doctorGoodAtLayout.getData()));
            doctorGoodAtLayout.editMode(true);
        });
    }

    @Override
    public void onRequestProfileSuccess(String content) {
        doctorGoodAtLayout.setData(content);
    }
}
