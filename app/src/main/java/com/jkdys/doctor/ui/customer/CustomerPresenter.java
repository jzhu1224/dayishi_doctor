package com.jkdys.doctor.ui.customer;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.PatientGroup;
import com.jkdys.doctor.data.model.PatientInfo;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMorePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

public class CustomerPresenter extends BaseRefreshLoadMorePresenter<BaseLoadMoreView<PatientInfo>,PatientInfo> {

    private int page = 1;
    private int totalPage = 1;
    private int pageSize = 20;

    private int grouptype;//分组类型，1是特需患者（VIP），2是普通患者

    DaYiShiServiceApi api;

    @Inject
    public CustomerPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void setGrouptype(int grouptype) {
        this.grouptype = grouptype;
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
        params.put("grouptype", grouptype);

        api.getMyPatientList(params).enqueue(new BaseCallback<BaseResponse<List<PatientInfo>>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<List<PatientInfo>> response) {
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
