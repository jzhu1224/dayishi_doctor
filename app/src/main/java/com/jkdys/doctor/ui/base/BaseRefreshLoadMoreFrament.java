package com.jkdys.doctor.ui.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chairoad.framework.util.LogUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.MvpFragment;
import com.jkdys.doctor.widget.MyQMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public abstract class BaseRefreshLoadMoreFrament<T extends Parcelable,V extends BaseLoadMoreView<T>, P extends BaseRefreshLoadMorePresenter<V,T>> extends MvpFragment<V,P>
        implements SwipeRefreshLayout.OnRefreshListener, BaseLoadMoreView<T>{

    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    protected SwipeRefreshLayout swipeRefreshLayout;

    protected BaseQuickAdapter<T,BaseViewHolder> adapter;
    protected ArrayList<T> mDatas;

    private MyQMUIEmptyView qmuiEmptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

        if (enableRefresh()) {
            swipeRefreshLayout.setOnRefreshListener(this);
        }


        if (savedInstanceState == null) {
            mDatas = new ArrayList<>();
        } else {
            mDatas = savedInstanceState.getParcelableArrayList("mDatas");
        }

        adapter = createAdapter(mDatas);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (enableLoadMore()) {
            adapter.setOnLoadMoreListener(() -> loadData(false, false), recyclerView);
        } else {
            adapter.setEnableLoadMore(false);
        }

        adapter.setOnItemClickListener(this::onItemClicked);

        qmuiEmptyView = new MyQMUIEmptyView(getActivity());
        adapter.setEmptyView(qmuiEmptyView);

        if (savedInstanceState != null) {
            showContent();
        }
    }

    protected void onItemClicked(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    protected void onLazyLoadOnce() {
        loadData(true, true);
    }

    protected boolean enableLoadMore() {
        return true;
    }

    protected boolean enableRefresh() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_refresh_loadmore;
    }

    @Override
    public void onRefresh() {
        loadData(true, true);
    }

    protected abstract BaseQuickAdapter<T,BaseViewHolder> createAdapter(List<T> mDatas);

    @Override
    public void setData(List<T> data) {
        adapter.setNewData(data);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mDatas", (ArrayList<? extends Parcelable>) adapter.getData());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void loadData(boolean pullToRefresh, boolean cleanCache) {
        getPresenter().loadMore(pullToRefresh);
    }

    @Override
    public void noMoreData() {
        adapter.loadMoreEnd(true);
    }

    @Override
    public void loadMoreFailed() {
        adapter.loadMoreFail();
    }

    @Override
    public void setMoreData(List<T> moreData) {
        adapter.loadMoreComplete();
        adapter.addData(moreData);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        swipeRefreshLayout.setRefreshing(pullToRefresh);
        //qmuiEmptyView.show(true);
    }

    @Override
    public void showContent() {
        swipeRefreshLayout.setRefreshing(false);
        qmuiEmptyView.show(false);
        //qmuiEmptyView.setTitleText("暂无数据");
        qmuiEmptyView.setDetailText("");
        qmuiEmptyView.setImageEmpty(R.drawable.img_empty);
    }

    @Override
    public void showError(String message) {
        swipeRefreshLayout.setRefreshing(false);
        //showMessage(message);
        qmuiEmptyView.setImageEmpty(R.drawable.img_load_error);
       // qmuiEmptyView.setTitleText("");
        qmuiEmptyView.setDetailText(message);
        qmuiEmptyView.setButton("重新加载", view -> loadData(true,true));
    }
}
