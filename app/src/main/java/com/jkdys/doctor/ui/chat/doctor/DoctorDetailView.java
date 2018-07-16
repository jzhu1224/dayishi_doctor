package com.jkdys.doctor.ui.chat.doctor;

import com.jkdys.doctor.data.model.DoctorDetailData;
import com.jkdys.doctor.ui.BaseView;

public interface DoctorDetailView extends BaseView {
    void onRequestSuccess(DoctorDetailData doctorDetailData);
    void onAddFriendSuccess();
}
