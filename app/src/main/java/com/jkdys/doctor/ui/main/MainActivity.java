package com.jkdys.doctor.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.consult.ConsultFragment;
import com.jkdys.doctor.ui.customer.CustomerFragment;
import com.jkdys.doctor.ui.login.LoginActivity;
import com.jkdys.doctor.ui.mine.MineFragment;
import com.jkdys.doctor.ui.verify.personalInfo.PersonalInfoActivity;
import com.jkdys.doctor.ui.verify.userVerify.IdentityActivity;
import com.jkdys.doctor.utils.FragmentUtils;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView {

    public final static String KEY_SELECT_INDEX = "key_select_index";

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    @Inject
    MainPresenter mainPresenter;

    @Inject
    LoginInfoUtil loginInfoUtil;

    private int selectIndex;
    private Fragment mCurrentFragment,consultFragment,customerFragment,mineFragment;
    FragmentManager fragmentManager;

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);

        getActivityComponent().inject(this);

//        if (jump())
//            return;

        fragmentManager = getSupportFragmentManager();

        initBottomNavigation();
        setNotification("1",2);

        if (savedInstanceState != null) {
            selectIndex = savedInstanceState.getInt(KEY_SELECT_INDEX,0);
            consultFragment = findFragmentByPosition(0);
            customerFragment = findFragmentByPosition(1);
            mineFragment = findFragmentByPosition(3);
        }

        setCurrentSelectedTab(selectIndex);
    }

    private boolean jump() {
        if (loginInfoUtil.getLoginResponse() == null) {
            //未登录
            Intent intent = new Intent(mActivity, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        int redirect = loginInfoUtil.getRedirecttopage();

        if (redirect > 0) {
            Intent intent = new Intent();

            switch (redirect) {
                case 1:
                    //实名认证页面
                    intent.setClass(mActivity, IdentityActivity.class);
                    break;
                case 2:
                    //所属医院页面
                    intent.setClass(mActivity, PersonalInfoActivity.class);
                    break;
                case 3:
                    //上传医生上岗证和医院工牌页面
                    intent.setClass(mActivity, IdentityActivity.class);
                    break;
            }
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

    private Fragment findFragmentByPosition(int position) {
        return fragmentManager.findFragmentByTag(FragmentUtils.makeFragmentName(R.id.fr_content,position));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECT_INDEX,selectIndex);
    }

    private void setNotification(String title, int position) {
        // Customize notification (title, background, typeface)
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));
        // Add or remove notification for each item
        bottomNavigation.setNotification(title, position);
    }

    private void setCurrentSelectedTab(int position) {
        // Set current item programmatically
        bottomNavigation.setCurrentItem(position);
    }

    private void initBottomNavigation() {
        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab1, R.drawable.ic_consult_normal, R.color.color_white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab2, R.drawable.ic_customers_normal, R.color.color_white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab3, R.drawable.ic_message_normal, R.color.color_white);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab4, R.drawable.ic_mine_normal_, R.color.color_white);

        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        // Set background color
        //bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        // Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);

        // Enable the translation of the FloatingActionButton
        //bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);

        // Change colors
        bottomNavigation.setAccentColor(getResources().getColor(R.color.color_6196FF));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.color_ADB5C1));

        // Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

        // Display color under navigation bar (API 21+)
        // Don't forget these lines in your style-v21
        // <item name="android:windowTranslucentNavigation">true</item>
        // <item name="android:fitsSystemWindows">true</item>
        bottomNavigation.setTranslucentNavigationEnabled(true);

        // Manage titles
        //bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        //bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        // Use colored navigation with circle reveal effect
        //bottomNavigation.setColored(true);


        // Enable / disable item & set disable color
        //bottomNavigation.enableItemAtPosition(2);
        //bottomNavigation.disableItemAtPosition(2);
        //bottomNavigation.setItemDisableColor(Color.parseColor("#3A000000"));

        // Set listeners
        bottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {
            // Do something cool here...
            doOnTabSelected(position);
            return true;
        });
        bottomNavigation.setOnNavigationPositionListener(y -> {
            // Manage the new y position
        });
    }

    private void doOnTabSelected(@IntRange(from = 0, to = 3) int position) {
        switch (position) {
            case 0:
                if (consultFragment == null) {
                    consultFragment = new ConsultFragment();
                }
                mCurrentFragment = FragmentUtils.switchContent(fragmentManager, mCurrentFragment, consultFragment, R.id.fr_content, position, false);
                break;
            case 1:
                if (customerFragment == null) {
                    customerFragment = new CustomerFragment();
                }
                mCurrentFragment = FragmentUtils.switchContent(fragmentManager,mCurrentFragment,customerFragment, R.id.fr_content, position,false);
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
                mCurrentFragment = FragmentUtils.switchContent(fragmentManager,mCurrentFragment,mineFragment, R.id.fr_content, position,false);
                break;
            default:
        }
        selectIndex = position;
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        //getActivityComponent().inject(this);
        return mainPresenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showError(String message) {

    }
}
