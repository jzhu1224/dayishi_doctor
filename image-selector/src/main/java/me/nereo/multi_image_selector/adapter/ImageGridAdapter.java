package me.nereo.multi_image_selector.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorFragment;
import me.nereo.multi_image_selector.R;
import me.nereo.multi_image_selector.bean.Image;

/**
 * 图片Adapter
 * Created by Nereo on 2015/4/7.
 * Updated by nereo on 2016/1/19.
 */
public class ImageGridAdapter extends BaseAdapter {

    private static final int TYPE_CAMERA = 0;
    private static final int TYPE_NORMAL = 1;

    private Context mContext;

    private LayoutInflater mInflater;
    private boolean showCamera = true;
    private boolean showSelectIndicator = true;

    private List<Image> mImages = new ArrayList<>();
    private List<Image> mSelectedImages = new ArrayList<>();

    final int mGridWidth;
    private IndicatorCheckListener mIndicatorCheckListener;
    private int mMaxCount;
    private boolean mUseDisableView;
    private int mCheckedResId;

    public ImageGridAdapter(Context context, boolean showCamera, int column,int maxCount,boolean useDisableView,int checkedResId){
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.showCamera = showCamera;
        mMaxCount = maxCount;
        mUseDisableView = useDisableView;
        mCheckedResId = checkedResId;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            width = size.x;
        }else{
            width = wm.getDefaultDisplay().getWidth();
        }
        mGridWidth = width / column;
    }

    public void setIndicatorCheckListener(IndicatorCheckListener listener){
        mIndicatorCheckListener = listener;
    }
    /**
     * 显示选择指示器
     * @param b
     */
    public void showSelectIndicator(boolean b) {
        showSelectIndicator = b;
    }

    public void setShowCamera(boolean b){
        if(showCamera == b) return;

        showCamera = b;
        notifyDataSetChanged();
    }

    public boolean isShowCamera(){
        return showCamera;
    }

    public void setSelectedImages(List<String> selectedImages){
        mSelectedImages.clear();
        for(Image image : mImages){
            for(String selectPath : selectedImages){
                if(selectPath.contains(image.path)){
                    mSelectedImages.add(image);
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 选择某个图片，改变选择状态
     * @param image
     */
    public void select(Image image) {
        if(mSelectedImages.contains(image)){
            mSelectedImages.remove(image);
        }else{
            mSelectedImages.add(image);
        }
        notifyDataSetChanged();
    }

    /**
     * 通过图片路径设置默认选择
     * @param resultList
     */
    public void setDefaultSelected(ArrayList<String> resultList) {
        for(String path : resultList){
            Image image = getImageByPath(path);
            if(image != null){
                mSelectedImages.add(image);
            }
        }
        if(mSelectedImages.size() > 0){
            notifyDataSetChanged();
        }
    }

    private Image getImageByPath(String path){
        if(mImages != null && mImages.size()>0){
            for(Image image : mImages){
                if(image.path.equalsIgnoreCase(path)){
                    return image;
                }
            }
        }
        return null;
    }

    /**
     * 设置数据集
     * @param images
     */
    public void setData(List<Image> images) {
        mSelectedImages.clear();

        if(images != null && images.size()>0){
            mImages = images;
        }else{
            mImages.clear();
        }
        notifyDataSetChanged();
    }

    public List<Image> getData(){
        return mImages;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(showCamera){
            return position==0 ? TYPE_CAMERA : TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getCount() {
        return showCamera ? mImages.size()+1 : mImages.size();
    }

    @Override
    public Image getItem(int i) {
        if(showCamera){
            if(i == 0){
                return null;
            }
            return mImages.get(i-1);
        }else{
            return mImages.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(isShowCamera()){
            if(i == 0){
                view = mInflater.inflate(R.layout.list_item_camera, viewGroup, false);
                return view;
            }
        }

        ViewHolder holder;
        if(view == null){
            view = mInflater.inflate(R.layout.list_item_image, viewGroup, false);
            holder = new ViewHolder(view);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        if(holder != null) {
            holder.bindData(getItem(i));
            holder.indicator.setTag(i);
        }

        return view;
    }

    public interface IndicatorCheckListener{
        void indicatorClick(int position);
    }

    class ViewHolder implements View.OnClickListener{
        ImageView image;
        ImageView indicator;
        View mask;

        ViewHolder(View view){
            image = (ImageView) view.findViewById(R.id.image);
            indicator = (ImageView) view.findViewById(R.id.checkmark);
            mask = view.findViewById(R.id.mask);
            view.setTag(this);
            indicator.setOnClickListener(this);
        }

        void bindData(final Image data){
            if(data == null) return;
            // 处理单选和多选状态
            if(showSelectIndicator){
                indicator.setVisibility(View.VISIBLE);
                if(mSelectedImages.contains(data)){
                    // 设置选中状态
                    indicator.setImageResource(mCheckedResId);
                    mask.setVisibility(View.VISIBLE);
                    mask.setBackgroundColor(0x88000000);
                }else{
                    // 未选择
                    indicator.setImageResource(R.drawable.uncheck_circle);
                    if(mUseDisableView && mMaxCount > 0 && mMaxCount == mSelectedImages.size()){
                        mask.setVisibility(View.VISIBLE);
                        mask.setBackgroundColor(0xccffffff);
                    }else{
                        mask.setVisibility(View.GONE);
                    }
                }
            }else{
                indicator.setVisibility(View.GONE);
            }

            File imageFile = new File(data.path);
            if (imageFile.exists()) {
                // 显示图片
                Picasso.get()
                        .load(imageFile)
                        .placeholder(R.drawable.default_error)
                        .config(Bitmap.Config.RGB_565)
                        .tag(MultiImageSelectorFragment.TAG)
                        .resize(mGridWidth, mGridWidth)
                        .centerCrop()
                        .into(image);
            }else{
                image.setImageResource(R.drawable.default_error);
            }
        }

        @Override
        public void onClick(View v) {
            int pos = (int)v.getTag();
            if(mIndicatorCheckListener != null){
                mIndicatorCheckListener.indicatorClick(pos);
            }
        }
    }
}
