package com.jkdys.doctor.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

public abstract class BaseSearchActivity<V extends SearchView,P extends BaseSearchPresenter<V>> extends MvpActivity<V, P> implements SearchView{

    @BindView(R.id.edt_content)
    EditText edtContent;
    @BindView(R.id.fl_clear)
    FrameLayout rlClear;

    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.empty_view)
    QMUIEmptyView emptyView;

    protected SearchAdapter searchAdapter;


    protected List<SearchData> mData = new ArrayList<>();


    Handler handler = new Handler();

    public static final String KEY_RETURN_DATA = "key_return_data";


    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        handler = new Handler();
        rlClear.setVisibility(View.GONE);
        searchAdapter = new SearchAdapter();

        emptyView.setDetailText("暂无数据");
        listView.setEmptyView(emptyView);

        listView.setAdapter(searchAdapter);
        edtContent.addTextChangedListener(new TextWatcher() {
            private String beforeText;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                beforeText = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                BaseSearchActivity.this.afterTextChanged(beforeText,editable);
            }
        });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = getIntent();
            intent.putExtra(KEY_RETURN_DATA,mData.get(i));
            setResult(RESULT_OK,intent);
            finish();
        });
    }

    protected void afterTextChanged(String beforeText,Editable editable) {
        handler.removeCallbacksAndMessages(null);
        if (!TextUtils.isEmpty(editable.toString())) {
            rlClear.setVisibility(View.VISIBLE);
            handler.postDelayed(() -> onSearch(editable.toString()),100);
        } else if (!TextUtils.isEmpty(beforeText)){
            onTextChangedEmpty();
        }
    }

    protected void onTextChangedEmpty() {
        rlClear.setVisibility(View.GONE);
        mData.clear();
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void showContent() {
        //super.showContent();
        emptyView.setLoadingShowing(false);
        emptyView.setDetailText("暂无数据");
        //listView.setEmptyView(emptyView);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        //super.showLoading(pullToRefresh);
        emptyView.show(true);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_base_search;
    }

    @OnClick(R.id.fl_clear)
    void flClearClick() {
        onClearClicked();
    }

    protected void onClearClicked() {
        edtContent.setText("");
        mData.clear();
        searchAdapter.notifyDataSetChanged();
    }

    protected void onSearch(String text) {
        getPresenter().search(text);
    }

    @Override
    public void onSearchResult(List<SearchData> searchData) {
        mData.clear();
        mData.addAll(searchData);
        searchAdapter.notifyDataSetChanged();
    }

    class SearchAdapter extends BaseAdapter implements Filterable {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int i) {
            return mData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
           MyViewHolder holder;
           if (view == null) {
               holder = new MyViewHolder();
               view = LayoutInflater.from(mActivity).inflate(R.layout.item_search_data,null);
               holder.tvContent = view.findViewById(R.id.tv_content);
               view.setTag(holder);
           } else {
               holder = (MyViewHolder) view.getTag();
           }

           holder.tvContent.setText(mData.get(i).getText());

           return view;
        }

        @Override
        public Filter getFilter() {
            return BaseSearchActivity.this.getFilter();
        }

        public final class MyViewHolder {
            TextView tvContent;
        }
    }

    protected Filter getFilter() {
        return null;
    }
}
