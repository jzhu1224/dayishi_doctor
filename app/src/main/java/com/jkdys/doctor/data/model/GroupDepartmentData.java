package com.jkdys.doctor.data.model;

import java.util.List;

import me.zhouzhuo.zzsecondarylinkage.bean.BaseMenuBean;

public class GroupDepartmentData extends BaseMenuBean {
    private String fid;
    private String facultyname;
    private List<DepartmentData> detaillist;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFacultyname() {
        return facultyname;
    }

    public void setFacultyname(String facultyname) {
        this.facultyname = facultyname;
    }

    public List<DepartmentData> getDetaillist() {
        return detaillist;
    }

    public void setDetaillist(List<DepartmentData> detaillist) {
        this.detaillist = detaillist;
    }
}
