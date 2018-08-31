package com.jkdys.doctor.ui.aboutus;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chairoad.framework.util.SystemUtil;
import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.BuildConfig;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.network.Api;
import com.jkdys.doctor.ui.BaseAppCompatActivity;
import com.jkdys.doctor.ui.base.BaseWebActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;

public class AboutUsActivity extends BaseAppCompatActivity {

    @BindView(R.id.my_toolbar)
    FrameLayout myToolbar;

    @BindView(R.id.tv_version)
    TextView tvVersion;

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

        tvVersion.setText(String.format("V%s", BuildConfig.VERSION_NAME));

        QMUICommonListItemView item1 = createItemView("客服热线");
        QMUICommonListItemView item2 = createItemView("官方网站");
        QMUICommonListItemView item3 = createItemView("服务协议");

        QMUIGroupListView.newSection(mActivity)
                .addItemView(item1, view -> {

                    Dexter.withActivity(mActivity)
                            .withPermission(Manifest.permission.CALL_PHONE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    SystemUtil.call(mActivity, "400111400");
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                    ToastUtil.show(mActivity,"拨打电话权限被拒绝");
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    new QMUIDialog.MessageDialogBuilder(mActivity)
                                            .setMessage("检测到没有拨打电话权限，请到权限管理页面授予相应权限，否则可能导致APP无法正常使用")
                                            .addAction("取消", (dialog, index) -> {
                                                token.cancelPermissionRequest();
                                                dialog.dismiss();
                                                finish();
                                            })
                                            .addAction("申请", (dialog, index) -> {
                                                token.continuePermissionRequest();
                                            }).setCanceledOnTouchOutside(false).show();
                                }
                            }).check(); })
                .addItemView(item2, view -> {
                    BaseWebActivity.openInWeb(mActivity, "http://www.jkdys.com");})
                .addItemView(item3, view -> {
                    BaseWebActivity.openInWeb(mActivity, Api.SERVICE_CONTRACT);})
                .setSeparatorDrawableRes(0,R.drawable.qmui_s_list_item_bg_with_border_bottom,R.drawable.qmui_s_list_item_bg_with_border_none,R.drawable.qmui_s_list_item_bg_with_border_bottom)
                .addTo(qmuiGroupListView);

    }

    private QMUICommonListItemView createItemView(CharSequence title) {
        return createItemView(0,title,"",QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
    }

    private QMUICommonListItemView createItemView(int drawableId, CharSequence title) {
        return createItemView(drawableId,title,"",QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
    }

    private QMUICommonListItemView createItemView(int drawableId, CharSequence title, String detailText, int accessory) {
        return qmuiGroupListView.createItemView(drawableId == 0?null:getResources().getDrawable(drawableId),
                title,
                detailText,
                QMUICommonListItemView.HORIZONTAL,
                accessory);
    }

    @Override
    protected void initStatusBar(int color) {
        super.initStatusBar(color);
        QMUIStatusBarHelper.setStatusBarDarkMode(mActivity);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_about_us;
    }
}
