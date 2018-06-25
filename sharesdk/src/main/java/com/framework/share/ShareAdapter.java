package com.framework.share;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * Created by zhengw on 2016/5/25.
 */
public class ShareAdapter extends BaseAdapter {

    private Context mContext;
//    private String[] mPlateformNames;
//    private int[] mPlateformIcons;
    private boolean mIsInstallMM;
    private List<ShareInfoModel.SharePlatformModel> mSharePlatformModels;
//    private boolean mIsInstallQQ;

//    public static final int[] PLATFORM_ICONS = new int[]{R.drawable.selector_wechat,
//            R.drawable.selector_wechatmoment};
//
//    public static final String[] PLATFORM_NAMES = new String[]{Wechat.NAME, WechatMoments.NAME};

    public ShareAdapter(Context context) {
        this.mContext = context;
        mIsInstallMM = ApkUtils.isInstalledApk(mContext, "com.tencent.mm");
//        mIsInstallQQ = ApkUtils.isInstalledApk(mContext, "com.tencent.mobileqq");
    }

    public void setData(List<ShareInfoModel.SharePlatformModel> shareInfoModels){
        mSharePlatformModels = shareInfoModels;
        notifyDataSetChanged();
    }

    /***
     * 返回1表示微信没有安装
     * 返回2表示qq没有安装
     * @param position
     * @return
     */
    public int canClick(int position){
        ShareInfoModel.SharePlatformModel model = getItem(position);
        if(model == null){
            return 0;
        }
        if(TextUtils.equals(model.platformName, Wechat.NAME)
                || TextUtils.equals(model.platformName, WechatMoments.NAME)){
            return mIsInstallMM ? 0:1;
        }/*else if(TextUtils.equals(platName,QZone.NAME)){
            return mIsInstallQQ ? 0:2;
        }*/
        return 0;
    }

    @Override
    public int getCount() {
        return mSharePlatformModels == null ? 0 : mSharePlatformModels.size();
    }

    @Override
    public ShareInfoModel.SharePlatformModel getItem(int position) {
        return mSharePlatformModels == null ? null : mSharePlatformModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_share,null);
            viewHolder.mIvSharePlatformIcon = convertView.findViewById(R.id.iv_share_platform_icon);
            viewHolder.mTvShareName = convertView.findViewById(R.id.tv_share_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        ShareInfoModel.SharePlatformModel model = getItem(position);
        if(model == null){
            return convertView;
        }
        viewHolder.mIvSharePlatformIcon.setBackgroundResource(model.platformIconId);
        viewHolder.mTvShareName.setText(model.platformTitle);

        if((TextUtils.equals(model.platformName, Wechat.NAME)
                || TextUtils.equals(model.platformName, WechatMoments.NAME)) && !mIsInstallMM){
            viewHolder.mIvSharePlatformIcon.setEnabled(false);
        }else{
            viewHolder.mIvSharePlatformIcon.setEnabled(true);
        }

        return convertView;
    }

    public class ViewHolder {
        public ImageView mIvSharePlatformIcon;
        public TextView mTvShareName;

    }
}
