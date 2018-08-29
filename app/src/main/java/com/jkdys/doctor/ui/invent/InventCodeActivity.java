package com.jkdys.doctor.ui.invent;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.Doctor;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;
import com.jkdys.doctor.ui.MvpActivity;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;

public class InventCodeActivity extends MvpActivity<InventView,InventCodePresenter> implements InventView {

    @Inject
    InventCodePresenter presenter;

    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;

    @BindView(R.id.btn_commit)
    Button btnCommit;

    @Inject
    LoginInfoUtil loginInfoUtil;

    QMUICommonListItemView itemInvent;
    EditText editText;

    @NonNull
    @Override
    public InventCodePresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);

        toolbar.setTitle("邀请码");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
    }

    @Override
    protected void afterMvpDelegateCreateInvoked() {
        super.afterMvpDelegateCreateInvoked();


        itemInvent = createItemView("邀请码(我的)", loginInfoUtil.getInventCode());

        if (TextUtils.isEmpty(loginInfoUtil.getDoctor().getParentdoctorid())) {
            QMUICommonListItemView itemWithCustom = mGroupListView.createItemView("邀请码(推荐人)");
            itemWithCustom.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
            editText = new EditText(mActivity);
            editText.setHint("请输入邀请码");
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            editText.setTextColor(Color.parseColor("#0A1D3D"));
            editText.setBackground(null);
            itemWithCustom.addAccessoryCustomView(editText);

            QMUIGroupListView.newSection(mActivity)
                    .addItemView(itemInvent,view -> copy(loginInfoUtil.getInventCode(),mActivity))
                    .setSeparatorDrawableRes(0,R.drawable.qmui_s_list_item_bg_with_border_bottom,R.drawable.qmui_s_list_item_bg_with_border_none,R.drawable.qmui_s_list_item_bg_with_border_bottom)
                    .addItemView(itemWithCustom,null)
                    .addTo(mGroupListView);
            btnCommit.setVisibility(View.VISIBLE);
        } else {
            QMUIGroupListView.newSection(mActivity)
                    .addItemView(itemInvent,view -> copy(loginInfoUtil.getInventCode(),mActivity))
                    .setSeparatorDrawableRes(0,R.drawable.qmui_s_list_item_bg_with_border_bottom,R.drawable.qmui_s_list_item_bg_with_border_none,R.drawable.qmui_s_list_item_bg_with_border_bottom)
                    .addTo(mGroupListView);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_invent_code;
    }

    @OnClick(R.id.btn_commit)
    void onBtnCommitClick() {
        if (!TextUtils.isEmpty(editText.getText().toString())) {
            presenter.setInventCode(editText.getText().toString());
        } else {
            ToastUtil.show(mActivity, "邀请码不能为空");
        }
    }

    public void copy(String content, Context context) {// 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", content);
        assert cmb != null;
        cmb.setPrimaryClip(mClipData);
        ToastUtil.show(context, "邀请码已复制到剪贴板");
    }

    private QMUICommonListItemView createItemView(CharSequence title, String detailText, int accessory) {
        return mGroupListView.createItemView(null,
                title,
                detailText,
                QMUICommonListItemView.HORIZONTAL,
                accessory);
    }

    private QMUICommonListItemView createItemView(CharSequence title, String detailText) {
        return createItemView(title,detailText,QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
    }

    @Override
    public void onInventUpdateSuccess() {
        Doctor doctor = loginInfoUtil.getDoctor();
        doctor.setParentdoctorid("xxx");
        loginInfoUtil.saveDoctor(doctor);
        finish();
    }
}
