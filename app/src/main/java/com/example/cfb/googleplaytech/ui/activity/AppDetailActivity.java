package com.example.cfb.googleplaytech.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.cfb.googleplaytech.ui.view.LoadingPage;
import com.example.cfb.googleplaytech.utils.UIUtils;

/**
 * Created by fbfatboy on 2018/6/26.
 */

public class AppDetailActivity extends BaseActivity {

    private LoadingPage mLoadingPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showActionBarLogo();
        Intent intent = getIntent();
        String packageName = intent.getStringExtra("packageName");
        TextView textView = new TextView(UIUtils.getContext());
        textView.setText(packageName);
        setContentView(textView);
        mLoadingPage = new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {
                return AppDetailActivity.onCreateSuccessView();
            }

            @Override
            public LoadResult onLoad() {
                return AppDetailActivity.onLoad();
            }
        };

        loadData();

        // TODO: 2018/6/26 item点击之后的响应
    }

    /**
     * 请求服务器或者缓存拿到数据
     * @return
     */
    private static LoadingPage.LoadResult onLoad() {

        return null;
    }

    /**
     * 初始化成功页面
     * @return
     */
    private static View onCreateSuccessView() {

        return null;
    }
    public void loadData(){
        if (mLoadingPage != null){
            mLoadingPage.loadData();
        }
    }
}
