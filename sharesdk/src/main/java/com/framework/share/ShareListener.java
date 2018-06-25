package com.framework.share;

/**
 * Created by yanxin on 2018/4/13.
 */

public interface ShareListener {

    void onComplete();

    void onError();

    void onCancel();

    void onUnInstallApk();

}
