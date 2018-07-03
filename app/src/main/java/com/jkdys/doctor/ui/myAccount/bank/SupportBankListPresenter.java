package com.jkdys.doctor.ui.myAccount.bank;

import com.jkdys.doctor.data.model.BankCardInfo;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMorePresenter;
import java.util.List;

import javax.inject.Inject;

public class SupportBankListPresenter extends BaseRefreshLoadMorePresenter<BaseLoadMoreView<BankCardInfo>,BankCardInfo> {

    DaYiShiServiceApi api;

    @Inject
    public SupportBankListPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    @Override
    public void loadMore(boolean pullToRefresh) {
        ifViewAttached(view -> view.showLoading(pullToRefresh));
        api.getSupportBankList().enqueue(new BaseCallback<BaseResponse<List<BankCardInfo>>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<List<BankCardInfo>> response) {
                ifViewAttached(view -> {
                    view.setData(response.getData());
                    view.showContent();
                });
            }
        });
    }
}
