package com.jkdys.doctor.ui.verify.personalInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.search.SearchData;
import com.jkdys.doctor.ui.search.SearchDepartmentActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonalInfoActivity extends MvpActivity<PersonalInfoView,PersonalInfoPresenter> implements PersonalInfoView {

    @Inject
    PersonalInfoPresenter personalInfoPresenter;

    @BindView(R.id.tv_hospital)
    TextView tvHospital;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_invent_code)
    EditText edtInventCode;

    @NonNull
    @Override
    public PersonalInfoPresenter createPresenter() {
        getActivityComponent().inject(this);
        return personalInfoPresenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_personal_info_user;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);

        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
        toolbar.setTitle("个人信息录入");

    }

    @OnClick(R.id.item1)
    void onHospitalClick() {
        //选择医院
    }

    @OnClick(R.id.item2)
    void onDepartmentClick() {
        //选择科室
    }

    @OnClick(R.id.item3)
    void onTitleClick() {
        //选择职称
        Intent intent = new Intent(mActivity, SearchDepartmentActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            SearchData searchData = (SearchData) data.getExtras().get(SearchDepartmentActivity.KEY_RETURN_DATA);
            tvTitle.setText(searchData.getText());
        }
    }

    @OnClick(R.id.btn_next)
    public void onNextStepClick() {

    }

    @Override
    public void onPersonalInfoUpdateSuccess() {

    }
}
