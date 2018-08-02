package com.jkdys.doctor.ui.chat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.data.model.MyFriendData;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMoreFrament;

import java.util.List;

import javax.inject.Inject;

public class MyFriendsFragment extends BaseRefreshLoadMoreFrament<MyFriendData, BaseLoadMoreView<MyFriendData>, MyFriendsPresenter> {

    @Inject
    MyFriendsPresenter presenter;

    @Override
    protected BaseQuickAdapter<MyFriendData, BaseViewHolder> createAdapter(List<MyFriendData> mDatas) {
        return new MyFriendsAdapter(mDatas);
    }

    @NonNull
    @Override
    public MyFriendsPresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }

    @Override
    protected void onItemClicked(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClicked(adapter, view, position);
        if (null != listener)
            listener.onItemClick((MyFriendData) adapter.getItem(position));
    }

    private OnFriendsItemClickListener listener;

    public void setOnFriendsItemClickListener(OnFriendsItemClickListener listener) {
        this.listener = listener;
    }


    public interface OnFriendsItemClickListener {
        void onItemClick(MyFriendData myFriendData);
    }

    class MyFriendsAdapter extends BaseQuickAdapter<MyFriendData, BaseViewHolder> {

        public MyFriendsAdapter(@Nullable List<MyFriendData> data) {
            super(R.layout.item_search_doctor,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MyFriendData item) {
            helper.setText(R.id.tv_name, item.getName());
            helper.setText(R.id.tv_desc, item.getHospitalname()+" / "+item.getFacultyname()+" / "+item.getTitlename());
            ImageLoader.with(getRecyclerView().getContext()).load(item.getPicheadurl()).placeholder(R.drawable.img_doctor).into(helper.getView(R.id.img_header));
        }
    }
}
