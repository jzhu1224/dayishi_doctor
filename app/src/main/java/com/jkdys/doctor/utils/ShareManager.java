package com.jkdys.doctor.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.chairoad.framework.util.ToastUtil;
import com.framework.share.ShareInfoModel;
import com.framework.share.ShareListener;
import com.framework.share.SharePlatformEnum;
import com.framework.share.ShareUtils;
import com.jkdys.doctor.R;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

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

    public void share(Context context, ShareInfoModel shareInfoModel) {
        showDialog(context, shareInfoModel);
    }

    private void showDialog(final Context context, final ShareInfoModel shareInfoModel) {
        final int TAG_SHARE_WECHAT_FRIEND = 0;
        final int TAG_SHARE_WECHAT_MOMENT = 1;
        final int TAG_SHARE_WEIBO = 2;
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(context);
        builder.addItem(R.drawable.icon_more_operation_share_friend, "分享到微信", TAG_SHARE_WECHAT_FRIEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.icon_more_operation_share_moment, "分享到朋友圈", TAG_SHARE_WECHAT_MOMENT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.icon_more_operation_share_weibo, "分享到微博", TAG_SHARE_WEIBO, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                //.addItem(R.mipmap.icon_more_operation_share_chat, "分享到私信", TAG_SHARE_CHAT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                //.addItem(R.mipmap.icon_more_operation_save, "保存到本地", TAG_SHARE_LOCAL, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .setOnSheetItemClickListener((dialog, itemView) -> {
                    dialog.dismiss();
                    int tag = (int) itemView.getTag();
                    switch (tag) {
                        case TAG_SHARE_WECHAT_FRIEND:
                            Toast.makeText(context, "分享到微信", Toast.LENGTH_SHORT).show();
                            share(context, shareInfoModel, SharePlatformEnum.WECHAI);
                            break;
                        case TAG_SHARE_WECHAT_MOMENT:
                            Toast.makeText(context, "分享到朋友圈", Toast.LENGTH_SHORT).show();
                            share(context, shareInfoModel, SharePlatformEnum.WechatMoments);
                            break;
                        case TAG_SHARE_WEIBO:
                            Toast.makeText(context, "分享到微博", Toast.LENGTH_SHORT).show();
                            share(context, shareInfoModel, SharePlatformEnum.SINAWEIBO);
                            break;
                    }
                }).build().show();
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
