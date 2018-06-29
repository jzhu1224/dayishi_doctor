package com.jkdys.doctor.data.model;

import java.util.List;

public class PatientGroup {
    private String groupId;
    private String groupname;
    private List<PatientInfo> detail;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public List<PatientInfo> getDetail() {
        return detail;
    }

    public void setDetail(List<PatientInfo> detail) {
        this.detail = detail;
    }
}
