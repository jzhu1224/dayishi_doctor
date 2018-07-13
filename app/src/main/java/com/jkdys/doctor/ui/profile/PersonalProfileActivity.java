package com.jkdys.doctor.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.data.model.Doctor;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.search.SearchData;
import com.jkdys.doctor.ui.search.searchDepartment.SearchDepartmentActivity;
import com.jkdys.doctor.ui.search.searchPhysiciansTitle.SearchPhysiciansTitleActivity;
import com.jkdys.doctor.ui.search.selectArea.SelectAreaActivity;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class PersonalProfileActivity extends MvpActivity<PersonalProfileView, PersonalProfilePresenter> implements PersonalProfileView{

    @Inject
    PersonalProfilePresenter personalProfilePresenter;

    @BindView(R.id.img_avatar)
    ImageView imgAvatar;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @BindView(R.id.tv_hospital)
    TextView tvHospital;

    @BindView(R.id.tv_department)
    TextView tvDepartment;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    private String hospitalId;

    @Inject
    LoginInfoUtil loginInfoUtil;

    @NonNull
    @Override
    public PersonalProfilePresenter createPresenter() {
        getActivityComponent().inject(this);
        return personalProfilePresenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("个人信息");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
    }

    @Override
    protected void afterMvpDelegateCreateInvoked() {
        super.afterMvpDelegateCreateInvoked();

        Doctor doctor = loginInfoUtil.getLoginResponse().getDoctor();

        ImageLoader.with(getApplicationContext()).placeholder(R.drawable.img_doctor).load(doctor.getPicheadurl()).into(imgAvatar);

        tvName.setText(doctor.getName());
        tvPhone.setText(doctor.getCellphoneno());
        tvHospital.setText(doctor.getHospital());
        tvDepartment.setText(doctor.getFaculty());
        tvTitle.setText(doctor.getTitle());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_personal_profile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                case 2:
                case 3:
                    SearchData searchData = (SearchData) data.getExtras().get(SearchPhysiciansTitleActivity.KEY_RETURN_DATA);
                    personalProfilePresenter.modifyDoctorInfo(requestCode, searchData);
                    break;
                case 4:
                    List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    personalProfilePresenter.uploadImage(paths.get(0));
                    break;
            }
        }
    }

    @OnClick(R.id.fr_avatar)
    void onAvatarClick() {
        multiImageSelector(4);
    }

    @OnClick(R.id.fr_hospital)
    void onHospitalClick() {
//        //选择医院
//        Intent intent = new Intent(mActivity, SelectAreaActivity.class);
//        startActivityForResult(intent,3);
    }

    @OnClick(R.id.fr_department)
    void onDepartmentClick() {

//        if (TextUtils.isEmpty(tvHospital.getText().toString())) {
//            ToastUtil.show(mActivity, "请先选择医院");
//            return;
//        }
//
//        //选择科室
//        Intent intent = new Intent(mActivity, SearchDepartmentActivity.class);
//        intent.putExtra(SearchDepartmentActivity.KEY_HOSPITAL_NAME,tvHospital.getText().toString());
//        intent.putExtra(SearchDepartmentActivity.KEY_HOSPITAL_ID,hospitalId);
//        startActivityForResult(intent,1);
    }

    @OnClick(R.id.fr_title)
    void onTitleClick() {
//        //选择职称
//        Intent intent = new Intent(mActivity, SearchPhysiciansTitleActivity.class);
//        startActivityForResult(intent,2);
    }

    @OnClick(R.id.fr_good_at)
    void onGoodAtClick() {

    }

    @OnClick(R.id.fr_personal_introduction)
    void onPersonalIntroductionClick() {
        Intent intent = new Intent(mActivity, EditPersonalIntroduceActivity.class);
        startActivity(intent);
    }

    @Override
    public void onModifySuccess(int requestCode, SearchData searchData) {
        if (requestCode == 1) {
            tvDepartment.setText(searchData.getText());
            loginInfoUtil.saveFaculty(searchData.getText());
        } else if (requestCode == 2) {
            tvTitle.setText(searchData.getText());
            loginInfoUtil.saveTitle(searchData.getText());
        } else if (requestCode == 3) {
            loginInfoUtil.saveHospital(searchData.getText());
            tvHospital.setText(searchData.getText());
            hospitalId = searchData.getId();
        }
    }

    @Override
    public void onModifyAvatarSuccess(String url) {
        ImageLoader.with(getApplicationContext())
                .placeholder(R.drawable.img_doctor)
                .load(url)
                .into(imgAvatar);
    }

    /**
     * 图片选择器调用
     */
    public void multiImageSelector(int requestCode) {
        Intent intent = new Intent(mActivity, MultiImageSelectorActivity.class);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        startActivityForResult(intent, requestCode);
    }
}
