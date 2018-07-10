package com.jkdys.doctor.ui.chat.doctor.search;

import android.text.TextUtils;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.SearchDoctorData;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseSearchLoadMorePresenter;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class SearchDoctorPresenter extends BaseSearchLoadMorePresenter<SearchDoctorData,SearchDoctorView> {

    DaYiShiServiceApi api;

    private String keywords;
    private int page = 1;
    private int totalPage = 1;
    private int pageSize = 20;

    @Inject
    public SearchDoctorPresenter(DaYiShiServiceApi daYiShiServiceApi) {
        this.api = daYiShiServiceApi;
    }

    @Override
    public void search(String keywords) {
        this.keywords = keywords;
        page = 1;
        if (TextUtils.isEmpty(keywords)) {
            return;
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("keyname", keywords);
        params.put("pageindex", page);
        params.put("pagesize", pageSize);

        ifViewAttached(view -> view.showLoading(false));

        api.searchDoctor(params).enqueue(new BaseCallback<BaseResponse<List<SearchDoctorData>>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<List<SearchDoctorData>> response) {
                ifViewAttached(view -> {
                    view.setData(response.getData());
                    view.showContent();
                });
            }
        });
    }

    @Override
    public void loadMore(boolean pullToRefresh) {
        if (pullToRefresh) {
            page = 1;
        }
        ifViewAttached(view -> view.showLoading(false));
        HashMap<String, Object> params = new HashMap<>();
        params.put("keyname", keywords);
        params.put("pageindex", page);
        params.put("pagesize", pageSize);
        api.searchDoctor(params).enqueue(new BaseCallback<BaseResponse<List<SearchDoctorData>>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<List<SearchDoctorData>> response) {
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
