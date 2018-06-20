package com.example.cfb.googleplaytech.ui.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.utils.UIUtils;

/**
 * 页面有五种状态：未加载，加载中，加载失败，数据为空，加载成功
 * Created by cfb on 2018/6/20.
 */

public class LoadingPage extends FrameLayout {
    private static final int STATE_UNDO = 0;
    private static final int STATE_LOADING = 1;
    private static final int STATE_ERROR = 2;
    private static final int STATE_EMPTY = 3;
    private static final int STATE_SUCCESS = 4;
    private int currenrState = STATE_UNDO;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;

    public LoadingPage(@NonNull Context context) {
        super(context);
        initView();
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {
        //未加载和加载中页面
        mLoadingView = UIUtils.inflate(R.layout.loading_page_layout);
        addView(mLoadingView);
        //加载失败
        mErrorView = UIUtils.inflate(R.layout.error_page_layout);
        addView(mErrorView);
        //加载成功，但是空数据
        mEmptyView = UIUtils.inflate(R.layout.empty_page_layout);
        addView(mEmptyView);
    }


}
