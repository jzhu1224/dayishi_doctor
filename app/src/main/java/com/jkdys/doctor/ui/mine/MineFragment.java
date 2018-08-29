package com.jkdys.doctor.ui.mine;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.chairoad.framework.util.SystemUtil;
import com.chairoad.framework.util.ToastUtil;
import com.framework.share.ShareInfoModel;
import com.hyphenate.EMCallBack;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.chat.ChatHelper;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.data.db.ChatDBManager;
import com.jkdys.doctor.data.model.Doctor;
import com.jkdys.doctor.data.network.Api;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;
import com.jkdys.doctor.ui.MvpFragment;
import com.jkdys.doctor.ui.aboutus.AboutUsActivity;
import com.jkdys.doctor.ui.base.BaseWebActivity;
import com.jkdys.doctor.ui.fee.SettingFeesActivity;
import com.jkdys.doctor.ui.invent.InventCodeActivity;
import com.jkdys.doctor.ui.login.LoginActivity;
import com.jkdys.doctor.ui.mine.qrcode.MyQRCodeFragment;
import com.jkdys.doctor.ui.myAccount.MyAccountActivity;
import com.jkdys.doctor.ui.order.MyOrderActivity;
import com.jkdys.doctor.ui.profile.PersonalProfileActivity;
import com.jkdys.doctor.ui.setting.SettingActivity;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;
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

    @Inject
    LoginInfoUtil loginInfoUtil;

    @Override
    protected void afterCreatePresenter() {
        super.afterCreatePresenter();
    }

    @Override
    public void onResume() {
        super.onResume();


        Doctor doctor = loginInfoUtil.getDoctor();

        ImageLoader.with(getContext())
                .placeholder(R.drawable.img_doctor)
                .load(doctor.getPicheadurl())
                .into(profileImg);

        tvName.setText(doctor.getName());
        tvInfo.setText(doctor.getHospital()+"\n"+doctor.getFaculty()+ "  "+doctor.getTitle());
    }

    @Override
    protected void initViews(View view, Bundle saveInstanceState) {
        toolbar.setTitle(R.string.person_center);
        QMUICommonListItemView itemAccount = createItemView(R.drawable.ic_account,"我的账户");
        QMUICommonListItemView itemOrder = createItemView(R.drawable.ic_order,"我的订单");
        QMUICommonListItemView itemFee = createItemView(R.drawable.ic_fee,"就诊费用");
        QMUICommonListItemView itemInvent = createItemView(R.drawable.ic_invent,"邀请码");
        QMUICommonListItemView itemAboutUs = createItemView(R.drawable.ic_about_us,"关于我们");
        QMUICommonListItemView itemStatement = createItemView(R.drawable.ic_low,"法律声明");
        QMUICommonListItemView itemSetting = createItemView(R.drawable.ic_setting,"设置");

        QMUIGroupListView.newSection(getContext())
                .addItemView(itemAccount, view1 -> {
                    Intent intent = new Intent(getActivity(), MyAccountActivity.class);
                    startActivity(intent);
                })
                .addItemView(itemOrder, view1 -> {
                    Intent intent = new Intent(getActivity(), MyOrderActivity.class);
                    startActivity(intent);
                })
                .addItemView(itemFee, view1 ->
                    startActivity(new Intent(getActivity(), SettingFeesActivity.class))
                )
                .addItemView(itemStatement, view1 -> {
                    BaseWebActivity.openInWeb(getActivity(), Api.STATEMENTS);
                })
                .addItemView(itemInvent, view1 -> {
                    startActivity(new Intent(getActivity(), InventCodeActivity.class));
                    //copy(loginInfoUtil.getInventCode(), Objects.requireNonNull(getContext()));
                })
                .addItemView(itemAboutUs, view1 -> {
                    startActivity(new Intent(getActivity(), AboutUsActivity.class));
                })
                .addItemView(itemSetting, view1 -> {
                    startActivity(new Intent(getActivity(), SettingActivity.class));
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

    @OnClick(R.id.rl_mine_info)
    void onMyInfoClick() {
        startActivity(new Intent(getActivity(), PersonalProfileActivity.class));
    }

    @NonNull
    @Override
    public MinePresenter createPresenter() {
        getActivityComponent().inject(this);
        return consultPresenter;
    }

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(String message) {

    }

    @OnClick(R.id.img_qrcode)
    void onQRCodeClick() {
        MyQRCodeFragment myQRCodeFragment = new MyQRCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("QRCodeUrl", loginInfoUtil.getLoginResponse().getDoctor().getTwodimensioncodeurl());
        bundle.putString("mAvatar", loginInfoUtil.getAvatar());
        myQRCodeFragment.setArguments(bundle);
        myQRCodeFragment.show(getChildFragmentManager(), "");
    }

    @OnClick(R.id.btn_logout)
    void onLogoutClick() {
        new QMUIDialog.MessageDialogBuilder(getActivity())
                .setMessage("确定退出？")
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addAction("取消", ((dialog, index) -> {
                    dialog.dismiss();
                }))
                .addAction("确定", (dialog, index) -> {
                    dialog.dismiss();
                    loginInfoUtil.clear();
                    ChatDBManager.getInstance().closeDB();
                    ChatHelper.getInstance().logout(true, new EMCallBack() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(int i, String s) {

                        }

                        @Override
                        public void onProgress(int i, String s) {

                        }
                    });
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    if (getActivity() != null)
                        getActivity().finish();
                }).show();
    }
}
