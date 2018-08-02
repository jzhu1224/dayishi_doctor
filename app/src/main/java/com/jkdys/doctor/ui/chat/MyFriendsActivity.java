package com.jkdys.doctor.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.MyFriendData;
import com.jkdys.doctor.data.model.SearchDoctorData;
import com.jkdys.doctor.ui.BaseAppCompatActivity;
import com.jkdys.doctor.ui.chat.doctor.DoctorDetailActivity;

public class MyFriendsActivity extends BaseAppCompatActivity implements MyFriendsFragment.OnFriendsItemClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            MyFriendsFragment myFriendsFragment = new MyFriendsFragment();
            myFriendsFragment.setOnFriendsItemClickListener(this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fr_content,myFriendsFragment)
                    .commit();
        }
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("我的好友");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_common_with_titlebar;
    }

    @Override
    public void onItemClick(MyFriendData myFriendData) {
        Intent intent = new Intent(mActivity, DoctorDetailActivity.class);
        intent.putExtra("doctorId", myFriendData.getDid());
        startActivity(intent);
    }
}
