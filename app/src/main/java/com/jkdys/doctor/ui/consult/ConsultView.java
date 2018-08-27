package com.jkdys.doctor.ui.consult;

import com.jkdys.doctor.data.model.ShareData;
import com.jkdys.doctor.ui.BaseView;

public interface ConsultView extends BaseView{
    void onGetShareDataSuccess(ShareData shareData);
}
