package com.jkdys.doctor.ui.myAccount.bank.tradeRecord;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseAppCompatActivity;

public class TradeRecordActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction().add(R.id.fr_content, new TradeRecordFragment()).commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_common;
    }
}
