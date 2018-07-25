package com.jkdys.doctor.ui.fee;

import com.jkdys.doctor.ui.BaseView;

import java.util.List;

public interface Face2FaceDiagnosieFeeView extends BaseView {
    void onEditSuccess();
    void onRequestPriceSuccess(List<String> price);
}
