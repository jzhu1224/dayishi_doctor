package com.jkdys.doctor.ui.myAccount.bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.BankCardInfo;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.myAccount.bank.widget.BankCardView;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;

public class BankCardListActivity extends MvpActivity<BankCardListView, BankCardListPresenter> implements BankCardListView{

    @Inject
    BankCardListPresenter bankCardListPresenter;

    @BindView(R.id.bankLL)
    LinearLayout bankLL;

    @BindView(R.id.addBtn)
    View addBtn;

    @NonNull
    @Override
    public BankCardListPresenter createPresenter() {
        getActivityComponent().inject(this);
        return bankCardListPresenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("银行卡");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
    }

    @Override
    protected void afterMvpDelegateCreateInvoked() {
        bankCardListPresenter.getBankCardInfo();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_bank_card_list;
    }

    @OnClick(R.id.addBtn)
    public void clickAddbtn() {
        Intent intent = new Intent(mActivity, BindBankCardActivity.class);
        startActivityForResult(intent,3);
    }

    @OnClick(R.id.supportBank)
    public void clicksupportBank() {
        startActivity(new Intent(mActivity, SupportBankListActivity.class));
    }

    @Override
    public void onRequestSuccess(BankCardInfo bankCardInfo) {
        if (bankCardInfo != null) {
            addBtn.setVisibility(View.GONE);
            BankCardView bankCardView = new BankCardView(mActivity);
            bankCardView.init(bankCardInfo.getBankid(), bankCardInfo.getBankname(), bankCardInfo.getBankaccount());
            bankLL.addView(bankCardView);

            toolbar.addRightImageButton(R.drawable.ic_more, R.id.id_right_btn).setOnClickListener(view -> showBottomSheet());

        } else {
            addBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onUnbindCardSuccess() {
        bankLL.removeAllViews();
        addBtn.setVisibility(View.VISIBLE);
        toolbar.removeAllRightViews();
    }

    QMUIBottomSheet qmuiBottomSheet;

    private void showBottomSheet() {
        if (qmuiBottomSheet == null) {
            QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(mActivity);
            builder.addItem("解绑银行卡");
            builder.addItem("取消");
            builder.setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                dialog.dismiss();
                if (tag.equals("解绑银行卡")) {
                    bankCardListPresenter.unBindCard();
                } else if (tag.equals("取消")) {

                }
            });
            qmuiBottomSheet = builder.build();
        }
        if (!qmuiBottomSheet.isShowing())
            qmuiBottomSheet.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (qmuiBottomSheet != null && qmuiBottomSheet.isShowing())
            qmuiBottomSheet.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 3) {
            bankCardListPresenter.getBankCardInfo();
        }
    }
}
