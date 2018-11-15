package com.jkdys.doctor.ui.consult;


import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.OrderInfo;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.event.UpdateTabCountEvent;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMorePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class OrderPresenter extends BaseRefreshLoadMorePresenter<BaseLoadMoreView<OrderInfo>,OrderInfo> {

    private int page = 1;
    private int totalPage = 1;
    private int pageSize = 20;

    private String orderstate;
    private String ordertype;//1 电话订单 2 门诊订单

    @Inject
    DaYiShiServiceApi api;

    @Inject
    public OrderPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void setParams(String orderstate, String ordertype) {
        this.orderstate = orderstate;
        this.ordertype = ordertype;
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
        params.put("orderstate", orderstate);
        params.put("ordertype", ordertype);

        if (isViewAttached())
        api.getDoctorOrderInfo(params).enqueue(new BaseCallback<BaseResponse<List<OrderInfo>>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<List<OrderInfo>> response) {

                totalPage = response.getTotalPage();

                if (page == 1) {
                    ifViewAttached(view -> {
                        view.setData(response.getData());
                        view.showContent();
                        if (orderstate.equals("0") && ordertype.equals("1")){
                            //电话诊断订单
                            EventBus.getDefault().post(new UpdateTabCountEvent(0,response.getTotalrecord()));
                        }
                        if (orderstate.equals("0") && ordertype.equals("2")){
                            //门诊订单
                            EventBus.getDefault().post(new UpdateTabCountEvent(1,response.getTotalrecord()));
                        }
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
