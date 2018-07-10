package com.jkdys.doctor.ui.chat.doctor.search;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.SearchDoctorData;

import java.util.List;

public class SearchDoctorAdapter extends BaseQuickAdapter<SearchDoctorData, BaseViewHolder> {
    public SearchDoctorAdapter(@Nullable List<SearchDoctorData> data) {
        super(R.layout.item_search_data,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchDoctorData item) {

    }
}
