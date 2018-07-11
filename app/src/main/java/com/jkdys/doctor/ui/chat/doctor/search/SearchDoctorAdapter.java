package com.jkdys.doctor.ui.chat.doctor.search;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.data.model.SearchDoctorData;

import java.util.List;

public class SearchDoctorAdapter extends BaseQuickAdapter<SearchDoctorData, BaseViewHolder> {
    public SearchDoctorAdapter(@Nullable List<SearchDoctorData> data) {
        super(R.layout.item_search_doctor,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchDoctorData item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_desc, item.getHospitalname()+" / "+item.getFacultyname()+" / "+item.getTitlename());
        ImageLoader.with(getRecyclerView().getContext()).load(item.getPicheadurl()).placeholder(R.drawable.img_doctor).into(helper.getView(R.id.img_header));
    }
}
