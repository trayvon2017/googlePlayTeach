package com.example.cfb.googleplaytech.ui.Holder;

import android.view.View;
import android.widget.TextView;

import com.example.cfb.googleplaytech.domain.ClassificationInfo;
import com.example.cfb.googleplaytech.utils.UIUtils;

/**
 * Created by cfb on 2018/6/25.
 */

public class ClassificationTitleHolder extends BaseHolder<ClassificationInfo> {

    @Override
    protected View initItemView() {
        TextView textView = new TextView(UIUtils.getContext());
        return textView;
    }

    @Override
    public void setData(ClassificationInfo classificationInfo) {
        TextView textTitle =(TextView)itemView;
        textTitle.setText(classificationInfo.title);
    }

    @Override
    public void findViewById() {

    }
}
