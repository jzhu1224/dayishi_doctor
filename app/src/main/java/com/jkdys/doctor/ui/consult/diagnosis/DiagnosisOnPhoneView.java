package com.jkdys.doctor.ui.consult.diagnosis;

import com.jkdys.doctor.data.model.PhoneNumberDetail;
import com.jkdys.doctor.data.model.PhoneOrderDetail;
import com.jkdys.doctor.ui.BaseView;

public interface DiagnosisOnPhoneView extends BaseView {
    void onRequestSuccess(PhoneOrderDetail phoneOrderDetail);
    void onRequestVirtualNumberSuccess(PhoneNumberDetail phoneNumberDetail);
    void onCancelCallSuccess();
}
