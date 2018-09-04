package com.jkdys.doctor.ui.search.searchDepartment;

import com.jkdys.doctor.data.model.GroupDepartmentData;
import com.jkdys.doctor.ui.BaseView;

import java.util.List;

public interface SelectDepartmentView extends BaseView{
    void onDataReturn(List<GroupDepartmentData> groupDepartmentDataList);
}
