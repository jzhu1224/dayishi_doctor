package com.framework.share;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class ApkUtils {

	public static final int GET_PKG_SIZE_OK = 0;

	private static boolean isNull(Object object) {
		if (object == null || object.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 根据包名判断是否安装
	 * 
	 * @param context
	 *            {@link Context}
	 * @param packageName
	 *            包名
	 * @return boolean
	 */
	public static boolean isInstalledApk(Context context, String packageName) {
		if (context == null || isNull(packageName)) {
			return false;
		}
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(packageName, 0);
			if (packageInfo != null) {
				return true;
			}
		} catch (NameNotFoundException e) {
			return false;
		}
		return false;
	}

	/**
	 * 根据包名判断是否安装
	 * 
	 * @param context
	 *            {@link Context}
	 * @param packageName
	 *            包名
	 * @return int versioncode
	 */
	public static int getInstalledApk(Context context, String packageName) {
		if (context == null || isNull(packageName)) {
			return -1;
		}
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(packageName, 0);
			if (packageInfo != null) {
				return packageInfo.versionCode;
			}
		} catch (NameNotFoundException e) {
			return -1;
		}
		return -1;
	}

	/**
	 * 根据包名,版本号判断是否已安装
	 * 
	 * @param context
	 *            {@link Context}
	 * @param packageName
	 * @param versionCode
	 * @return boolean
	 */
	public static boolean isInstalledApk(Context context, String packageName,
                                         String versionCode) {
		if (context == null || isNull(packageName) || isNull(versionCode)) {
			return false;
		}
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(packageName, 0);
			if (packageInfo != null) {
				String v = String.valueOf(packageInfo.versionCode);
				if (versionCode.equals(v))
					return true;
			}
		} catch (NameNotFoundException e) {
			return false;
		}
		return false;
	}
}
