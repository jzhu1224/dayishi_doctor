
package com.jkdys.doctor.ui.search.selectArea.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.CityData;
import com.jkdys.doctor.ui.search.selectArea.ViewHolder;
import com.jkdys.doctor.utils.ViewUtil;
import java.util.List;
import me.zhouzhuo.zzsecondarylinkage.adapter.RightMenuBaseListAdapter;

public class CityListAdapter extends RightMenuBaseListAdapter<ViewHolder, CityData> {

    public CityListAdapter(Context ctx, List<CityData> list) {
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
        leftListViewHolder.imgArrow = itemView.findViewById(R.id.img_arrow);

    }

    @Override
    public void bindData(ViewHolder leftListViewHolder, int position) {

        leftListViewHolder.imgArrow.setVisibility(list.get(position).isSelected()?View.VISIBLE:View.INVISIBLE);
        leftListViewHolder.tvName.setText(list.get(position).getName());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_area;
    }

    @Override
    public int getIndicatorResId() {
        return R.color.color_white;
    }
}