package com.jkdys.doctor.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public abstract class BaseSearchRefreshLoadMoreFragment<T extends Parcelable,
        V extends BaseLoadMoreView<T>,
        P extends BaseSearchLoadMorePresenter<T,V>> extends BaseRefreshLoadMoreFrament<T,V,P> {

    @BindView(R.id.edt_content)
    protected EditText edtContent;
    @BindView(R.id.fl_clear)
    protected FrameLayout rlClear;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.fr_search)
    FrameLayout frSearch;

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        super.initViews(view, savedInstanceState);
        rlClear.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= 14) {
            frSearch.setPadding(0, QMUIStatusBarHelper.getStatusbarHeight(getContext()),0,0);
        }
    }

    @OnTextChanged(value = R.id.edt_content, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged(Editable editable) {
        rlClear.setVisibility(TextUtils.isEmpty(editable.toString())?View.GONE:View.VISIBLE);
    }

    @OnClick(R.id.fl_clear)
    void flClearClick() {
        onClearClicked();
    }

    protected void onClearClicked() {
        presenter.search("");
        edtContent.setText("");
        adapter.setNewData(new ArrayList<>());
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_search_loadmore;
    }

    @OnClick(R.id.tv_search)
    void onSearchClick() {
        if (TextUtils.isEmpty(edtContent.getText().toString()))
            return;
        presenter.search(edtContent.getText().toString());
    }

    @OnClick(R.id.btn_back)
    void onBtnBackClick() {
        if (getActivity() != null)
            getActivity().finish();
    }
}
