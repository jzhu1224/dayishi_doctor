package com.jkdys.doctor.utils;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

public class QMUIUtils {

    public static QMUICommonListItemView createItemView(QMUIGroupListView qmuiGroupListView, int drawableId, CharSequence title, String detailText, int accessory) {
        return qmuiGroupListView.createItemView(qmuiGroupListView.getResources().getDrawable(drawableId),
                title,
                detailText,
                QMUICommonListItemView.HORIZONTAL,
                accessory);
    }

    public static QMUICommonListItemView createItemView(QMUIGroupListView qmuiGroupListView,int drawableId, CharSequence title) {
        return createItemView(qmuiGroupListView,drawableId,title,"",QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
    }

    public static QMUICommonListItemView createItemView(QMUIGroupListView qmuiGroupListView, CharSequence title) {
        return qmuiGroupListView.createItemView(null,
                title,
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
    }
}
