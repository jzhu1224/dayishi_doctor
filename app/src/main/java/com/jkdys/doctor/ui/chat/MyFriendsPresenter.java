package com.jkdys.doctor.ui.chat;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.MyFriendData;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMorePresenter;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class MyFriendsPresenter extends BaseRefreshLoadMorePresenter<BaseLoadMoreView<MyFriendData>, MyFriendData> {

    DaYiShiServiceApi api;

    private int page = 1;
    private int totalPage = 1;
    private int pageSize = 20;

    @Inject
    public MyFriendsPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    @Override
    public void loadMore(boolean pullToRefresh) {

        if (pullToRefresh) {
            page = 1;
        }
        ifViewAttached(view -> view.showLoading(pullToRefresh));
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageindex", page);
        params.put("pagesize", pageSize);
        api.myFriends(params).enqueue(new BaseCallback<BaseResponse<List<MyFriendData>>>() {
            @Override
            public void onBusinessSuccess(BaseResponse<List<MyFriendData>> response) {
                totalPage = response.getTotalPage();

                if (page == 1) {
                    ifViewAttached(view -> {
                        view.setData(response.getData());
                        view.showContent();
                    });
                } else {
                    ifViewAttached(view -> view.setMoreData(response.getData()));
                }
                //已经最后一页了
                if (page >= totalPage) {
                    ifViewAttached(BaseLoadMoreView::noMoreData);
                    //view.noMoreData();
                } else {
                    page++;
                }
            }
        });
    }
}
