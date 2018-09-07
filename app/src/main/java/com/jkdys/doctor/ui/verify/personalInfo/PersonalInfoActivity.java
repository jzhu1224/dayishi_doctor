package com.jkdys.doctor.ui.verify.personalInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.DoctorWorkInfo;
import com.jkdys.doctor.data.model.LoginResponse;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.main.MainActivity;
import com.jkdys.doctor.ui.search.SearchData;
import com.jkdys.doctor.ui.search.searchDepartment.SearchDepartmentActivity;
import com.jkdys.doctor.ui.search.searchPhysiciansTitle.SearchPhysiciansTitleActivity;
import com.jkdys.doctor.ui.search.selectArea.SelectAreaActivity;
import com.jkdys.doctor.ui.verify.JumpHelper;
import com.jkdys.doctor.ui.verify.userVerify.IdentityActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class PersonalInfoActivity extends MvpActivity<PersonalInfoView,PersonalInfoPresenter> implements PersonalInfoView {

    @Inject
    PersonalInfoPresenter personalInfoPresenter;
    @Inject
    JumpHelper jumpHelper;

    @BindView(R.id.tv_hospital)
    TextView tvHospital;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_invent_code)
    EditText edtInventCode;

    DoctorWorkInfo doctorWorkInfo = new DoctorWorkInfo();

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
        startActivityForResult(intent,3);
    }

    @OnClick(R.id.item2)
    void onDepartmentClick() {

        if (TextUtils.isEmpty(tvHospital.getText().toString())) {
            ToastUtil.show(mActivity, "请先选择医院");
            return;
        }

        //选择科室
        Intent intent = new Intent(mActivity, SearchDepartmentActivity.class);
        intent.putExtra(SearchDepartmentActivity.KEY_HOSPITAL_NAME,tvHospital.getText().toString());
        intent.putExtra(SearchDepartmentActivity.KEY_HOSPITAL_ID,doctorWorkInfo.getHospitalcode());
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
                doctorWorkInfo.setFacultycode(searchData.getId());
            } else if (requestCode == 2) {
                tvTitle.setText(searchData.getText());
                doctorWorkInfo.setTitlecode(searchData.getId());
            } else if (requestCode == 3) {
                tvHospital.setText(searchData.getText());
                doctorWorkInfo.setHospitalcode(searchData.getId());
            }
        }


    }

    @OnClick(R.id.btn_next)
    public void onNextStepClick() {
        personalInfoPresenter.updateWorkInfo(doctorWorkInfo);
    }

    @OnTextChanged(R.id.edt_invent_code)
    public void onEdtInventCodeChanged() {
        if (!TextUtils.isEmpty(edtInventCode.getText().toString())) {
            doctorWorkInfo.setInvitationcode(edtInventCode.getText().toString());
        }
    }

    @Override
    public void onPersonalInfoUpdateSuccess(LoginResponse response) {
        int redirect = response.getDoctorauthstatus().getRedirecttopage();
        jumpHelper.jump(mActivity,redirect);
    }
}
