package com.jkdys.doctor.ui.search.searchHospital;

import android.text.TextUtils;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.HospitalData;
import com.jkdys.doctor.data.model.Physicianstitle;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.ui.search.BaseSearchPresenter;
import com.jkdys.doctor.ui.search.SearchData;
import com.jkdys.doctor.ui.search.SearchView;

import java.util.ArrayList;
import java.util.HashMap;
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

        HashMap<String, Object> params = new HashMap<>();

        if (!TextUtils.isEmpty(text[0])) {
            params.put("provinceid", text[0]);
        }

        if (!TextUtils.isEmpty(text[1])) {
            params.put("cityid", text[1]);
        }

        if (!TextUtils.isEmpty(text[2])) {
            params.put("hospitalname", text[2]);
        }

        api.getHospitalList(params).enqueue(new BaseCallback<BaseResponse<List<HospitalData>>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<List<HospitalData>> response) {
                List<SearchData> searchDatas = new ArrayList<>();
                for (HospitalData hospitalData:response.getData()) {
                    SearchData searchData = new SearchData();
                    searchData.setId(hospitalData.getHid());
                    searchData.setText(hospitalData.getHospitalname());
                    searchDatas.add(searchData);
                }
                onSearchDataReturn(searchDatas);
            }
        });
    }
}
