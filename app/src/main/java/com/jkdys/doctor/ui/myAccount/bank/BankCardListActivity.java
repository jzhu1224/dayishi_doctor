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
//        Intent intent = new Intent(getContext(), BindBankCardActivity.class);
//        intent.putExtra(BindBankCardActivity.PARAM_BIZ_TYPE,2);
//        startActivity(intent);
    }

    @OnClick(R.id.supportBank)
    public void clicksupportBank() {
        startActivity(new Intent(mActivity, SupportBankListActivity.class));
    }

    @Override
    public void onRequestSuccess(BankCardInfo bankCardInfo) {

//        if (bankCardInfo == null) {
//            bankCardInfo = new BankCardInfo();
//            bankCardInfo.setBankid("7886E2DA-8847-4570-9390-DD1BA8739FD9");
//            bankCardInfo.setBankname("招商银行");
//            bankCardInfo.setBankaccount("6222*********4541");
//        }

        if (bankCardInfo != null) {
            addBtn.setVisibility(View.GONE);
            BankCardView bankCardView = new BankCardView(mActivity);
            bankCardView.init(bankCardInfo.getBankid(), bankCardInfo.getBankname(), bankCardInfo.getBankaccount());
            bankLL.addView(bankCardView);
        } else {
            addBtn.setVisibility(View.VISIBLE);
        }
    }
}
