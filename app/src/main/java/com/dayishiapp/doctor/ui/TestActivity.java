package com.dayishiapp.doctor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.dayishiapp.doctor.R;

public class TestActivity extends BaseAppCompatActivity {
    @Override
    protected int getLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("测试");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> {
            finish();
        });
    }
}
