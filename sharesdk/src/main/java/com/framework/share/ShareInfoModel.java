package com.framework.share;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by jeme on 2017/12/29.
 */

public class ShareInfoModel {

    public String title;
    public String imgUrl;
    public String imgPath;
    public Bitmap imgData;
    public String desc;
    public String siteUrl;

    public List<SharePlatformModel> platforms;


    public static class SharePlatformModel{
        public String platformName;//各个平台的名称，固定，Wechat.NAME WechatMoments.NAME
        public String platformTitle;
        public int platformIconId;

        public SharePlatformModel(String platformName,String platformTitle, int platformIconId) {
            this.platformTitle = platformTitle;
            this.platformName = platformName;
            this.platformIconId = platformIconId;
        }
    }
}
