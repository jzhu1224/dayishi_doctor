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

                List<MyPatientSection> myPatientSectionList = new ArrayList<>();

                int i = 0;
                if (response.getData() != null) {
                    for (PatientGroup patienGroup:response.getData()) {
                        MyPatientSection myPatientSection = new MyPatientSection(true,patienGroup.getGroupname());
                        myPatientSectionList.add(myPatientSection);
                        if (patienGroup.getDetail() != null) {
                            for (PatientInfo patienInfo: patienGroup.getDetail()) {
                                myPatientSection = new MyPatientSection(patienInfo);
                                myPatientSectionList.add(myPatientSection);
                            }
                        }

                        if (i == 0) {
                            PatientInfo patientInfo = new PatientInfo();
                            patientInfo.setAge("39");
                            patientInfo.setGender("男");
                            patientInfo.setPatientname("宝强");
                            myPatientSectionList.add(new MyPatientSection(patientInfo));
                        }

                        if (i == 1) {
                            PatientInfo patientInfo = new PatientInfo();
                            patientInfo.setAge("36");
                            patientInfo.setGender("女");
                            patientInfo.setPatientname("沈月");
                            myPatientSectionList.add(new MyPatientSection(patientInfo));
                        }

                        i++;
                    }
                }
                ifViewAttached(view -> view.setData(myPatientSectionList));
            }
        });
    }
}
