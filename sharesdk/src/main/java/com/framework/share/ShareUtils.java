package com.framework.share;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.wechat.utils.WechatClientNotExistException;

/**
 * Created by zhengw on 2016/5/26.
 */

public class ShareUtils {

    public static void sharePlatform(Context context, int position, ShareInfoModel shareInfoModel,
                                     PlatformActionListener paListener) {
        if (position >= shareInfoModel.platforms.size()) {
            return;
        }

        sharePlatform(context, shareInfoModel.platforms.get(position).platformName, shareInfoModel, paListener);

    }

    public static void sharePlatform(Context context, String plateformName, ShareInfoModel shareInfoModel,
                                     PlatformActionListener paListener) {
        String title = shareInfoModel.title;
        String desc = shareInfoModel.desc;
        String iconUrl = shareInfoModel.imgUrl;
        String siteUrl = shareInfoModel.siteUrl;
        Platform.ShareParams shareParams = new Platform.ShareParams();
        if (TextUtils.equals(plateformName, Wechat.NAME)) {
            shareParams = new Wechat.ShareParams();
            shareParams.setShareType(Platform.SHARE_WEBPAGE);
            shareParams.setTitle(title);
            shareParams.setText(desc);
            shareParams.setImageUrl(iconUrl);
            shareParams.setUrl(siteUrl);
        } else if (TextUtils.equals(plateformName, WechatMoments.NAME)) {
            shareParams = new WechatMoments.ShareParams();
            shareParams.setShareType(Platform.SHARE_WEBPAGE);
            shareParams.setTitle(title);
            shareParams.setText(desc);//朋友圈不显示此字段
            if (TextUtils.isEmpty(iconUrl)) {
                shareParams.setImagePath(shareInfoModel.imgPath);
            } else {
                shareParams.setImageUrl(iconUrl);
            }
            shareParams.setUrl(siteUrl);
        }/*else if(TextUtils.equals(plateformName, QQ.NAME)){
            shareParams = new QQ.ShareParams();
            shareParams.setTitle(title);
            shareParams.setTitleUrl(siteUrl);
            shareParams.setText(desc);
            shareParams.setImageUrl(iconUrl);
        }else if(TextUtils.equals(plateformName, QZone.NAME)){
            shareParams = new QZone.ShareParams();
            shareParams.setTitle(title);
            shareParams.setTitleUrl(siteUrl);
            shareParams.setText(desc);
            shareParams.setImageUrl(iconUrl);
            shareParams.setSite(title);
            shareParams.setSiteUrl(siteUrl);
        }else if(TextUtils.equals(plateformName, SinaWeibo.NAME)){
            shareParams = new SinaWeibo.ShareParams();
            shareParams.setTitle(title);
            shareParams.setText(desc + siteUrl + " ");
            shareParams.setImageUrl(iconUrl);
            //此设置配合手机未安装微博客户端分享
            shareParams.setUrl("");
        }*/

        Platform platform = ShareSDK.getPlatform(plateformName);
        if (platform == null) {
            Toast.makeText(context, "不支持的平台类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (paListener != null) {
            platform.setPlatformActionListener(paListener); // 设置分享事件回调
        }
        /*if (TextUtils.equals(plateformName, SinaWeibo.NAME) && !ApkUtils.isInstalledApk(context, "com.sina.weibo")) {
            showShare(context,plateformName, shareParams);
        } else {*/
        // 执行图文分享
        platform.share(shareParams);
//        }
    }

    public static void sharePlatform(Context context, ShareInfoModel shareInfoModel, SharePlatformEnum platformEnum, final ShareListener listener) {
        String title = shareInfoModel.title;
        String desc = shareInfoModel.desc;
        String iconUrl = shareInfoModel.imgUrl;
        String siteUrl = shareInfoModel.siteUrl;
        Platform.ShareParams shareParams = new Platform.ShareParams();
        String plateformName = "";
        if (SharePlatformEnum.WECHAI.equals(platformEnum)) {
            plateformName = Wechat.NAME;
            shareParams = new Wechat.ShareParams();
            shareParams.setShareType(Platform.SHARE_WEBPAGE);
            shareParams.setTitle(title);
            shareParams.setText(desc);
            if(!TextUtils.isEmpty(iconUrl)) {
                shareParams.setImageUrl(iconUrl);
            } else if(!TextUtils.isEmpty(shareInfoModel.imgPath)) {
                shareParams.setImagePath(shareInfoModel.imgPath);
            } else if(shareInfoModel.imgData != null) {
                shareParams.setImageData(shareInfoModel.imgData);
            }
            shareParams.setUrl(siteUrl);
        } else if (SharePlatformEnum.WechatMoments.equals(platformEnum)) {
            plateformName = WechatMoments.NAME;
            shareParams = new WechatMoments.ShareParams();
            shareParams.setShareType(Platform.SHARE_WEBPAGE);
            shareParams.setTitle(title);
            shareParams.setText(desc);//朋友圈不显示此字段
            if(!TextUtils.isEmpty(iconUrl)) {
                shareParams.setImageUrl(iconUrl);
            } else if(!TextUtils.isEmpty(shareInfoModel.imgPath)) {
                shareParams.setImagePath(shareInfoModel.imgPath);
            } else if(shareInfoModel.imgData != null) {
                shareParams.setImageData(shareInfoModel.imgData);
            }
            shareParams.setUrl(siteUrl);
        } else if (SharePlatformEnum.SINAWEIBO.equals(platformEnum)) {
            if(TextUtils.isEmpty(shareInfoModel.imgPath)) {
                OnekeyShare onekeyShare = new OnekeyShare();
                onekeyShare.setPlatform(SinaWeibo.NAME);
                onekeyShare.setText(desc + " "+siteUrl + " ");
                onekeyShare.setImageUrl(shareInfoModel.imgUrl);
                onekeyShare.setCallback(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        if(listener != null) {
                            listener.onComplete();
                        }
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        if(listener != null) {
                            if (throwable instanceof WechatClientNotExistException) {
                                listener.onUnInstallApk();
                            } else {
                                listener.onError();
                            }
                        }
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        if(listener != null) {
                            listener.onCancel();
                        }
                    }
                });
                onekeyShare.show(context);
                return;
            } else {
                plateformName = SinaWeibo.NAME;
                shareParams = new SinaWeibo.ShareParams();
                shareParams.setTitle(title);
                shareParams.setText(desc + " "+siteUrl + " ");
                shareParams.setImagePath(shareInfoModel.imgPath);
            }
        }

        final Platform platform = ShareSDK.getPlatform(plateformName);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                if(listener != null) {
                    listener.onComplete();
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                if(listener != null) {
                    if (throwable instanceof WechatClientNotExistException) {
                        listener.onUnInstallApk();
                    } else {
                        listener.onError();
                    }
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {
                if(listener != null) {
                    listener.onCancel();
                }
            }
        });

        platform.share(shareParams);
    }

}
