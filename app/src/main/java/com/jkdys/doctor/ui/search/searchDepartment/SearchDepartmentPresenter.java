package com.jkdys.doctor.ui.search.searchDepartment;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.GroupDepartmentData;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;

public class SearchDepartmentPresenter extends MvpBasePresenter<SelectDepartmentView> {

    @Inject
    DaYiShiServiceApi api;

    @Inject
    public SearchDepartmentPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void getData(String hospitalId) {
        HashMap<String,Object> map = new HashMap<>();
        ifViewAttached(view -> view.showLoading(false));
        map.put("hospital_id", hospitalId);
        api.getFacultyInfoListByHospitalId(map).enqueue(new BaseCallback<BaseResponse<List<GroupDepartmentData>>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<List<GroupDepartmentData>> response) {
               ifViewAttached(view -> view.onDataReturn(response.getData()));
            }
        });
    }
}
