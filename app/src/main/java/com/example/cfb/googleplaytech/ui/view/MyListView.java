package com.example.cfb.googleplaytech.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by fbfatboy on 2018/6/22.
 */

public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {
        this.setSelector(new ColorDrawable());//设置默认状态选择器全透明
        this.setDivider(null);
        this.setCacheColorHint(Color.TRANSPARENT);//滑动时候的背景设置为全透明
    }
}
