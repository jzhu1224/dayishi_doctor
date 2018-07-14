package com.jkdys.doctor.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.nex3z.flowlayout.FlowLayout;
import java.util.ArrayList;
import java.util.List;

public class DoctorGoodAtLayout extends FlowLayout {

    List<DoctorGoodAtTagLayout> dls = new ArrayList<>();

    DoctorGoodAtTagLayout tvAdd;
    AddTagClickListener addTagClickListener;

    public DoctorGoodAtLayout(Context context) {
        this(context, null);
    }

    public DoctorGoodAtLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        tvAdd = new DoctorGoodAtTagLayout(getContext());
        tvAdd.setText("    +    ");

        tvAdd.setOnClickListener(view -> {
            if (addTagClickListener != null)
                addTagClickListener.onClick(view);
        });
    }

    public void setAddTagClickListener (AddTagClickListener addTagClickListener) {
        this.addTagClickListener = addTagClickListener;
    }

    public void editMode(boolean editable) {
        if (editable) {
            for (DoctorGoodAtTagLayout doctorGoodAtTagLayout: dls) {
                doctorGoodAtTagLayout.setImgCloseVisable(true);
            }
            removeView(tvAdd);
            addView(tvAdd);
        } else {
            for (DoctorGoodAtTagLayout doctorGoodAtTagLayout: dls) {
                doctorGoodAtTagLayout.setImgCloseVisable(false);
            }
            removeView(tvAdd);
        }
    }

    public void setData(String tags) {
        if (TextUtils.isEmpty(tags))
            return;
        String[] tagsArray = tags.split(",");

        for (int i = 0; i < tagsArray.length; i++) {
            DoctorGoodAtTagLayout dl = new DoctorGoodAtTagLayout(getContext());
            dl.setImgCloseClickListener(dls::remove);
            dl.setText(tagsArray[i]);
            addView(dl);
            dls.add(dl);
        }
    }

    public void addTag(String tag) {
        removeView(tvAdd);
        DoctorGoodAtTagLayout dl = new DoctorGoodAtTagLayout(getContext());
        dl.setImgCloseClickListener(dls::remove);
        dl.setText(tag);
        dl.setImgCloseVisable(true);
        addView(dl);
        dls.add(dl);
        addView(tvAdd);
    }

    public String getData() {
        StringBuilder tags = new StringBuilder();
        int i = 0;
        for (DoctorGoodAtTagLayout doctorGoodAtTagLayout: dls) {

            if (i > 0) {
                tags.append(",");
            }
            tags.append(doctorGoodAtTagLayout.getTag());

            i ++;
        }
        return tags.toString();
    }

    public interface AddTagClickListener {
        void onClick(View view);
    }
}
