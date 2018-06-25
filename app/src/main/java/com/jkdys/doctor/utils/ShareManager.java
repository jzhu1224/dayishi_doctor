package com.jkdys.doctor.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.chairoad.framework.util.ToastUtil;
import com.framework.share.ShareInfoModel;
import com.framework.share.ShareListener;
import com.framework.share.SharePlatformEnum;
import com.framework.share.ShareUtils;

/**
 * Created by yanxin on 2018/4/13.
 */

public class ShareManager {

    private static ShareManager shareManager;
    private Handler handler;

    private ShareManager() {
        handler = new Handler(Looper.getMainLooper());
    }

    public static ShareManager get() {
        if (shareManager == null) {
            shareManager = new ShareManager();
        }
        return shareManager;
    }

    public void show(Context context, ShareInfoModel shareInfoModel) {
        showDialog(context, shareInfoModel);
    }

    private void showDialog(final Context context, final ShareInfoModel shareInfoModel) {
//        View view = LayoutInflater.from(context).inflate(R.layout.layout_share, null);
//        final AppAlertDialog dialog = AppAlertDialog.create(context, view);
//
//        view.findViewById(R.id.wechatBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                share(context, shareInfoModel, SharePlatformEnum.WECHAI);
//                dialog.dismiss();
//            }
//        });
//
//        view.findViewById(R.id.wechatFriendBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                share(context, shareInfoModel, SharePlatformEnum.WechatMoments);
//                dialog.dismiss();
//            }
//        });
//
//        view.findViewById(R.id.weiboBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                share(context, shareInfoModel, SharePlatformEnum.SINAWEIBO);
//                dialog.dismiss();
//            }
//        });
//
//        view.findViewById(R.id.copyBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SystemUtil.copy(context, shareInfoModel.siteUrl);
//                ToastUtil.show(context,"复制成功");
//                dialog.dismiss();
//            }
//        });
//
//        dialog.showFromBottom();
    }

    private void share(final Context context, ShareInfoModel shareInfoModel, final SharePlatformEnum sharePlatformEnum) {
        ShareUtils.sharePlatform(context, shareInfoModel, sharePlatformEnum, new ShareListener() {
            @Override
            public void onComplete() {
                ToastUtil.show(context, "分享成功");
            }

            @Override
            public void onError() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.show(context, "分享失败");
                    }
                });
            }

            @Override
            public void onCancel() {
                ToastUtil.show(context, "分享取消");
            }

            @Override
            public void onUnInstallApk() {
                ToastUtil.show(context, "没有安装"+sharePlatformEnum.getName()+"客户端");
            }
        });
    }

}
