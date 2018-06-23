package com.jkdys.doctor.ui.search;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.DepartmentData;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.ArrayList;
import java.util.HashMap;
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
    public void search(String... params) {
        HashMap<String,Object> map = new HashMap<>();
        ifViewAttached(view -> view.showLoading(false));
        map.put("hospital_id", params[0]);
        api.getFacultyInfoListByHospitalId(map).enqueue(new BaseCallback<BaseResponse<List<DepartmentData>>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<List<DepartmentData>> response) {
                List<SearchData> searchDatas = new ArrayList<>();
                for (DepartmentData departmentData:response.getData()) {
                    SearchData searchData = new SearchData();
                    searchData.setId(departmentData.getFid());
                    searchData.setText(departmentData.getFacultyname());
                    searchDatas.add(searchData);
                }
                onSearchDataReturn(searchDatas);
            }
        });
    }
}
