package com.example.cfb.googleplaytech.ui.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.utils.UIUtils;

/**
 * 页面有五种状态：未加载，加载中，加载失败，数据为空，加载成功
 * Created by cfb on 2018/6/20.
 */

public abstract class LoadingPage extends FrameLayout {
    private static final int STATE_UNDO = 0;
    private static final int STATE_LOADING = 1;
    private static final int STATE_ERROR = 2;
    private static final int STATE_EMPTY = 3;
    private static final int STATE_SUCCESS = 4;
    private int currenrState = STATE_UNDO;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private TextView mTextView;
    private View mSuccessView;

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

    /**
     * 初始化三个公用视图
     */
    private void initView() {
        //未加载和加载中页面
        if (mLoadingView == null) {
            mLoadingView = UIUtils.inflate(R.layout.loading_page_layout);
            addView(mLoadingView);
        }
        //加载失败
        if (mErrorView == null) {
            mErrorView = UIUtils.inflate(R.layout.error_page_layout);
            addView(mErrorView);
        }

        //加载成功，但是空数据
        if (mEmptyView == null) {
            mEmptyView = UIUtils.inflate(R.layout.empty_page_layout);
            addView(mEmptyView);
        }

        showCorrect();
    }

    /**
     * 根据当前mCurrentState的值来展示fragment页面
     * STATE_UNDO和STATE_LOADING  只展示mLoadingView
     * STATE_ERROR  只展示mErrorView
     * STATE_EMPTY  只展示mEmptyView
     * STATE_SUCCESS 只展示 mSuccessView
     */
    private void showCorrect() {
        mLoadingView.setVisibility(currenrState == STATE_UNDO || currenrState == STATE_LOADING ?
                VISIBLE : GONE);
        mErrorView.setVisibility(currenrState == STATE_ERROR ? VISIBLE : GONE);
        mEmptyView.setVisibility(currenrState == STATE_EMPTY ? VISIBLE : GONE);
        // TODO: 2018/6/21 状态的问题 ,只初始化一次?
        if (mSuccessView == null && currenrState == STATE_SUCCESS) {
            mSuccessView = onCreateSuccessView();
            if (mSuccessView != null) {
                addView(mSuccessView);

            }
        }
        if (mSuccessView != null) {
            mSuccessView.setVisibility(currenrState == STATE_SUCCESS ? VISIBLE : GONE);
        }

        /*if (currenrState == STATE_UNDO || currenrState == STATE_LOADING){
           mLoadingView.setVisibility(VISIBLE);
            mEmptyView.setVisibility(GONE);
            mErrorView.setVisibility(GONE);
            mTextView.setVisibility(GONE);
        }else if (currenrState == STATE_ERROR){
            mLoadingView.setVisibility(GONE);
            mEmptyView.setVisibility(GONE);
            mErrorView.setVisibility(VISIBLE);
            mTextView.setVisibility(GONE);
        }else if (currenrState == STATE_EMPTY){
            mLoadingView.setVisibility(GONE);
            mEmptyView.setVisibility(VISIBLE);
            mErrorView.setVisibility(GONE);
            mTextView.setVisibility(GONE);
        }else if (currenrState == STATE_SUCCESS){
            mLoadingView.setVisibility(GONE);
            mEmptyView.setVisibility(GONE);
            mErrorView.setVisibility(GONE);
            mTextView.setVisibility(VISIBLE);
        }*/
    }

    /**
     * @return加载请求成功数据页面的视图
     */
    public abstract View onCreateSuccessView();

    /**
     * 请求数据,并根据返回值更新当前fragment的视图
     */
    public void loadData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //请求数据,由子类完成
                LoadResult result = onLoad();
                if (result != null){
                    int state = result.getState();
                    currenrState = state;
                    UIUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showCorrect();
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 请求数据,运行在子线程
     * @return 请求数据的结果
     */
    public abstract LoadResult onLoad();

    /**
     * 枚举值,请求数据失败:RESULT_ERROR
     * RESULT_EMPTY :空数据
     * RESULT_SUCCESS:请求成功
     */
    public enum LoadResult{
        RESULT_ERROR(STATE_ERROR),
        RESULT_EMPTY(STATE_EMPTY),
        RESULT_SUCCESS(STATE_SUCCESS);
        int state;
        LoadResult(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }


}
