package com.jkdys.doctor.ui.search.searchHospital;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.Physicianstitle;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.ui.search.BaseSearchPresenter;
import com.jkdys.doctor.ui.search.SearchData;
import com.jkdys.doctor.ui.search.SearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SearchHospitalPresenter extends BaseSearchPresenter<SearchView> {

    @Inject
    DaYiShiServiceApi api;

    @Inject
    public SearchHospitalPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    @Override
    public void search(String... text) {
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
