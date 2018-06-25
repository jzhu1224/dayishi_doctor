package com.jkdys.doctor.ui.chat;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanxin on 17/4/6.
 */

public abstract class AbstractAdapter<T> extends BaseAdapter {

    protected List<T> mData = new ArrayList<>();

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void setData(List<T> mList) {
        this.mData.clear();
        addData(mList);
    }

    public void addData(List<T> mList) {
        if(mList!=null) {
            this.mData.addAll(mList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}