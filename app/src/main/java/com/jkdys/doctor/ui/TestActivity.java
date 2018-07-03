package com.jkdys.doctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.main.MainActivity;

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

        startActivity(new Intent(mActivity, MainActivity.class));
        finish();
    }
}
