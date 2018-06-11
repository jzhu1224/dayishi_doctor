package com.dayishiapp.doctor.ui.mine;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.dayishiapp.doctor.R;
import com.dayishiapp.doctor.ui.MvpFragment;
import com.dayishiapp.doctor.ui.login.LoginActivity;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import javax.inject.Inject;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MineFragment extends MvpFragment<MineView,MinePresenter> implements MineView {

    @Inject
    MinePresenter consultPresenter;

    @BindView(R.id.groupListView)
    QMUIGroupListView qmuiGroupListView;
    @BindView(R.id.profile_image)
    CircleImageView profileImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_info)
    TextView tvInfo;


    @Override
    protected void initViews(View view) {
        toolbar.setTitle(R.string.person_center);

        profileImg.setImageResource(R.drawable.test_image);
        tvName.setText("沈月");
        tvInfo.setText("上海第九人民医院 \n儿科 主治医师");

        QMUICommonListItemView itemAccount = createItemView(R.drawable.ic_account,"我的账户");
        QMUICommonListItemView itemOrder = createItemView(R.drawable.ic_order,"我的订单");
        QMUICommonListItemView itemFee = createItemView(R.drawable.ic_fee,"就诊费用");
        QMUICommonListItemView itemSetting = createItemView(R.drawable.ic_setting,"设置");
        QMUICommonListItemView itemCustomerService = createItemView(R.drawable.ic_customer_service,"联系客服");
        QMUICommonListItemView itemInvent = createItemView(R.drawable.ic_invent,"邀请码","yaoqingma",QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        QMUIGroupListView.newSection(getContext())
                .addItemView(itemAccount, view1 -> {

                })
                .addItemView(itemOrder, view1 -> {

                })
                .addItemView(itemFee, view1 -> {

                })
                .addItemView(itemSetting, view1 -> {

                })
                .addItemView(itemCustomerService, view1 -> {

                })
                .addItemView(itemInvent, view1 -> {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                })
                .setSeparatorDrawableRes(0,R.drawable.qmui_s_list_item_bg_with_border_bottom,R.drawable.qmui_s_list_item_bg_with_border_none,R.drawable.qmui_s_list_item_bg_with_border_bottom)
                .addTo(qmuiGroupListView);
    }

    private QMUICommonListItemView createItemView(int drawableId, CharSequence title, String detailText, int accessory) {
        return qmuiGroupListView.createItemView(getResources().getDrawable(drawableId),
                title,
                detailText,
                QMUICommonListItemView.HORIZONTAL,
                accessory);
    }

    private QMUICommonListItemView createItemView(int drawableId, CharSequence title) {
        return createItemView(drawableId,title,"",QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showError(String message) {

    }

    @NonNull
    @Override
    public MinePresenter createPresenter() {
        getActivityComponent().inject(this);
        return consultPresenter;
    }
}
