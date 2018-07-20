package com.jkdys.doctor.ui.fee;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseAppCompatActivity;
import com.jkdys.doctor.ui.order.MyOrderActivity;
import com.jkdys.doctor.utils.QMUIUtils;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import butterknife.BindView;

public class SettingFeesActivity extends BaseAppCompatActivity {

    @BindView(R.id.groupListView)
    QMUIGroupListView qmuiGroupListView;

    @Override
    protected int getLayout() {
        return R.layout.layout_group_listview;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);

        toolbar.setTitle("就诊费用");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());

        QMUICommonListItemView itemAccount = QMUIUtils.createItemView(qmuiGroupListView,"电话就诊");
        QMUICommonListItemView itemOrder = QMUIUtils.createItemView(qmuiGroupListView,"门诊预约");

        QMUIGroupListView.newSection(mActivity)
                .addItemView(itemAccount, view1 ->
                    new QMUIDialog.MenuDialogBuilder(mActivity)
                            .addItem("普通就诊", (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                            })
                            .addItem("特需就诊", (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                            })
                            .show()
                )
                .addItemView(itemOrder, view1 -> {
                    Intent intent = new Intent(mActivity, MyOrderActivity.class);
                    startActivity(intent);
                })
                .setSeparatorDrawableRes(0,R.drawable.qmui_s_list_item_bg_with_border_bottom,R.drawable.qmui_s_list_item_bg_with_border_none,R.drawable.qmui_s_list_item_bg_with_border_bottom)
                .addTo(qmuiGroupListView);
    }

}
