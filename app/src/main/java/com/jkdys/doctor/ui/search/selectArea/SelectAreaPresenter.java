package com.jkdys.doctor.ui.search.selectArea;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.CityData;
import com.jkdys.doctor.data.model.ProvinceData;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class SelectAreaPresenter extends MvpBasePresenter<SelectAreaView> {

    @Inject
    DaYiShiServiceApi api;

    @Inject
    public SelectAreaPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void requestProvinceData() {


        ifViewAttached(view -> view.showLoading(false));

        api.getProvinceList().enqueue(new BaseCallback<BaseResponse<List<ProvinceData>>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<List<ProvinceData>> response) {
                List<ProvinceData> provinceDatas = new ArrayList<>();
                ProvinceData provinceData = new ProvinceData();
                provinceData.setId(null);
                provinceData.setName("全国");
                provinceDatas.add(provinceData);
                provinceDatas.addAll(response.getData());
                ifViewAttached(view -> view.onRequestProvinceSuccess(provinceDatas));
            }
        });
    }

    public void requestCityData(String id) {

        ifViewAttached(view -> view.showLoading(false));

        HashMap<String, Object> params = new HashMap<>();
        params.put("provinceid", id);

        api.getCityList(params).enqueue(new BaseCallback<BaseResponse<List<CityData>>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<List<CityData>> response) {

                List<CityData> cityDatas = new ArrayList<>();

                CityData cityData = new CityData();
                cityData.setId(null);
                cityData.setName("不限");

                cityDatas.add(cityData);
                cityDatas.addAll(response.getData());


                ifViewAttached(view -> view.onRequestCitySuccess(cityDatas));
            }
        });
    }
}
