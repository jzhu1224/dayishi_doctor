package com.jkdys.doctor.data.model;

import me.zhouzhuo.zzsecondarylinkage.bean.BaseMenuBean;

public class DepartmentData extends BaseMenuBean{
    private String fid;
    private String facultyname;

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
}
