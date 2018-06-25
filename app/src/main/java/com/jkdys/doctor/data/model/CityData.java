package com.jkdys.doctor.data.model;

import com.google.gson.annotations.SerializedName;

import me.zhouzhuo.zzsecondarylinkage.bean.BaseMenuBean;

public class CityData extends BaseMenuBean {
    @SerializedName("aid")
    private String id;
    @SerializedName("areaname")
    private String name;
    private String parentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
