package com.jkdys.doctor.ui.customer;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.PatientGroup;
import com.jkdys.doctor.data.model.PatientInfo;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMorePresenter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

public class CustomerPresenter extends BaseRefreshLoadMorePresenter<BaseLoadMoreView<MyPatientSection>,MyPatientSection> {

    DaYiShiServiceApi api;

    @Inject
    public CustomerPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    @Override
    public void loadMore(boolean pullToRefresh) {
        ifViewAttached(view -> view.showLoading(pullToRefresh));
        api.getMyPatientList().enqueue(new BaseCallback<BaseResponse<List<PatientGroup>>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<List<PatientGroup>> response) {

                LinkedList<MyPatientSection> myPatientSectionList = new LinkedList<>();

                if (response.getData() != null) {
                    for (PatientGroup patienGroup:response.getData()) {
                        MyPatientSection myPatientSection = new MyPatientSection(true,patienGroup.getGroupname());
                        myPatientSectionList.addFirst(myPatientSection);
                        if (patienGroup.getDetail() != null) {
                            for (PatientInfo patienInfo: patienGroup.getDetail()) {
                                myPatientSection = new MyPatientSection(patienInfo);
                                myPatientSectionList.add(myPatientSection);
                            }
                        }
                    }
                }
                ifViewAttached(view -> view.setData(myPatientSectionList));
            }
        });
    }
}
