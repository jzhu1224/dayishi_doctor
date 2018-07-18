package com.jkdys.doctor.ui.consult.diagnosis;

import com.jkdys.doctor.data.model.Face2FaceOrderDetail;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMorePresenter;
import javax.inject.Inject;

public class DelayRecordPresenter extends BaseRefreshLoadMorePresenter<BaseLoadMoreView<Face2FaceOrderDetail.DelayRecord>, Face2FaceOrderDetail.DelayRecord> {

    @Inject
    public DelayRecordPresenter() {

    }

    @Override
    public void loadMore(boolean pullToRefresh) {

    }
}
