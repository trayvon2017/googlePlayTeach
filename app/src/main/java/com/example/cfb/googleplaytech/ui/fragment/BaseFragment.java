package com.example.cfb.googleplaytech.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cfb.googleplaytech.ui.view.LoadingPage;
import com.example.cfb.googleplaytech.utils.UIUtils;

import java.util.ArrayList;

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

    /**
     * 运行在子线程,向服务器请求数据,然后返回状态值
     * @return RESULT_ERROR请求失败  ; RESULT_EMPTY 空数据;RESULT_SUCCESS请求成功
     */
    public abstract LoadingPage.LoadResult onLoad();

    public LoadingPage.LoadResult checkData(ArrayList list){
        if (list!=null){
            return LoadingPage.LoadResult.RESULT_SUCCESS;
        }

        return LoadingPage.LoadResult.RESULT_ERROR;
    }

    /**
     *
     * @return 加载当前fragment对象对应的视图
     */
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
