package com.chairoad.framework.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.chairoad.framework.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by lili on 16/9/18.
 */
public class AppUtils {

    /**
     * 判断是否在主进程中
     * @return true：是主进程
     */
    public static boolean isMainProcess(Context context){
        String processName = getProcessName(context);
        String packageName = context.getPackageName();
        return packageName.equals(processName);
    }

    /**
     * 进程名称
     * @param context
     * @return
     */
    public static String getProcessName(Context context){
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApp = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : runningApp) {
            if (pid == info.pid) {
                return info.processName;
            }
        }
        return "";
    }

    public static String getAPPVersion(Context context){
        String version = null;
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            version = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 检查服务是否运行中
     * @param context
     * @return true:正在运行
     */
    public static boolean isServiceRunning(Context context, Class c){
        String serviceName = c.getName();
        return isServiceRunning(context, serviceName);
    }

    /**
     * 检查服务是否运行中
     * @param context
     * @param serviceName
     * @return true:正在运行
     */
    private static boolean isServiceRunning(Context context, String serviceName){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfos = am.getRunningServices(500);
        if (serviceInfos != null && !serviceInfos.isEmpty()) {
            for (ActivityManager.RunningServiceInfo info : serviceInfos) {
                String runningName = info.service.getClassName();
                if (runningName.equals(serviceName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            Log.e("APP", "get status bar height fail: " + e);
            e.printStackTrace();
        }
        return context.getResources().getDimensionPixelSize(R.dimen.statusBar_height);
    }
}
