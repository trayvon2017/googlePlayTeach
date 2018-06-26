package com.example.cfb.googleplaytech.ui.Holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.http.HttpHelper;
import com.example.cfb.googleplaytech.utils.BitmapHelper;
import com.example.cfb.googleplaytech.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by fbfatboy on 2018/6/21.
 */

public class AppPageHolder extends BaseHolder<AppInfo> {

    private TextView mTvText;
    private ImageView mAppIcon;
    private TextView mDesc;
    private TextView mTvAppSize;
    private RatingBar mRb;


    @Override
    protected View initItemView() {
        return UIUtils.inflate(R.layout.item_home_page);
    }

    @Override
    public void setData(AppInfo data) {
        mTvText.setText(data.name);
        mTvAppSize.setText(Formatter.formatFileSize(UIUtils.getContext(),data.size));
        mDesc.setText(data.des);
        BitmapUtils bitmapUtils = BitmapHelper.getBitmapUtils();
        bitmapUtils.display(mAppIcon, HttpHelper.URL + "image?name="
                + data.iconUrl);
    }

    @Override
    public void findViewById() {
        mTvText = (TextView) mRootView.findViewById(R.id.tv_app_name);
        mTvAppSize = (TextView) mRootView.findViewById(R.id.tv_app_size);
        mDesc = (TextView) mRootView.findViewById(R.id.tv_desc);
        mRb = (RatingBar) mRootView.findViewById(R.id.rb_app_rate);
        mAppIcon = (ImageView) mRootView.findViewById(R.id.iv_app_icon);
    }
}
