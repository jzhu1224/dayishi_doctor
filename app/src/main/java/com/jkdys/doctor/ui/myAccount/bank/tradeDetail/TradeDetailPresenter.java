package com.jkdys.doctor.ui.myAccount.bank.tradeDetail;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.TradeDetailData;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.HashMap;

import javax.inject.Inject;

public class TradeDetailPresenter extends MvpBasePresenter<TradeDetailView> {

    DaYiShiServiceApi api;

    @Inject
    public TradeDetailPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void getTradeDetail(String rId, String type) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("rid", rId);
        params.put("type", type);
        ifViewAttached(view -> view.showLoading(false));
        api.getTradeRecordDetail(params).enqueue(new BaseCallback<BaseResponse<TradeDetailData>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<TradeDetailData> response) {
                ifViewAttached(view -> view.onRequestSuccess(response.getData()));
            }
        });
    }
}
