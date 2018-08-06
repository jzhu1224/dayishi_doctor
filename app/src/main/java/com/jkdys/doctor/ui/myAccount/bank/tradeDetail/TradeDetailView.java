package com.jkdys.doctor.ui.myAccount.bank.tradeDetail;

import com.jkdys.doctor.data.model.TradeDetailData;
import com.jkdys.doctor.ui.BaseView;

public interface TradeDetailView extends BaseView{
    void onRequestSuccess(TradeDetailData data);
}
