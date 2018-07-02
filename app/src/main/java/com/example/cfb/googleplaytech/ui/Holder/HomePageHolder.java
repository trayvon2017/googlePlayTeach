package com.example.cfb.googleplaytech.ui.Holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.domain.DownloadInfo;
import com.example.cfb.googleplaytech.http.HttpHelper;
import com.example.cfb.googleplaytech.manager.DownloadManager;
import com.example.cfb.googleplaytech.ui.view.ProgressArc;
import com.example.cfb.googleplaytech.utils.BitmapHelper;
import com.example.cfb.googleplaytech.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by fbfatboy on 2018/6/21.
 */

public class HomePageHolder extends BaseHolder<AppInfo> implements View.OnClickListener{

    private TextView mTvText;
    private ImageView mAppIcon;
    private TextView mDesc;
    private TextView mTvAppSize;
    private RatingBar mRb;
    private ProgressArc pbProgress;
    private DownloadManager mDM;
    private int mCurrentState;
    private float mProgress;
    private AppInfo mAppInfo;
    private TextView tvDownload;


    @Override
    protected View initItemView() {
        View view = UIUtils.inflate(R.layout.item_home_page);

        // 初始化进度条
        FrameLayout flProgress = (FrameLayout) view
                .findViewById(R.id.fl_home_progress);
        flProgress.setOnClickListener(this);

        pbProgress = new ProgressArc(UIUtils.getContext());
        // 设置圆形进度条直径
        pbProgress.setArcDiameter(UIUtils.dip2px(26));
        // 设置进度条颜色
        pbProgress.setProgressColor(UIUtils.getColor(R.color.progress));
        // 设置进度条宽高布局参数
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                UIUtils.dip2px(27), UIUtils.dip2px(27));
        flProgress.addView(pbProgress, params);

        // pbProgress.setOnClickListener(this);

        mDM = DownloadManager.getInstance();
        mDM.registerDownloadObserver(new DownloadManager.DownloadObserver() {
            @Override
            public void onDownloadStateChanged(DownloadInfo info) {
                refreshUIOnMainThread(info);
            }

            @Override
            public void onDownloadProgress(DownloadInfo info) {
                refreshUIOnMainThread(info);
            }
        });// 注册观察者, 监听状态和进度变化

        return view;
    }

    @Override
    public void setData(AppInfo data) {
        mAppInfo = data;
        mTvText.setText(data.name);
        mTvAppSize.setText(Formatter.formatFileSize(UIUtils.getContext(),data.size));
        mDesc.setText(data.des);
        BitmapUtils bitmapUtils = BitmapHelper.getBitmapUtils();
        bitmapUtils.display(mAppIcon, HttpHelper.URL + "image?name="
                + data.iconUrl);
        mRb.setRating(data.stars);
        refreshView(data);
    }

    @Override
    public void findViewById() {
        mTvText = (TextView) mRootView.findViewById(R.id.tv_app_name);
        mTvAppSize = (TextView) mRootView.findViewById(R.id.tv_app_size);
        mDesc = (TextView) mRootView.findViewById(R.id.tv_desc);
        mRb = (RatingBar) mRootView.findViewById(R.id.rb_app_rate);
        mAppIcon = (ImageView) mRootView.findViewById(R.id.iv_app_icon);
        tvDownload = (TextView) mRootView.findViewById(R.id.tv_download);

    }

    public void refreshView(AppInfo data) {
        // 判断当前应用是否下载过
        DownloadInfo downloadInfo = mDM.getDownloadInfo(data);
        if (downloadInfo != null) {
            // 之前下载过
            mCurrentState = downloadInfo.currentState;
            mProgress = downloadInfo.getProgress();
        } else {
            // 没有下载过
            mCurrentState = DownloadManager.DOWNLOAD_STATE_UNDO;
            mProgress = 0;
        }

        refreshUI(mCurrentState, mProgress, data.id);
    }
    private AppInfo getData(){
        return mAppInfo;
    }

    /**
     * 刷新界面
     *
     * @param progress
     * @param state
     */
    private void refreshUI(int state, float progress, String id) {
        // 由于listview重用机制, 要确保刷新之前, 确实是同一个应用
        if (!getData().id.equals(id)) {
            return;
        }

        mCurrentState = state;
        mProgress = progress;
        switch (state) {
            case DownloadManager.DOWNLOAD_STATE_UNDO:
                // 自定义进度条背景
                pbProgress.setBackgroundResource(R.drawable.ic_download);
                // 没有进度
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownload.setText("下载");
                break;
            case DownloadManager.DOWNLOAD_STATE_WAIT:
                pbProgress.setBackgroundResource(R.drawable.ic_download);
                // 等待模式
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
                tvDownload.setText("等待");
                break;
            case DownloadManager.DOWNLOAD_STATE_DOING:
                pbProgress.setBackgroundResource(R.drawable.ic_pause);
                // 下载中模式
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                pbProgress.setProgress(progress, true);
                tvDownload.setText((int) (progress * 100) + "%");
                break;
            case DownloadManager.DOWNLOAD_STATE_PAUSE:
                pbProgress.setBackgroundResource(R.drawable.ic_resume);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                break;
            case DownloadManager.DOWNLOAD_STATE_ERROR:
                pbProgress.setBackgroundResource(R.drawable.ic_redownload);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownload.setText("下载失败");
                break;
            case DownloadManager.DOWNLOAD_STATE_SUCCESS:
                pbProgress.setBackgroundResource(R.drawable.ic_install);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownload.setText("安装");
                break;

            default:
                break;
        }
    }

    // 主线程更新ui 3-4
    private void refreshUIOnMainThread(final DownloadInfo info) {
        // 判断下载对象是否是当前应用
        AppInfo appInfo = getData();
        if (appInfo.id.equals(info.id)) {
            UIUtils.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    refreshUI(info.currentState, info.getProgress(), info.id);
                }
            });
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_home_progress:
                // 根据当前状态来决定下一步操作
                if (mCurrentState == DownloadManager.DOWNLOAD_STATE_UNDO
                        || mCurrentState == DownloadManager.DOWNLOAD_STATE_ERROR
                        || mCurrentState == DownloadManager.DOWNLOAD_STATE_PAUSE) {
                    mDM.download(getData());// 开始下载
                } else if (mCurrentState == DownloadManager.DOWNLOAD_STATE_DOING
                        || mCurrentState == DownloadManager.DOWNLOAD_STATE_WAIT) {
                    mDM.pause(getData());// 暂停下载
                } else if (mCurrentState == DownloadManager.DOWNLOAD_STATE_SUCCESS) {
                    mDM.install(getData());// 开始安装
                }

                break;

            default:
                break;
        }
    }

}
