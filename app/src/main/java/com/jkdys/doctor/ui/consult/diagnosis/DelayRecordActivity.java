package com.jkdys.doctor.ui.consult.diagnosis;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseAppCompatActivity;


public class DelayRecordActivity extends BaseAppCompatActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_common;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);

        if (savedInstanceState == null) {

            DelayRecordFragment delayRecordFragment = new DelayRecordFragment();
            delayRecordFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().
                    beginTransaction().add(R.id.fr_content, delayRecordFragment).commit();
        }
    }
}
