package com.jkdys.doctor.ui.chat.doctor;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chairoad.framework.util.ToastUtil;
import com.hyphenate.easeui.domain.EaseUser;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.data.db.ChatDBManager;
import com.jkdys.doctor.data.model.DoctorDetailData;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.chat.ChatActivity;
import com.jkdys.doctor.widget.DoctorGoodAtLayout;
import com.jkdys.doctor.widget.DoctorGoodAtTag;
import com.nex3z.flowlayout.FlowLayout;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class DoctorDetailActivity extends MvpActivity<DoctorDetailView, DoctorDetailPresenter> implements DoctorDetailView {

    @Inject
    DoctorDetailPresenter doctorDetailPresenter;
    @BindView(R.id.my_toolbar)
    FrameLayout myToolbar;

    @BindView(R.id.profile_image)
    ImageView imgHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_doctor_info)
    TextView tvDoctorInfo;

    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.btn_add)
    Button btnAdd;

    @BindView(R.id.dg_layout)
    DoctorGoodAtLayout doctorGoodAtLayout;


    String doctorId, hxId;
    private DoctorDetailData doctorDetailData;

    @NonNull
    @Override
    public DoctorDetailPresenter createPresenter() {
        getActivityComponent().inject(this);
        return doctorDetailPresenter;
    }

    @Override
    protected void onStart() {
        super.onStart();
        doctorId = getIntent().getStringExtra("doctorId");
        hxId = getIntent().getStringExtra("hxId");

        if (!TextUtils.isEmpty(doctorId)) {
            doctorDetailPresenter.getDoctorDetail(doctorId);
        } else if (!TextUtils.isEmpty(hxId)) {
            doctorDetailPresenter.getDoctorDetailByHxId(hxId);
        } else {
            ToastUtil.show(mActivity, "参数错误");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        doctorId = intent.getStringExtra("doctorId");
        hxId = intent.getStringExtra("hxId");

        if (!TextUtils.isEmpty(doctorId)) {
            doctorDetailPresenter.getDoctorDetail(doctorId);
        } else if (!TextUtils.isEmpty(hxId)){
            doctorDetailPresenter.getDoctorDetailByHxId(hxId);
        } else {
            ToastUtil.show(mActivity, "参数错误");
        }
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 14) {
            myToolbar.setPadding(0, QMUIStatusBarHelper.getStatusbarHeight(mActivity),0,0);
        }
        myToolbar.setBackgroundColor(getResources().getColor(R.color.color_transparent));
        toolbar.setBackgroundAlpha(0);
        toolbar.addLeftImageButton(R.drawable.ic_back_white,R.id.qmui_topbar_item_left_back).setOnClickListener(view -> finish());
    }

    @Override
    protected void initStatusBar(int color) {
        super.initStatusBar(color);
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_doctor_detail;
    }

    @Override
    public void onRequestSuccess(DoctorDetailData doctorDetailData) {
        this.doctorDetailData = doctorDetailData;
        tvName.setText(doctorDetailData.getDoctorname());
        tvDoctorInfo.setText(doctorDetailData.getFacultyname()+" "+doctorDetailData.getTitlename());
        tvDesc.setText(doctorDetailData.getPersonalprofile());
        ImageLoader.with(mActivity).load(doctorDetailData.getDoctorpicheadurl()).placeholder(R.drawable.img_doctor).into(imgHeader);
        btnAdd.setText(doctorDetailData.isIsfriend()? "发送消息":"加为好友");
        String sGoodAt = doctorDetailData.getGoodat();
        doctorGoodAtLayout.setData(sGoodAt);
    }

    @Override
    public void onAddFriendSuccess() {
        btnAdd.setText("发送消息");
        EaseUser easeUser = new EaseUser(doctorDetailData.getHxid());
        easeUser.setAvatar(doctorDetailData.getDoctorpicheadurl());
        easeUser.setNickname(doctorDetailData.getDoctorname());
        new Thread(() -> ChatDBManager.getInstance().saveContact(easeUser)).start();
    }

    @OnClick(R.id.btn_add)
    void onBtnAddClick() {
        if (doctorDetailData != null &&
                doctorDetailData.isIsfriend()) {
            Intent intent = new Intent(mActivity, ChatActivity.class);
            intent.putExtra(ChatActivity.PARAM_USERID,doctorDetailData.getHxid());
            startActivity(intent);
        } else {
            assert doctorDetailData != null;
            presenter.addFriend(doctorDetailData.getDoctorid());
        }
    }
}
