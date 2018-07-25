package com.jkdys.doctor.ui.fee;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.utils.ManyiUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class PhoneDiagnosisFeeActivity extends MvpActivity<Face2FaceDiagnosieFeeView,Face2FaceDiagnosisFeePresenter> implements Face2FaceDiagnosieFeeView {

    @Inject Face2FaceDiagnosisFeePresenter presenter;

    @BindView(R.id.edt_price)
    EditText edtPrice;

    @BindView(R.id.fr_minute)
    FrameLayout frMinute;

    @BindView(R.id.line)
    View line;


    Button rightBtn;

    private String type = "1";

    @NonNull
    @Override
    public Face2FaceDiagnosisFeePresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }

    @Override
    protected void afterMvpDelegateCreateInvoked() {
        super.afterMvpDelegateCreateInvoked();
        presenter.getPrice(type);
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("电话普通就诊费用");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> {
            ManyiUtils.closeKeyBoard(mActivity, edtPrice);
            finish();
        });

        frMinute.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);

        edtPrice.setEnabled(false);

        rightBtn = toolbar.addRightTextButton("编辑",R.id.id_right_btn);
        rightBtn.setTextColor(getResources().getColor(R.color.color_6196FF));
        rightBtn.setOnClickListener(view -> {
            if (!edtPrice.isEnabled()) {
                ManyiUtils.showKeyBoard(mActivity,edtPrice);
            } else {
                ManyiUtils.closeKeyBoard(mActivity,edtPrice);
            }
            edtPrice.setEnabled(true);
            rightBtn.setText("保存");
            rightBtn.setOnClickListener(view1 -> presenter.editPrice(type,edtPrice.getText().toString()));
            edtPrice.setSelection(edtPrice.getText().toString().length());
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_face2face_diagnosis_fee;
    }

    @Override
    public void onEditSuccess() {
        edtPrice.setEnabled(false);
        rightBtn.setText("编辑");
        rightBtn.setOnClickListener(view -> {
            if (!edtPrice.isEnabled()) {
                ManyiUtils.showKeyBoard(mActivity,edtPrice);
            } else {
                ManyiUtils.closeKeyBoard(mActivity,edtPrice);
            }
            edtPrice.setEnabled(true);
            rightBtn.setText("保存");
            rightBtn.setOnClickListener(view1 -> presenter.editPrice(type,edtPrice.getText().toString()));
            edtPrice.setSelection(edtPrice.getText().toString().length());
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //ManyiUtils.closeKeyBoard(mActivity, edtPrice);
    }

    @Override
    public void onRequestPriceSuccess(List<String> price) {
        if (price.size()>0)
            edtPrice.setText(price.get(0));
    }
}
