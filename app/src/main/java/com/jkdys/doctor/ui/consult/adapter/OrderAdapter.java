package com.jkdys.doctor.ui.consult.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.OrderInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends BaseQuickAdapter<OrderInfo,OrderAdapter.OrderViewHolder>{


    public OrderAdapter(@Nullable List<OrderInfo> data) {
        super(R.layout.layout_card_phone, data);
    }

    @Override
    protected void convert(OrderViewHolder helper, OrderInfo item) {
        helper.tvName.setText(item.getPatientname());
        helper.tvTime.setText(item.getOrderdate());
        helper.tvPrice.setText("￥"+item.getAmount());
        helper.imgVip.setVisibility(item.isIsvip()?View.VISIBLE:View.GONE);
        if (item.isIsvip()) {
            helper.container.setBackgroundResource(R.drawable.bg_card_vip);
        } else {
            helper.container.setBackgroundResource(R.drawable.bg_card);
        }

        helper.imgPhone.setVisibility(item.getOrdertype().equals("1")?View.VISIBLE:View.GONE);
        helper.tvPrice.setVisibility(item.getOrdertype().equals("1")?View.VISIBLE:View.GONE);
    }

    public static class OrderViewHolder extends BaseViewHolder{

        @BindView(R.id.ll_container)
        View container;
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.img_vip)
        ImageView imgVip;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.img_phone)
        ImageView imgPhone;

        public OrderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
