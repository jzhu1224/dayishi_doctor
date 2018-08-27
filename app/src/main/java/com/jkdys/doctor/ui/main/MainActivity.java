package com.jkdys.doctor.ui.main;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.chat.ChatHelper;
import com.jkdys.doctor.core.event.OnNewMessageArriveEvent;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.chat.YunFragment;
import com.jkdys.doctor.ui.consult.ConsultFragment;
import com.jkdys.doctor.ui.customer.CustomerFragment;
import com.jkdys.doctor.ui.login.LoginActivity;
import com.jkdys.doctor.ui.mine.MineFragment;
import com.jkdys.doctor.ui.verify.JumpHelper;
import com.jkdys.doctor.utils.FragmentUtils;
import com.jkdys.doctor.utils.UpdateAppHttpUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.vector.update_app.UpdateAppManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

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

    @Inject
    JumpHelper jumpHelper;

    @Inject
    UpdateAppHttpUtil updateAppHttpUtil;

    private int selectIndex;
    private Fragment mCurrentFragment,consultFragment,yunFragment,customerFragment,mineFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainPresenter.syncData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (logoutDialog!= null && logoutDialog.isShowing()) {
            logoutDialog.dismiss();
            logoutDialog = null;
        }
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        getActivityComponent().inject(this);
        if (loginInfoUtil.getLoginResponse() != null) {
            Dexter.withActivity(mActivity)
                    .withPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    ).withListener(new MultiplePermissionsListener() {
                @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                        checkUpdate();
                    } else {
                        ToastUtil.show(mActivity,"访问相机或者读取媒体权限被拒绝");
                    }
                }
                @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    ToastUtil.show(mActivity,"弹出对话框引导用户开启权限");

                    new QMUIDialog.MessageDialogBuilder(mActivity)
                            .setMessage("检测到没有运行APP需要的必要权限，请到权限管理页面授予相应权限，否则APP无法正常使用")
                            .addAction("取消", (dialog, index) -> {
                                token.cancelPermissionRequest();
                                dialog.dismiss();
                                finish();
                            })
                            .addAction("申请", (dialog, index) -> {
                                token.continuePermissionRequest();
                            }).setCanceledOnTouchOutside(false).show();
                }

            }).withErrorListener(error -> ToastUtil.show(mActivity,"error:"+error.name())).check();
        }

        fragmentManager = getSupportFragmentManager();

        initBottomNavigation();

        if (savedInstanceState != null) {
            selectIndex = savedInstanceState.getInt(KEY_SELECT_INDEX,0);
            consultFragment = findFragmentByPosition(0);
            customerFragment = findFragmentByPosition(1);
            yunFragment = findFragmentByPosition(2);
            mineFragment = findFragmentByPosition(3);
            mCurrentFragment = findFragmentByPosition(selectIndex);
        }

        setCurrentSelectedTab(selectIndex);

        if (!needLogout(getIntent())) {
            jump();
        }
    }

    @Override
    protected void afterMvpDelegateCreateInvoked() {
        super.afterMvpDelegateCreateInvoked();
    }

    private void checkUpdate() {
        new UpdateAppManager
                .Builder()
                .setPost(true)
                //当前Activity
                .setActivity(this)
                //更新地址
                .setUpdateUrl("https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/json/json.txt")
                //实现httpManager接口的对象
                .setHttpManager(updateAppHttpUtil)
                .setThemeColor(getResources().getColor(R.color.color_6196FF))
                .build()
                .update();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUnReadMessageCount();
    }

    private void updateUnReadMessageCount() {

        int unRead = ChatHelper.getInstance().getUnReadMessageCount();
        if (unRead == 0) {
            setNotification("",2);
        } else {
            setNotification(unRead+"",2);
        }
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
            case 2:
                if (yunFragment == null) {
                    yunFragment = new YunFragment();
                }
                mCurrentFragment = FragmentUtils.switchContent(fragmentManager,mCurrentFragment,yunFragment, R.id.fr_content, position,false);
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
        getActivityComponent().inject(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(OnNewMessageArriveEvent event) {
        setNotification(ChatHelper.getInstance().getUnReadMessageCount()+"",2);
    }

    private Dialog logoutDialog;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        needLogout(intent);
    }

    private void jump() {
        if (loginInfoUtil.getLoginResponse() == null) {
            //未登录
            Intent intent = new Intent(mActivity, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        int redirect = loginInfoUtil.getRedirecttopage();
        jumpHelper.jump(mActivity,redirect);
    }

    private boolean needLogout(Intent intent) {
        if (intent.getBooleanExtra("TOKEN_EXPIRED",false)) {
            loginInfoUtil.clear();
            if (logoutDialog == null) {
                logoutDialog = new QMUIDialog.MessageDialogBuilder(mActivity)
                        .setMessage("登录过期，请重新登录")
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addAction("退出", ((dialog, index) -> finish()))
                        .addAction("确定", (dialog, index) -> {
                            Intent intent2 = new Intent(mActivity, LoginActivity.class);
                            startActivity(intent2);
                            finish();
                        }).create();
            }

            if (!logoutDialog.isShowing()) {
                logoutDialog.show();
            }
            mainPresenter.logout();
            return true;
        }
        return false;
    }

    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                ToastUtil.show(this, "再按一次退出程序");
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
               // System.exit(0);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
