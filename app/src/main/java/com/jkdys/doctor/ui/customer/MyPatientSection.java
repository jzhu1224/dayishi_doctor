package com.jkdys.doctor.ui.customer;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.jkdys.doctor.data.model.PatientInfo;

public class MyPatientSection extends SectionEntity<PatientInfo> {
    public MyPatientSection(boolean isHeader, String header) {
        super(isHeader, header);
    }
    public MyPatientSection(PatientInfo patienInfo) {
        super(patienInfo);
    }
}
