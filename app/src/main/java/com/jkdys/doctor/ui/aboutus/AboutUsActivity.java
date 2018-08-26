package com.jkdys.doctor.ui.aboutus;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jkdys.doctor.BuildConfig;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseAppCompatActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
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
                .addItemView(item1, view -> {})
                .addItemView(item2, view -> {})
                .addItemView(item3, view -> {})
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
