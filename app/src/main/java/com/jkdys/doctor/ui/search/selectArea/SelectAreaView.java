package com.jkdys.doctor.ui.search.selectArea;

import com.jkdys.doctor.data.model.CityData;
import com.jkdys.doctor.data.model.ProvinceData;
import com.jkdys.doctor.ui.BaseView;

import java.util.List;

public interface SelectAreaView extends BaseView {
    void onRequestProvinceSuccess(List<ProvinceData> provinceDataList);
    void onRequestCitySuccess(List<CityData> cityDataList);
}
