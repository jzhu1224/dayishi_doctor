package com.jkdys.doctor.ui.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.db.ChatDBManager;
import com.jkdys.doctor.data.model.Doctor;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;
import com.jkdys.doctor.ui.MvpActivity;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import javax.inject.Inject;

import butterknife.BindView;

public class SettingActivity extends MvpActivity<SettingView,SettingPresenter> implements SettingView {

    @Inject
    SettingPresenter presenter;

    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;
    QMUICommonListItemView itemWithSwitch;

    @Inject
    LoginInfoUtil loginInfoUtil;

    @NonNull
    @Override
    public SettingPresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);

        toolbar.setTitle("设置");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());

        itemWithSwitch = mGroupListView.createItemView("是否开启短信通知");
        itemWithSwitch.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);

        QMUIGroupListView.newSection(mActivity)
                .addItemView(itemWithSwitch, null)
                .setSeparatorDrawableRes(0,R.drawable.qmui_s_list_item_bg_with_border_bottom,R.drawable.qmui_s_list_item_bg_with_border_none,R.drawable.qmui_s_list_item_bg_with_border_bottom)
                .addTo(mGroupListView);

        itemWithSwitch.setOnClickListener(view -> {
            presenter.switchNotification(!loginInfoUtil.getDoctor().isSmsnotice());
        });
    }

    @Override
    protected void afterMvpDelegateCreateInvoked() {
        super.afterMvpDelegateCreateInvoked();
        itemWithSwitch.getSwitch().setChecked(loginInfoUtil.getDoctor().isSmsnotice());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_group_list;
    }

    @Override
    public void onSettingSuccess(boolean on) {
        itemWithSwitch.getSwitch().setChecked(on);
        Doctor doctor = loginInfoUtil.getDoctor();
        doctor.setSmsnotice(on);
        loginInfoUtil.saveDoctor(doctor);
    }
}
