
package com.jkdys.doctor.ui.search.selectArea.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.ProvinceData;
import com.jkdys.doctor.ui.search.selectArea.ViewHolder;
import com.jkdys.doctor.utils.ViewUtil;
import java.util.List;
import me.zhouzhuo.zzsecondarylinkage.adapter.LeftMenuBaseListAdapter;

public class ProvinceListAdapter extends LeftMenuBaseListAdapter<ViewHolder, ProvinceData> {

    public ProvinceListAdapter(Context ctx, List<ProvinceData> list) {
        super(ctx, list);
    }

    @Override
    public ViewHolder getViewHolder() {
        return new ViewHolder();
    }

    @Override
    public void bindView(ViewHolder leftListViewHolder, View itemView) {
        //ViewUtil.scaleContentView((ViewGroup) itemView.findViewById(R.id.root));
        leftListViewHolder.tvName = (TextView) itemView.findViewById(R.id.tv_menu);

    }

    @Override
    public void bindData(ViewHolder leftListViewHolder, int position) {
        leftListViewHolder.tvName.setText(list.get(position).getName());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_area;
    }

    @Override
    public int getIndicatorResId() {
        return R.drawable.list_select;
    }
}