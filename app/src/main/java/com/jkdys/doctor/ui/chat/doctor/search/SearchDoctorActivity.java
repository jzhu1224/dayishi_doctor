package com.jkdys.doctor.ui.chat.doctor.search;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseAppCompatActivity;

public class SearchDoctorActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fr_content,new SearchDoctorFragment())
                    .commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_common;
    }
}
