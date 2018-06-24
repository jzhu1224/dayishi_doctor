package com.jkdys.doctor.ui.search.selectArea;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.CityData;
import com.jkdys.doctor.data.model.ProvinceData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SelectAreaPresenter extends MvpBasePresenter<SelectAreaView> {
    @Inject
    public SelectAreaPresenter() {

    }

    public void requestProvinceData() {
        List<ProvinceData> list = new ArrayList<>();
        ProvinceData provinceData = new ProvinceData();
        provinceData.setName("上海市");
        provinceData.setId("1");
        list.add(provinceData);
        provinceData = new ProvinceData();
        provinceData.setName("北京市");
        provinceData.setId("2");
        list.add(provinceData);
        provinceData = new ProvinceData();
        provinceData.setName("江苏省");
        provinceData.setId("3");
        list.add(provinceData);
        ifViewAttached(view -> view.onRequestProvinceSuccess(list));
    }

    public void requestCityData(String id) {
        List<CityData> list = new ArrayList<>();
        if (id.equals("1")) {
            CityData cityData = new CityData();
            cityData.setParentId("1");
            cityData.setName("黄浦区");
            list.add(cityData);
            cityData = new CityData();
            cityData.setParentId("1");
            cityData.setName("普陀区");
            list.add(cityData);
            cityData = new CityData();
            cityData.setParentId("1");
            cityData.setName("青浦区");
            list.add(cityData);
        } else if (id.equals("2")) {
            CityData cityData = new CityData();
            cityData.setParentId("2");
            cityData.setName("海淀区");
            list.add(cityData);
            cityData = new CityData();
            cityData.setParentId("2");
            cityData.setName("朝阳区");
            list.add(cityData);
            cityData = new CityData();
            cityData.setParentId("2");
            cityData.setName("通州");
            list.add(cityData);
        } else {
            CityData cityData = new CityData();
            cityData.setParentId("3");
            cityData.setName("南京市");
            list.add(cityData);
            cityData = new CityData();
            cityData.setParentId("3");
            cityData.setName("苏州市");
            list.add(cityData);
            cityData = new CityData();
            cityData.setParentId("3");
            cityData.setName("常州市");
            list.add(cityData);
        }
        ifViewAttached(view -> view.onRequestCitySuccess(list));
    }
}
