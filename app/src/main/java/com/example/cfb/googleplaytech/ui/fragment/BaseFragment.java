package com.example.cfb.googleplaytech.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cfb.googleplaytech.ui.view.LoadingPage;
import com.example.cfb.googleplaytech.utils.UIUtils;

/**
 * Created by cfb on 2018/6/20.
 */

public abstract class BaseFragment extends Fragment {
    public LoadingPage mLoadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoadingPage = new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {
                return BaseFragment.this.onCreateSuccessView();
            }

            @Override
            public LoadResult onLoad() {
                return BaseFragment.this.onLoad();
            }
        };

        return mLoadingPage;
    }

    public abstract LoadingPage.LoadResult onLoad();

    public abstract View onCreateSuccessView();

    /**
     * 加载数据,回调LoadingPage中的加载数据方法
     */
    public void loadData(){
        if (mLoadingPage != null){
            mLoadingPage.loadData();
        }

    }



}
