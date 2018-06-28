package com.example.cfb.googleplaytech.ui.Holder;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.domain.DownloadInfo;
import com.example.cfb.googleplaytech.manager.DownloadManager;
import com.example.cfb.googleplaytech.ui.view.ProgressHorizontal;
import com.example.cfb.googleplaytech.utils.UIUtils;

/**
 * Created by cfb on 2018/6/28.
 */

public class DetailAppDownloadHolder extends BaseHolder<AppInfo> implements View.OnClickListener  {
    private DownloadInfo info;
    private FrameLayout flProgress;
    private DownloadManager mDM;
    private ProgressHorizontal pbProgress;
    private int mCurrentState;
    private float mProgress;
    private Button btnDownload;

    @Override
    protected View initItemView() {
        View view = UIUtils.inflate(R.layout.detail_app_download_layout);
        btnDownload = (Button) view.findViewById(R.id.btn_detail_download);
        //注册监听
        mDM = DownloadManager.getInstance();
        mDM.registerDownloadObserver(new DownloadManager.DownloadObserver() {
            /**
             * 状态更新
             * @param info 更新的DownloadInfo
             */
            @Override
            public void onDownloadStateChanged(DownloadInfo info) {
                if (info.id.equals(getData().id)){
                    refreshUIOnMainThread(info.currentState,info.getProgress());
                }
            }

            /**
             * 状态更新
             * @param info 更新的DownloadInfo
             */
            @Override
            public void onDownloadProgress(DownloadInfo info) {
                if (info.id.equals(getData().id)){
                    refreshUIOnMainThread(info.currentState,info.getProgress());
                }
            }
        });
        flProgress = (FrameLayout) view.findViewById(R.id.fl_detail_progress);

        pbProgress = new ProgressHorizontal(UIUtils.getContext());
        pbProgress.setProgressBackgroundResource(R.drawable.progress_bg);// 进度条背景图片
        pbProgress.setProgressResource(R.drawable.progress_normal);// 进度条图片
        pbProgress.setProgressTextColor(Color.WHITE);// 进度文字颜色
        pbProgress.setProgressTextSize(UIUtils.dip2px(18));// 进度文字大小

        // 宽高填充父窗体
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);

        // 给帧布局添加自定义进度条
        flProgress.addView(pbProgress, params);
        return view;
    }
    private AppInfo mAppinfo;
    public AppInfo getData(){
        return mAppinfo;
    }
    @Override
    public void setData(AppInfo info) {
        mAppinfo = info;
        //判断之前是否下载过
        DownloadInfo downloadInfo = mDM.getDownloadInfo(info);
        if (downloadInfo!=null){
            //之前已经下载过
            mCurrentState = downloadInfo.currentState;
            mProgress = downloadInfo.getProgress();

        }else {
            //之前没下载过
            downloadInfo = DownloadInfo.copy(info);
            mCurrentState = downloadInfo.currentState;
            mProgress = downloadInfo.getProgress();
        }
        refreshUI(mCurrentState, mProgress);

    }

    /**
     * 首次进入以及监听到变化之后都会用到
     * @param currentState
     * @param progress
     */
    private void refreshUI(int currentState, float progress) {
        mCurrentState = currentState;
        mProgress = progress;
        switch (currentState){
            case DownloadManager.DOWNLOAD_STATE_UNDO://未下载
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("下载");
                break;
            case DownloadManager.DOWNLOAD_STATE_WAIT://等待下载
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("等待中");
                break;
            case DownloadManager.DOWNLOAD_STATE_DOING://正在下载
                flProgress.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                pbProgress.setProgress(progress);
                pbProgress.setCenterText("");
                break;
            case DownloadManager.DOWNLOAD_STATE_PAUSE://下载暂停
                flProgress.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                pbProgress.setProgress(progress);
                pbProgress.setCenterText("暂停");
                break;
            case DownloadManager.DOWNLOAD_STATE_ERROR://下载失败
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("下载失败");
                break;
            case DownloadManager.DOWNLOAD_STATE_SUCCESS://下载成功
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("安装");
                break;
            default:
                break;
        }
    }

    /**
     * 主线程更新UI
     * @param currentState
     * @param progress
     */
    public void refreshUIOnMainThread(final int currentState, final float progress){
        UIUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshUI(currentState,progress);
            }
        });
    }


    @Override
    public void findViewById() {
//        flProgress = (FrameLayout) mRootView.findViewById(R.id.fl_detail_progress);
//        btnDownload = (Button) mRootView.findViewById(R.id.btn_detail_download);

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
                if (mCurrentState == DownloadManager.DOWNLOAD_STATE_UNDO||
                        mCurrentState == DownloadManager.DOWNLOAD_STATE_ERROR||
                        mCurrentState == DownloadManager.DOWNLOAD_STATE_PAUSE){
                    mDM.download(getData());
                }
                else if (mCurrentState == DownloadManager.DOWNLOAD_STATE_DOING||
                        mCurrentState == DownloadManager.DOWNLOAD_STATE_WAIT){//下载中++暂停
                    mDM.pause(getData());
                }else {// 成功++安装
                    mDM.install(getData());
                }




                break;
            default:
                break;
        }

    }

}
