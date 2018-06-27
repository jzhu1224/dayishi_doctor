package com.jkdys.doctor.ui.search.selectArea;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.CityData;
import com.jkdys.doctor.data.model.ProvinceData;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.search.searchHospital.SearchHospitalActivity;
import com.jkdys.doctor.ui.search.selectArea.adapter.CityListAdapter;
import com.jkdys.doctor.ui.search.selectArea.adapter.ProvinceListAdapter;
import com.jkdys.doctor.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.zhouzhuo.zzsecondarylinkage.ZzSecondaryLinkage;
import me.zhouzhuo.zzsecondarylinkage.model.ILinkage;

public class SelectAreaActivity extends MvpActivity<SelectAreaView, SelectAreaPresenter> implements SelectAreaView {

    @Inject
    SelectAreaPresenter selectAreaPresenter;

    @BindView(R.id.zz_linkage)
    ZzSecondaryLinkage zzSecondaryLinkage;

    List<ProvinceData> mProvinceDataList;
    List<CityData> mCityDataList;

    ProvinceListAdapter provinceListAdapter;
    CityListAdapter cityListAdapter;

    private int currentProvincePosition;

    @NonNull
    @Override
    public SelectAreaPresenter createPresenter() {
        getActivityComponent().inject(this);
        return selectAreaPresenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("请选择区域");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
        //ViewUtil.scaleContentView((ViewGroup) findViewById(R.id.act_main_root_layout));

        mProvinceDataList = new ArrayList<>();
        mCityDataList = new ArrayList<>();

        provinceListAdapter = new ProvinceListAdapter(this, mProvinceDataList);
        cityListAdapter = new CityListAdapter(this, mCityDataList);

        zzSecondaryLinkage.setLeftMenuAdapter(provinceListAdapter);
        zzSecondaryLinkage.setRightContentAdapter(cityListAdapter);

        zzSecondaryLinkage.setOnItemClickListener(new ILinkage.OnItemClickListener() {
            @Override
            public void onLeftClick(View itemView, int position) {

                currentProvincePosition = position;

                if (position == 0) {
                    //选择全国
                    cityListAdapter.setList(new ArrayList<>());
                    cityListAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(mActivity, SearchHospitalActivity.class);
                    intent.putExtra(SearchHospitalActivity.KEY_AREA_NAME,"全国");
                    startActivityForResult(intent, 0x1);
                } else {
                    selectAreaPresenter.requestCityData(((ProvinceData)provinceListAdapter.getItem(position)).getId());
                }
            }

            @Override
            public void onRightClick(View itemView, int position) {
                Intent intent = new Intent(mActivity, SearchHospitalActivity.class);
                ProvinceData provinceData = (ProvinceData) provinceListAdapter.getItem(currentProvincePosition);
                if (position == 0) {
                    intent.putExtra(SearchHospitalActivity.KEY_AREA_NAME, provinceData.getName());
                    intent.putExtra(SearchHospitalActivity.KEY_PROVINCE_ID, provinceData.getId());
                } else {
                    CityData cityData = cityListAdapter.getItem(position);
                    intent.putExtra(SearchHospitalActivity.KEY_AREA_NAME, cityData.getName());
                    intent.putExtra(SearchHospitalActivity.KEY_CITY_ID, cityData.getId());
                    intent.putExtra(SearchHospitalActivity.KEY_PROVINCE_ID, provinceData.getId());
                }
                startActivityForResult(intent, 0x2);
            }
        });
    }

    @Override
    protected void afterMvpDelegateCreateInvoked() {
        selectAreaPresenter.requestProvinceData();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_select_area;
    }

    @Override
    public void onRequestProvinceSuccess(List<ProvinceData> provinceDataList) {
        provinceListAdapter.setList(provinceDataList);
    }

    @Override
    public void onRequestCitySuccess(List<CityData> cityDataList) {
        cityListAdapter.setList(cityDataList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
