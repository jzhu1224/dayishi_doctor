package com.jkdys.doctor.ui.consult;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.framework.share.ShareInfoModel;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseFragment;
import com.jkdys.doctor.utils.ShareManager;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class ConsultFragment extends BaseFragment{


    @BindView(R.id.tabSegment)
    QMUITabSegment mTabSegment;
    @BindView(R.id.contentViewPager)
    ViewPager mContentViewPager;

    ContentPagerAdapter contentPagerAdapter;
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
        toolbar.setTitle(R.string.consult);
        toolbar.setBackgroundDividerEnabled(false);
        toolbar.addRightImageButton(R.drawable.ic_share,R.id.id_share).setOnClickListener(view1 -> {
            ShareManager.get().share(getActivity(),new ShareInfoModel());
        });
        initTabAndPager();
    }

    private void initTabAndPager() {
        fragmentList = new ArrayList<>();

        fragmentList.add(new PhoneFragment());
        fragmentList.add(new DoorFragment());

        contentPagerAdapter = new ContentPagerAdapter(getFragmentManager());
        mContentViewPager.setAdapter(contentPagerAdapter);
        mContentViewPager.setCurrentItem(0, false);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setIndicatorPosition(false);
        mTabSegment.setIndicatorWidthAdjustContent(true);
        mTabSegment.setDefaultNormalColor(getResources().getColor(R.color.color_0A1D3D));
        mTabSegment.setDefaultSelectedColor(getResources().getColor(R.color.color_6196FF));
        mTabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.tabSegment_item_1_title)));
        mTabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.tabSegment_item_2_title)));
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
