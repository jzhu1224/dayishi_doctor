package com.framework.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by zhengw on 2016/5/25.
 */
public class SharePopupWindow extends PopupWindow implements View.OnClickListener {

    private Context mContext;
    private PlatformActionListener mPlatformActionListener;
    private View mRootView;
    private FrameLayout mTopView;
    public ShareAdapter mAdapter;
    private ShareInfoModel mShareInfoModel;
    private OnPlatformItemClick mPlatformItemClickListener;

    public SharePopupWindow(Context context, ShareInfoModel shareInfoModels) {
        if(shareInfoModels == null || shareInfoModels.platforms == null || shareInfoModels.platforms.size() == 0){
            dismiss();
        }
        this.mContext = context;
        mShareInfoModel = shareInfoModels;
        initPopData();
        initPopView();
    }

    private void initPopData() {
        /*mGridPlatformNames = mContext.getResources().getStringArray(R.array.share_platform_name);

        if (mTemplateShareInfoModel != null && mGoodsBaseModel != null) {
            mShareInfoModel.setG_id(mGoodsBaseModel.getG_id());
            mShareInfoModel.setTitle(mTemplateShareInfoModel.getTitle());
            mShareInfoModel.setPic(mGoodsBaseModel.getG_img());//如果图片不存在，微信和朋友无法分享
            mShareInfoModel.setDesc(ShareUtils.getTargetString(mTemplateShareInfoModel.getDesc(), mGoodsBaseModel));
            mShareInfoModel.setUrl_android(ShareUtils.getTargetString(mTemplateShareInfoModel.getUrl_android(), mGoodsBaseModel));
            mShareInfoModel.setmAgentCode(mTemplateShareInfoModel.getmAgentCode());
            if (null != mTemplateShareInfoModel.getmAgentValue()) {
                mShareInfoModel.setmAgentValue(mTemplateShareInfoModel.getmAgentValue());
                mAgentValue = mTemplateShareInfoModel.getmAgentValue();
            }
            mAgentValue = TextUtils.isEmpty(mAgentValue)?
                    (null==mShareInfoModel.getG_id()?"":mShareInfoModel.getG_id()):mAgentValue;
        }*/
    }

    private void initPopView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.pop_grid_share_layout, null);
        mTopView = mRootView.findViewById(R.id.fl_share_top);
        GridView gvShareContainer = mRootView.findViewById(R.id.gv_share_container);
        if(mShareInfoModel.platforms.size() >= 3){
            gvShareContainer.setNumColumns(3);
        }else{
            gvShareContainer.setNumColumns(mShareInfoModel.platforms.size());
        }
        mAdapter = new ShareAdapter(mContext);
        gvShareContainer.setAdapter(mAdapter);
        mAdapter.setData(mShareInfoModel.platforms);

        // 设置SharePopupWindow的View
        this.setContentView(mRootView);
        // 设置SharePopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SharePopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SharePopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 设置SharePopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.popwin_anim_style);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SharePopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        gvShareContainer.setOnItemClickListener(new ShareItemClickListener(this));
        mRootView.findViewById( R.id.tv_cancel_share).setOnClickListener(this);
        mRootView.findViewById( R.id.pop_black_view).setOnClickListener(this);

    }

    public void setPlatformActionListener(PlatformActionListener platformActionListener) {
        this.mPlatformActionListener = platformActionListener;
    }
    public void setPlatformItemListener(OnPlatformItemClick platformItemListener) {
        this.mPlatformItemClickListener = platformItemListener;
    }

    public void show(boolean show,boolean showTop) {

        show(show);
        mTopView.setVisibility(showTop?View.VISIBLE:View.GONE);
    }

    public void show(boolean show) {

        if (show) {
            if (mContext instanceof Activity) {
                Activity activity = (Activity) mContext;
                if (!activity.isFinishing()) {
                    showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                }
            }
        } else {
            dismiss();
        }
    }

    public void addTopView(View view){
        mTopView.addView(view);
    }

    @Override
    public void onClick(View v) {
        show(false);
    }

    private class ShareItemClickListener implements AdapterView.OnItemClickListener {
        private SharePopupWindow mPopWindow;

        public ShareItemClickListener(SharePopupWindow pop) {
            this.mPopWindow = pop;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            if(mPlatformItemClickListener != null){
                if(mPlatformItemClickListener.onPlatformClick(position,mShareInfoModel)){
                    return;
                }
            }
            switch (mAdapter.canClick(position)) {
                case 1:
                    Toast.makeText(mContext,"没有安装微信",Toast.LENGTH_SHORT).show();
                    return;
                case 2:
                    Toast.makeText(mContext,"没有安装QQ",Toast.LENGTH_SHORT).show();
                    return;
            }

            ShareUtils.sharePlatform(mContext, position, mShareInfoModel, new PlatformActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    Toast.makeText(mContext,"分享成功",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                    Toast.makeText(mContext,"分享失败",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    Toast.makeText(mContext,"取消分享",Toast.LENGTH_SHORT).show();
                }
            });
            mPopWindow.show(false);
        }
    }

    public interface OnPlatformItemClick{
        boolean onPlatformClick(int position, ShareInfoModel model);
    }

}
