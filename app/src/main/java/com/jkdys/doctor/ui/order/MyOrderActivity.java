package com.jkdys.doctor.ui.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseAppCompatActivity;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class MyOrderActivity extends BaseAppCompatActivity{

    @BindView(R.id.tabSegment)
    QMUITabSegment mTabSegment;
    @BindView(R.id.contentViewPager)
    ViewPager mContentViewPager;

    ContentPagerAdapter contentPagerAdapter;
    List<Fragment> fragmentList;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle(R.string.consult);
        toolbar.setBackgroundDividerEnabled(false);
        initTabAndPager();
    }


    private void initTabAndPager() {
        fragmentList = new ArrayList<>();

        fragmentList.add(new AllOrderFragment());
        fragmentList.add(new ProcessOrderFragment());
        fragmentList.add(new CompletedOrderFragment());
        fragmentList.add(new CanceledOrderFragment());
        fragmentList.add(new InvalidedOrderFragment());

        contentPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentViewPager.setAdapter(contentPagerAdapter);
        mContentViewPager.setCurrentItem(0, false);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setIndicatorPosition(false);
        mTabSegment.setIndicatorWidthAdjustContent(true);
        mTabSegment.setDefaultNormalColor(getResources().getColor(R.color.color_0A1D3D));
        mTabSegment.setDefaultSelectedColor(getResources().getColor(R.color.color_6196FF));
        mTabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.all)));
        mTabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.process)));
        mTabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.completed)));
        mTabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.canceled)));
        mTabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.invalided)));
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
}
