package com.jkdys.doctor.ui.verify.personalInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.search.SearchData;
import com.jkdys.doctor.ui.search.SearchDepartmentActivity;
import com.jkdys.doctor.ui.search.SearchDepartmentPresenter;
import com.jkdys.doctor.ui.search.SearchPhysiciansTitleActivity;
import com.jkdys.doctor.ui.search.selectArea.SelectAreaActivity;

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
        Intent intent = new Intent(mActivity, SelectAreaActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.item2)
    void onDepartmentClick() {
        //选择科室
        Intent intent = new Intent(mActivity, SearchDepartmentActivity.class);
        intent.putExtra(SearchDepartmentActivity.KEY_HOSPITAL_NAME,"上海市第九人民医院");
        intent.putExtra(SearchDepartmentActivity.KEY_HOSPITAL_ID,"C88615E8-1BAF-455F-BD67-4085AAD39055");
        startActivityForResult(intent,1);
    }

    @OnClick(R.id.item3)
    void onTitleClick() {
        //选择职称
        Intent intent = new Intent(mActivity, SearchPhysiciansTitleActivity.class);
        startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            SearchData searchData = (SearchData) data.getExtras().get(SearchPhysiciansTitleActivity.KEY_RETURN_DATA);
            if (requestCode == 1) {
                tvDepartment.setText(searchData.getText());
            } else if (requestCode == 2) {
                tvTitle.setText(searchData.getText());
            }
        }


    }

    @OnClick(R.id.btn_next)
    public void onNextStepClick() {

    }

    @Override
    public void onPersonalInfoUpdateSuccess() {

    }
}
