package com.jkdys.doctor.ui.search;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.Physicianstitle;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SearchDepartmentPresenter extends BaseSearchPresenter<SearchView> {

    @Inject
    DaYiShiServiceApi api;

    @Inject
    public SearchDepartmentPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    @Override
    public void search(String text) {
        ifViewAttached(view -> view.showLoading(false));
        api.getPhysiciansTitleList().enqueue(new BaseCallback<BaseResponse<List<Physicianstitle>>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<List<Physicianstitle>> response) {
                List<SearchData> searchDatas = new ArrayList<>();
                for (Physicianstitle physicianstitle:response.getData()) {
                    SearchData searchData = new SearchData();
                    searchData.setId(physicianstitle.getPid());
                    searchData.setText(physicianstitle.getPhysicianstitle());
                    searchDatas.add(searchData);
                }
                onSearchDataReturn(searchDatas);
            }
        });
    }
}
