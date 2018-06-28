package com.example.cfb.googleplaytech.ui.Holder;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.domain.DownloadInfo;
import com.example.cfb.googleplaytech.utils.UIUtils;

/**
 * Created by cfb on 2018/6/28.
 */

public class DetailAppDownloadHolder extends BaseHolder<DownloadInfo> implements View.OnClickListener {
    private DownloadInfo info;
    private FrameLayout flProgress;
    private Button btnDownload;

    @Override
    protected View initItemView() {
        View view = UIUtils.inflate(R.layout.detail_app_download_layout);
        return view;
    }

    @Override
    public void setData(DownloadInfo info) {
        this.info = info;
        refreshView();
    }

    private void refreshView() {
        int state =info.getDownloadState();
        switch (state){
            case DownloadInfo.NONE:

                break;
            case DownloadInfo.COMPLETED:

                break;
            case DownloadInfo.UNCOMPLETED:

                break;
        }
    }

    @Override
    public void findViewById() {
        flProgress = (FrameLayout) mRootView.findViewById(R.id.fl_detail_progress);
        btnDownload = (Button) mRootView.findViewById(R.id.btn_detail_download);

        flProgress.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_detail_progress:
            case R.id.btn_detail_download:
                //根据当前的状态app的状态执行操作
                //非下载状态==执行下载

                //下载中++暂停

                // 成功++安装


                break;
            default:
                break;
        }

    }
}
