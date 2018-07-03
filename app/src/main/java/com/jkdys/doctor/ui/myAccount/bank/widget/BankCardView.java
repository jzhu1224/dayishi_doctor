package com.jkdys.doctor.ui.myAccount.bank.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkdys.doctor.R;
import com.jkdys.doctor.utils.BankUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BankCardView extends FrameLayout {

    @BindView(R.id.main_bg_view)
    CardView cardView;
    @BindView(R.id.ivBankIcon)
    ImageView ivBankIcon;
    @BindView(R.id.tvBankNo)
    TextView tvBankNo;
    @BindView(R.id.tvBankName)
    TextView tvBankName;

    public BankCardView(@NonNull Context context) {
        this(context, null);
    }

    public BankCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BankCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.item_bank_card,this);
        ButterKnife.bind(this);
    }

    public void init(String bankId, String bankName, String cardNo) {
        cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), BankUtil.getBankColorById(bankId)));
        ivBankIcon.setImageResource(BankUtil.getBankIconById(bankId));
        tvBankNo.setText(cardNo);
        tvBankName.setText(bankName);
    }
}
