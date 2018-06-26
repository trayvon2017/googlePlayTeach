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
 * Created by cfb on 2018/6/26.
 */

public class DetailAppInfoHolder extends BaseHolder<AppInfo> {


    private TextView mTvAppSize;
    private TextView mTvAppDate;
    private TextView mTvAppDownloadNum;
    private TextView mTvVersion;
    private TextView mTvAppName;
    private ImageView mIvAppIcon;
    private RatingBar mRbAppRate;
    private BitmapUtils bitmapUtils;

    @Override
    protected View initItemView() {
        View view = UIUtils.inflate(R.layout.detail_app_info_layout);
        return view;
    }

    @Override
    public void setData(AppInfo appInfo) {
        bitmapUtils = BitmapHelper.getBitmapUtils();
        mTvAppSize.setText(Formatter.formatFileSize(UIUtils.getContext(),appInfo.size));
        mTvAppDate.setText(appInfo.date);
        mTvAppDownloadNum.setText(appInfo.downloadNum);
        mTvVersion.setText(appInfo.version);
        mTvAppName.setText(appInfo.name);
        bitmapUtils.display(mIvAppIcon, HttpHelper.URL + "image?name="
                + appInfo.iconUrl);
        mRbAppRate.setRating(appInfo.stars);

    }

    @Override
    public void findViewById() {
        mTvAppSize = mRootView.findViewById(R.id.tv_detail_app_size);
        mTvAppDate =  mRootView.findViewById(R.id.tv_detail_date);
        mTvAppDownloadNum =  mRootView.findViewById(R.id.tv_detail_down_num);
        mTvVersion =  mRootView.findViewById(R.id.tv_detail_version);
        mTvAppName = mRootView.findViewById(R.id.tv_detail_app_name);
        mIvAppIcon =  mRootView.findViewById(R.id.iv_detail_app_icon);
        mRbAppRate =  mRootView.findViewById(R.id.rb_detail_app_rate);

    }
}
