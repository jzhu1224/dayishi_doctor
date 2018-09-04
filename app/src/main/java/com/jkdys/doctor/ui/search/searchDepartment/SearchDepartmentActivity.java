package com.jkdys.doctor.ui.search.searchDepartment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.DepartmentData;
import com.jkdys.doctor.data.model.GroupDepartmentData;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.search.BaseSearchActivity;
import com.jkdys.doctor.ui.search.SearchData;
import com.jkdys.doctor.ui.search.searchDepartment.adapter.DepartmentAdapter;
import com.jkdys.doctor.ui.search.searchDepartment.adapter.GroupDepartmentAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import butterknife.BindView;
import me.zhouzhuo.zzsecondarylinkage.ZzSecondaryLinkage;
import me.zhouzhuo.zzsecondarylinkage.model.ILinkage;

public class SearchDepartmentActivity extends MvpActivity<SelectDepartmentView,SearchDepartmentPresenter> implements SelectDepartmentView {

    public static final String KEY_HOSPITAL_ID = "key_hospital_id";
    public static final String KEY_HOSPITAL_NAME = "key_hospital_name";

    @Inject
    SearchDepartmentPresenter searchDepartmentPresenter;

    @BindView(R.id.zz_linkage)
    ZzSecondaryLinkage zzSecondaryLinkage;

    GroupDepartmentAdapter groupDepartmentAdapter;
    DepartmentAdapter departmentAdapter;

    String hospitalId,hospitalName;

    List<GroupDepartmentData> groupDepartmentData = new ArrayList<>();
    List<DepartmentData> departmentData = new ArrayList<>();

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);

        hospitalId = getIntent().getStringExtra(KEY_HOSPITAL_ID);
        hospitalName = getIntent().getStringExtra(KEY_HOSPITAL_NAME);

        toolbar.setTitle(hospitalName+"->科室");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());

        groupDepartmentAdapter = new GroupDepartmentAdapter(mActivity,groupDepartmentData);
        departmentAdapter = new DepartmentAdapter(mActivity, departmentData);

        zzSecondaryLinkage.setLeftMenuAdapter(groupDepartmentAdapter);
        zzSecondaryLinkage.setRightContentAdapter(departmentAdapter);

        zzSecondaryLinkage.setOnItemClickListener(new ILinkage.OnItemClickListener() {
            @Override
            public void onLeftClick(View itemView, int position) {
                departmentAdapter.setList(((GroupDepartmentData)groupDepartmentAdapter.getItem(position)).getDetaillist());
            }

            @Override
            public void onRightClick(View itemView, int position) {
                Intent intent = getIntent();
                DepartmentData departmentData1 = departmentData.get(position);
                SearchData searchData = new SearchData();
                searchData.setText(departmentData1.getFacultyname());
                searchData.setId(departmentData1.getFid());
                intent.putExtra(BaseSearchActivity.KEY_RETURN_DATA, searchData);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    @NonNull
    @Override
    public SearchDepartmentPresenter createPresenter() {
        getActivityComponent().inject(this);
        return searchDepartmentPresenter;
    }

    @Override
    protected void afterMvpDelegateCreateInvoked() {
        searchDepartmentPresenter.getData(hospitalId);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_select_area;
    }

    @Override
    public void onDataReturn(List<GroupDepartmentData> groupDepartmentDataList) {
        groupDepartmentAdapter.setList(groupDepartmentDataList);
    }
}
