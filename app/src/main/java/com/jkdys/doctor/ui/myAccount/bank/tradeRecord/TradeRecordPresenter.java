package com.jkdys.doctor.ui.myAccount.bank.tradeRecord;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.TradeRecord;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMorePresenter;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;

public class TradeRecordPresenter extends BaseRefreshLoadMorePresenter<BaseLoadMoreView<TradeRecord>, TradeRecord> {


    private int page = 1;
    private int totalPage = 1;
    private int pageSize = 20;

    DaYiShiServiceApi api;

    @Inject
    public TradeRecordPresenter(DaYiShiServiceApi api) {
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
        if (isViewAttached())
            api.getTradeRecord(params).enqueue(new BaseCallback<BaseResponse<List<TradeRecord>>>(getView()) {
                @Override
                public void onBusinessSuccess(BaseResponse<List<TradeRecord>> response) {

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
