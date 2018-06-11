package com.dayishiapp.doctor.ui.customer;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;
import com.dayishiapp.doctor.R;
import com.dayishiapp.doctor.ui.MvpFragment;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import javax.inject.Inject;
import butterknife.OnClick;

public class CustomerFragment extends MvpFragment<CustomerView,CustomerPresenter> implements CustomerView {

    @Inject
    CustomerPresenter consultPresenter;
    @Override
    protected void initViews(View view) {
        toolbar.setTitle(R.string.customer);
    }

    @OnClick(R.id.btn_test)
    void onBtnTestClick() {
       showMessagePositiveDialog();
    }

    private void showMessagePositiveDialog() {
        new QMUIDialog.MessageDialogBuilder(getActivity())
                .setTitle("标题")
                .setMessage("确定要发送吗？")
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", (dialog, index) -> {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();
                })
                .create().show();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_customer;
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
    public CustomerPresenter createPresenter() {
        getActivityComponent().inject(this);
        return consultPresenter;
    }
}
