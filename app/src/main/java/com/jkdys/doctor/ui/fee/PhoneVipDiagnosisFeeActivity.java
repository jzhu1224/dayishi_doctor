package com.jkdys.doctor.ui.fee;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.utils.ManyiUtils;
import com.jkdys.doctor.widget.DoctorGoodAtTagLayout;
import com.jkdys.doctor.widget.PhoneDiagnosisPriceView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class PhoneVipDiagnosisFeeActivity extends MvpActivity<Face2FaceDiagnosieFeeView,Face2FaceDiagnosisFeePresenter> implements Face2FaceDiagnosieFeeView {

    @Inject Face2FaceDiagnosisFeePresenter presenter;

    @BindView(R.id.fr_minute)
    FrameLayout frMinute;

    @BindView(R.id.line)
    View line;

    @BindView(R.id.line1)
    View line1;

    @BindView(R.id.ll_content)
    LinearLayout llContent;

    @BindView(R.id.tv_add_price)
    TextView tvAddPrice;

    List<PhoneDiagnosisPriceView> priceViews = new ArrayList<>();


    Button rightBtn;

    private String type = "2";

    private String originPrice = "";

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
        toolbar.setTitle("电话特需就诊费用");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> {
            finish();
        });

        frMinute.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);

        rightBtn = toolbar.addRightTextButton("编辑",R.id.id_right_btn);
        rightBtn.setTextColor(getResources().getColor(R.color.color_6196FF));
        rightBtn.setOnClickListener(view -> {
            onEditClick();
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_vip_phone_diagnosis_fee;
    }

    private void onEditClick() {
        rightBtn.setOnClickListener(view1 -> {
            if (originPrice.equals(getData())) {
                editMode(false);
                rightBtn.setText("编辑");
                tvAddPrice.setVisibility(View.GONE);
                rightBtn.setOnClickListener(view -> {
                    onEditClick();
                });
            } else {
                presenter.editPrice(type,getData());
            }
        });
        rightBtn.setText("保存");
        tvAddPrice.setVisibility(View.VISIBLE);
        editMode(true);
    }

    @Override
    public void onEditSuccess() {
        originPrice = getData();
        editMode(false);
        rightBtn.setText("编辑");
        tvAddPrice.setVisibility(View.GONE);
        rightBtn.setOnClickListener(view -> {
            onEditClick();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //ManyiUtils.closeKeyBoard(mActivity, edtPrice);
    }

    @Override
    public void onRequestPriceSuccess(List<String> price) {

        if (price.size()>0) {
            for (int i = 0; i < price.size(); i++) {
                if (i == 0) {
                    originPrice = price.get(i);
                } else {
                    originPrice = originPrice + ","+price.get(i);
                }
            }
            setData(price);
        }
    }

    @OnClick(R.id.tv_add_price)
    void onAddPriceClick() {
        QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(mActivity);
        builder.setPlaceholder("请输入价格").setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", (dialog, index) -> {
                    dialog.dismiss();
                    String tag = builder.getEditText().getText().toString();
                    addPrice(tag);
                }).show();
    }

    public void editMode(boolean editable) {
        if (editable) {
            for (PhoneDiagnosisPriceView doctorGoodAtTagLayout: priceViews) {
                doctorGoodAtTagLayout.setImgCloseVisable(true);
            }
        } else {
            for (PhoneDiagnosisPriceView doctorGoodAtTagLayout: priceViews) {
                doctorGoodAtTagLayout.setImgCloseVisable(false);
            }
        }
    }

    public void setData(List<String> tags) {

        for (int i = 0; i < tags.size(); i++) {
            PhoneDiagnosisPriceView dl = new PhoneDiagnosisPriceView(mActivity);
            dl.setImgCloseClickListener(priceViews::remove);
            dl.setText(tags.get(i));
            llContent.addView(dl);
            priceViews.add(dl);
        }
    }

    public void addPrice(String tag) {
        PhoneDiagnosisPriceView dl = new PhoneDiagnosisPriceView(mActivity);
        dl.setImgCloseClickListener(priceViews::remove);
        dl.setText(tag);
        dl.setImgCloseVisable(true);
        llContent.addView(dl);
        priceViews.add(dl);
    }

    public String getData() {
        StringBuilder tags = new StringBuilder();
        int i = 0;
        for (PhoneDiagnosisPriceView doctorGoodAtTagLayout: priceViews) {

            if (i > 0) {
                tags.append(",");
            }
            tags.append(doctorGoodAtTagLayout.getTag());

            i ++;
        }
        return tags.toString();
    }
}
