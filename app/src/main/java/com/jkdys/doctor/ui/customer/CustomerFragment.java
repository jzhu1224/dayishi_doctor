package com.jkdys.doctor.ui.customer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseFragment;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class CustomerFragment extends BaseFragment {

    @BindView(R.id.tabSegment)
    QMUITabSegment mTabSegment;
    @BindView(R.id.contentViewPager)
    ViewPager mContentViewPager;

    CustomerFragment.ContentPagerAdapter contentPagerAdapter;
    List<Fragment> fragmentList;

    private class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @Override
    protected void initViews(View view, Bundle saveInstanceState) {
        toolbar.setTitle("我的患者");
        toolbar.setBackgroundDividerEnabled(false);
        initTabAndPager();
    }

    private void initTabAndPager() {
        fragmentList = new ArrayList<>();

        fragmentList.add(new VipCustomerFragment());
        fragmentList.add(new NormalCustomerFragment());

        contentPagerAdapter = new CustomerFragment.ContentPagerAdapter(getChildFragmentManager());
        mContentViewPager.setAdapter(contentPagerAdapter);
        mContentViewPager.setCurrentItem(0, false);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setIndicatorPosition(false);
        mTabSegment.setIndicatorWidthAdjustContent(true);
        mTabSegment.setDefaultNormalColor(getResources().getColor(R.color.color_0A1D3D));
        mTabSegment.setDefaultSelectedColor(getResources().getColor(R.color.color_6196FF));
        mTabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.tabSegment_item_3_title)));
        mTabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.tabSegment_item_4_title)));
        mTabSegment.setupWithViewPager(mContentViewPager, false);
        mTabSegment.setMode(QMUITabSegment.MODE_FIXED);
        mTabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                mTabSegment.hideSignCountView(index);
            }

            @Override
            public void onTabUnselected(int index) {

            }

            @Override
            public void onTabReselected(int index) {
                mTabSegment.hideSignCountView(index);
            }

            @Override
            public void onDoubleTap(int index) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_consult;
    }
}
