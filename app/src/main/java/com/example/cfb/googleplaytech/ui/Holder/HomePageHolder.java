package com.example.cfb.googleplaytech.ui.Holder;

import android.view.View;
import android.widget.TextView;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.utils.UIUtils;

/**
 * Created by fbfatboy on 2018/6/21.
 */

public class HomePageHolder extends BaseHolder<AppInfo> {

    private TextView mTvText;


    @Override
    protected View initItemView() {
        return UIUtils.inflate(R.layout.item_test_textview);
    }

    @Override
    public void setData(AppInfo data) {
        mTvText.setText(data.name);
    }

    @Override
    public void findViewById() {
        mTvText = (TextView) itemView.findViewById(R.id.tv_test_text);

    }
}
