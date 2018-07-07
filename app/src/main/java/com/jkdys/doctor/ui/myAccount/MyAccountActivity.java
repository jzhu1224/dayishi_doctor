package com.jkdys.doctor.ui.myAccount;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.AccountData;
import com.jkdys.doctor.data.model.Doctor;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.myAccount.bank.BankCardListActivity;
import com.jkdys.doctor.ui.myAccount.bank.withdraw.WithdrawActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MyAccountActivity extends MvpActivity<MyAccountView,MyAccountPresenter> implements MyAccountView {

    @Inject
    MyAccountPresenter myAccountPresenter;

    @BindView(R.id.my_toolbar)
    FrameLayout myToolbar;

    @BindView(R.id.groupListView)
    QMUIGroupListView qmuiGroupListView;

    @BindView(R.id.tv_total_income)
    TextView tvTotalIncome;

    @BindView(R.id.tv_allow_withdraw)
    TextView tvAllWithdraw;


    QMUICommonListItemView phoneFee,doorFee,tuiguangFee,bindCard;

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 14) {
            myToolbar.setPadding(0, QMUIStatusBarHelper.getStatusbarHeight(mActivity),0,0);
        }
        myToolbar.setBackgroundColor(getResources().getColor(R.color.color_transparent));
        toolbar.setBackgroundAlpha(0);
        toolbar.addLeftImageButton(R.drawable.ic_back_white,R.id.qmui_topbar_item_left_back).setOnClickListener(view -> finish());
        toolbar.setTitle("我的账户");


        phoneFee = createItemView(0,"电话诊断收入","0.00",0);
        doorFee = createItemView(0,"门诊预约收费","0.00",0);
        tuiguangFee = createItemView(0, "推广补贴", "0.00", 0);


        QMUIGroupListView.newSection(mActivity)
                .addItemView(phoneFee, view -> {})
                .addItemView(doorFee, view -> {})
                .addItemView(tuiguangFee, view -> {})
                .setSeparatorDrawableRes(0,R.drawable.qmui_s_list_item_bg_with_border_bottom,R.drawable.qmui_s_list_item_bg_with_border_none,R.drawable.qmui_s_list_item_bg_with_border_bottom)
                .addTo(qmuiGroupListView);

        bindCard = createItemView(R.drawable.ic_bank, "绑定银行卡", "未绑定", QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);


        QMUIGroupListView.newSection(mActivity) //绑定银行卡
                .addItemView(bindCard, view -> {
                    startActivity(new Intent(mActivity, BankCardListActivity.class));
                })
                .setSeparatorDrawableRes(0,R.drawable.qmui_s_list_item_bg_with_border_bottom,R.drawable.qmui_s_list_item_bg_with_border_none,R.drawable.qmui_s_list_item_bg_with_border_bottom)
                .addTo(qmuiGroupListView);

        QMUICommonListItemView tradeRecord = createItemView(R.drawable.ic_trade_record, "交易记录", "", QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUIGroupListView.newSection(mActivity) //交易记录
                .addItemView(tradeRecord, view -> {})
                .setSeparatorDrawableRes(0,R.drawable.qmui_s_list_item_bg_with_border_bottom,R.drawable.qmui_s_list_item_bg_with_border_none,R.drawable.qmui_s_list_item_bg_with_border_bottom)
                .addTo(qmuiGroupListView);
    }

    @Override
    protected void initStatusBar(int color) {
        super.initStatusBar(color);
        QMUIStatusBarHelper.setStatusBarDarkMode(mActivity);
    }

    @Override
    protected void afterMvpDelegateCreateInvoked() {
        super.afterMvpDelegateCreateInvoked();
        myAccountPresenter.getDoctorInfo();
    }

    @NonNull
    @Override
    public MyAccountPresenter createPresenter() {
        getActivityComponent().inject(this);
        return myAccountPresenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_my_account;
    }

    private QMUICommonListItemView createItemView(int drawableId, CharSequence title, String detailText, int accessory) {
        return qmuiGroupListView.createItemView(drawableId == 0?null:getResources().getDrawable(drawableId),
                title,
                detailText,
                QMUICommonListItemView.HORIZONTAL,
                accessory);
    }

    @Override
    public void onRequestSuccess(AccountData accountData) {
        if (accountData == null)
            return;
        tvTotalIncome.setText(accountData.getTelfee()+"");
        tvAllWithdraw.setText(accountData.getUndrawnamount()+"");
        phoneFee.setDetailText(accountData.getTelfee()+"");
        doorFee.setDetailText(accountData.getOutpatientfee()+"");
        tuiguangFee.setDetailText(accountData.getPromotefee()+"");
        bindCard.setDetailText(accountData.bindornot?"已绑定":"未绑定");
    }

    @OnClick(R.id.btn_withdraw)
    void onWithdrawClicked() {
        startActivity(new Intent(mActivity, WithdrawActivity.class));
    }
}
