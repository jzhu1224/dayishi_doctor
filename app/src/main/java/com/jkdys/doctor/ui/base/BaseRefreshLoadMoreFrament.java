package com.jkdys.doctor.ui.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.MvpFragment;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public abstract class BaseRefreshLoadMoreFrament<T,V extends BaseLoadMoreView<T>, P extends BaseRefreshLoadMorePresenter<V,T>> extends MvpFragment<V,P>
        implements SwipeRefreshLayout.OnRefreshListener, BaseLoadMoreView<T>{

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    protected BaseQuickAdapter<T,BaseViewHolder> adapter;
    protected List<T> mDatas;

    @Override
    protected void initViews(View view) {

        swipeRefreshLayout.setOnRefreshListener(this);

        mDatas = new ArrayList<>();

        adapter = createAdapter(mDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnLoadMoreListener(() -> loadData(false, false), recyclerView);
        recyclerView.setAdapter(adapter);
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
    protected void onLazyLoadOnce() {
        loadData(true, true);
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
    }

    @Override
    public void showContent() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        swipeRefreshLayout.setRefreshing(false);
        showMessage(message);
    }
}
