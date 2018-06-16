package com.jkdys.doctor.ui.myAccount;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import javax.inject.Inject;

import butterknife.BindView;

public class MyAccountActivity extends MvpActivity<MyAccountView,MyAccountPresenter> implements MyAccountView {

    @Inject
    MyAccountPresenter myAccountPresenter;

    @BindView(R.id.my_toolbar)
    FrameLayout myToolbar;

    @BindView(R.id.groupListView)
    QMUIGroupListView qmuiGroupListView;

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


        QMUICommonListItemView phoneFee = createItemView(0,"电话诊断收入","1000,00",0);
        QMUICommonListItemView doorFee = createItemView(0,"门诊预约收费","1099.00",0);
        QMUICommonListItemView tuiguangFee = createItemView(0, "推广补贴", "500", 0);


        QMUIGroupListView.newSection(mActivity)
                .addItemView(phoneFee, view -> {})
                .addItemView(doorFee, view -> {})
                .addItemView(tuiguangFee, view -> {})
                .setSeparatorDrawableRes(0,R.drawable.qmui_s_list_item_bg_with_border_bottom,R.drawable.qmui_s_list_item_bg_with_border_none,R.drawable.qmui_s_list_item_bg_with_border_bottom)
                .addTo(qmuiGroupListView);

        QMUICommonListItemView bindCard = createItemView(R.drawable.ic_bank, "绑定银行卡", "未绑定", QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);


        QMUIGroupListView.newSection(mActivity)
                .addItemView(bindCard, view -> {})
                .setSeparatorDrawableRes(0,R.drawable.qmui_s_list_item_bg_with_border_bottom,R.drawable.qmui_s_list_item_bg_with_border_none,R.drawable.qmui_s_list_item_bg_with_border_bottom)
                .addTo(qmuiGroupListView);

        QMUICommonListItemView tradeRecord = createItemView(R.drawable.ic_trade_record, "交易记录", "", QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUIGroupListView.newSection(mActivity)
                .addItemView(tradeRecord, view -> {})
                .setSeparatorDrawableRes(0,R.drawable.qmui_s_list_item_bg_with_border_bottom,R.drawable.qmui_s_list_item_bg_with_border_none,R.drawable.qmui_s_list_item_bg_with_border_bottom)
                .addTo(qmuiGroupListView);
    }

    @Override
    protected void initStatusBar(int color) {
        super.initStatusBar(color);
        QMUIStatusBarHelper.setStatusBarDarkMode(mActivity);
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
}
