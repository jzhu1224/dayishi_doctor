package com.jkdys.doctor.ui.fee;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.DiagnosisFeeData;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import java.util.HashMap;

import javax.inject.Inject;

public class Face2FaceDiagnosisFeePresenter extends MvpBasePresenter<Face2FaceDiagnosieFeeView> {

    DaYiShiServiceApi api;

    @Inject
    public Face2FaceDiagnosisFeePresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void editPrice(String type, String price) {

        /**
         * "type": "2",  //1是电话普通费用，2是电话特需费用，3是门诊费用
         "price": "233,333,433"  //只有特需费用（type=2）以英文逗号相隔，其它两种都是数值型
         *
         */

        ifViewAttached(view -> view.showLoading(false));
        HashMap<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("price", price);

        api.modifyDiagnosisFee(params).enqueue(new BaseCallback<BaseResponse<Object>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<Object> response) {
                ifViewAttached(Face2FaceDiagnosieFeeView::onEditSuccess);
            }
        });
    }

    public void getPrice(String type) {
        //"type": "1"   //1是电话普通费用，2是电话特需费用，3是门诊费用
        ifViewAttached(view -> view.showLoading(false));
        HashMap<String, Object> params = new HashMap<>();
        params.put("type", type);
        api.diagnosisFeeDetail(params).enqueue(new BaseCallback<BaseResponse<DiagnosisFeeData>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<DiagnosisFeeData> response) {
                ifViewAttached(view -> view.onRequestPriceSuccess(response.getData().getPrice()));
            }
        });
    }
}
