package com.jkdys.doctor.ui.consult.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.data.model.OrderInfo;
import com.jkdys.doctor.ui.BaseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends BaseQuickAdapter<OrderInfo,BaseViewHolder>{


    public OrderAdapter(@Nullable List<OrderInfo> data) {
        super(R.layout.layout_card_phone, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfo item) {
        helper.setText(R.id.tv_name, item.getPatientname());
        helper.setText(R.id.tv_time, item.getOrderdate());
        helper.setText(R.id.tv_price, "￥"+ item.getAmount());
        helper.setVisible(R.id.img_vip, item.isIsvip());
        if (item.isIsvip()) {
            helper.setBackgroundRes(R.id.ll_container, R.drawable.bg_card_vip);
        } else {
            helper.setBackgroundRes(R.id.ll_container, R.drawable.bg_card);
        }
        helper.setVisible(R.id.img_phone, item.getOrdertype().equals("1"));
        helper.setVisible(R.id.tv_price, item.getOrdertype().equals("1"));

        ImageLoader.with(helper.itemView.getContext())
                .placeholder(R.drawable.img_doctor)
                .load(item.getPicheadurl())
                .into(helper.getView(R.id.img_avatar));

    }
}
