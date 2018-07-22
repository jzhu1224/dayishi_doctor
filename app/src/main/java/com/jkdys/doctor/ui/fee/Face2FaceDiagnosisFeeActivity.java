package com.jkdys.doctor.ui.fee;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.utils.ManyiUtils;

import javax.inject.Inject;
import butterknife.BindView;


public class Face2FaceDiagnosisFeeActivity extends MvpActivity<Face2FaceDiagnosieFeeView,Face2FaceDiagnosisFeePresenter> implements Face2FaceDiagnosieFeeView {

    @Inject Face2FaceDiagnosisFeePresenter presenter;

    @BindView(R.id.edt_price)
    EditText edtPrice;

    Button rightBtn;

    @NonNull
    @Override
    public Face2FaceDiagnosisFeePresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("擅长疾病");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> {
            ManyiUtils.closeKeyBoard(mActivity, edtPrice);
            finish();
        });

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
            rightBtn.setOnClickListener(view1 -> presenter.editPrice(edtPrice.getText().toString()));
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
            rightBtn.setOnClickListener(view1 -> presenter.editPrice(edtPrice.getText().toString()));
            edtPrice.setSelection(edtPrice.getText().toString().length());
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //ManyiUtils.closeKeyBoard(mActivity, edtPrice);
    }

    @Override
    public void onRequestPriceSuccess(String price) {
        edtPrice.setText(price);
    }
}
