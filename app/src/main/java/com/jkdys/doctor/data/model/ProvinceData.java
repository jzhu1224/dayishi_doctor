package com.jkdys.doctor.data.model;

import com.google.gson.annotations.SerializedName;

import me.zhouzhuo.zzsecondarylinkage.bean.BaseMenuBean;

public class ProvinceData extends BaseMenuBean{
    @SerializedName("provinceid")
   private String id;
    @SerializedName("areaname")
   private String name;

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
}
