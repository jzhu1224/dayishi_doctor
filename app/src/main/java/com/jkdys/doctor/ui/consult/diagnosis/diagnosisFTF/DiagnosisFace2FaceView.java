package com.jkdys.doctor.ui.consult.diagnosis.diagnosisFTF;

import com.jkdys.doctor.data.model.Face2FaceOrderDetail;
import com.jkdys.doctor.ui.BaseView;

public interface DiagnosisFace2FaceView extends BaseView {
    void onRequestSuccess(Face2FaceOrderDetail face2FaceOrderDetail);
    void onProcessSuccess(String type);
}
