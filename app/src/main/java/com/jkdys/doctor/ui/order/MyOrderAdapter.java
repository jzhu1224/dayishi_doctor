package com.jkdys.doctor.ui.order;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.data.model.OrderInfo;

import java.util.List;

public class MyOrderAdapter extends BaseQuickAdapter<OrderInfo,BaseViewHolder>{


    public MyOrderAdapter(@Nullable List<OrderInfo> data) {
        super(R.layout.item_my_order, data);
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

        if (item.getOrdertype().equals("1")) {
            helper.setBackgroundRes(R.id.tv_order_type, R.drawable.shape_order_type_phone);
            helper.setText(R.id.tv_order_type, "电话就诊");
        } else {
            helper.setBackgroundRes(R.id.tv_order_type, R.drawable.shape_order_type_door);
            helper.setText(R.id.tv_order_type, "门诊预约");
        }

        //"status": "1"//0待处理、1已完成、2已取消
        if (item.getStatus().equals("0")) {
            helper.setText(R.id.tv_state, "待处理");
        } else if (item.getStatus().equals("1")) {
            helper.setText(R.id.tv_state, "已完成");
        } else {
            helper.setText(R.id.tv_state, "已取消");
        }

        //helper.setVisible(R.id.img_phone, item.getOrdertype().equals("1"));
        //helper.setVisible(R.id.tv_price, item.getOrdertype().equals("1"));

        ImageLoader.with(helper.itemView.getContext())
                .placeholder(R.drawable.img_doctor)
                .load(item.getPicheadurl())
                .into(helper.getView(R.id.img_avatar));

    }
}
