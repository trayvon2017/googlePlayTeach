package com.example.cfb.googleplaytech.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.domain.DownloadInfo;
import com.example.cfb.googleplaytech.http.protocol.DetailPageProtocol;
import com.example.cfb.googleplaytech.ui.Holder.DetailAppDesHolder;
import com.example.cfb.googleplaytech.ui.Holder.DetailAppDownloadHolder;
import com.example.cfb.googleplaytech.ui.Holder.DetailAppInfoHolder;
import com.example.cfb.googleplaytech.ui.Holder.DetailSafeInfoHolder;
import com.example.cfb.googleplaytech.ui.Holder.DetailScreenHolder;
import com.example.cfb.googleplaytech.ui.view.LoadingPage;
import com.example.cfb.googleplaytech.utils.UIUtils;

/**
 * Created by fbfatboy on 2018/6/26.
 */

public class AppDetailActivity extends BaseActivity {

    private LoadingPage mLoadingPage;
    private String packageName;
    private AppInfo data;
    private FrameLayout mViewAppInfo;
    private FrameLayout mViewAppSafeInfo;
    private FrameLayout mViewAppScreen;
    private FrameLayout mFlAppDes;
    private FrameLayout mFlAppDownload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showActionBarLogo();
        Intent intent = getIntent();
        packageName = intent.getStringExtra("packageName");
//        TextView textView = new TextView(UIUtils.getContext());
//        textView.setText(packageName);

        mLoadingPage = new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {
                return AppDetailActivity.this.onCreateSuccessView();
            }

            @Override
            public LoadResult onLoad() {
                return AppDetailActivity.this.onLoad();
            }
        };
        setContentView(mLoadingPage);
        loadData();

        // TODO: 2018/6/26 item点击之后的响应
    }

    /**
     * 请求服务器或者缓存拿到数据
     * @return
     */
    private  LoadingPage.LoadResult onLoad() {
        DetailPageProtocol detailPageProtocol = new DetailPageProtocol();
        detailPageProtocol.setParams("&packageName="+packageName);
        data = detailPageProtocol.getData(0);
        if (data !=null){
            return LoadingPage.LoadResult.RESULT_SUCCESS;
        }
        return LoadingPage.LoadResult.RESULT_ERROR;
    }

    /**
     * 初始化成功页面
     * @return
     */
    private  View onCreateSuccessView() {
        //根布局
        View view = UIUtils.inflate(R.layout.app_detail_page_layout);
        //初始化appinfo部分
        mViewAppInfo = (FrameLayout) view.findViewById(R.id.fl_app_info);
        DetailAppInfoHolder detailAppInfoHolder = new DetailAppInfoHolder();
        detailAppInfoHolder.setData(data);
        mViewAppInfo.addView(detailAppInfoHolder.mRootView);
        //初始化safe部分
        mViewAppSafeInfo = (FrameLayout) view.findViewById(R.id.fl_app_safe_info);
        DetailSafeInfoHolder detailSafeInfoHolder = new DetailSafeInfoHolder();
        detailSafeInfoHolder.setData(data.safe);
        mViewAppSafeInfo.addView(detailSafeInfoHolder.mRootView);
        //初始化预览图部分
        mViewAppScreen = (FrameLayout) view.findViewById(R.id.fl_app_screen);
        DetailScreenHolder detailScreenHolder = new DetailScreenHolder();
        detailScreenHolder.setData(AppDetailActivity.this,data.screen);
        mViewAppScreen.addView(detailScreenHolder.mRootView);
        //初始化应用介绍部分
        mFlAppDes = (FrameLayout) view.findViewById(R.id.fl_app_des);
        DetailAppDesHolder detailAppDesHolder = new DetailAppDesHolder();
        detailAppDesHolder.setData(data);
        mFlAppDes.addView(detailAppDesHolder.mRootView);
        //初始化下载部分
        mFlAppDownload = (FrameLayout) view.findViewById(R.id.fl_app_download);
        DetailAppDownloadHolder detailAppDownloadHolder = new DetailAppDownloadHolder();
        // appinfo转化成download设置给downloadHolder
        detailAppDownloadHolder.setData(data);
        mFlAppDownload.addView(detailAppDownloadHolder.mRootView);


        return view;
    }
    public void loadData(){
        if (mLoadingPage != null){
            mLoadingPage.loadData();
        }
    }

}
