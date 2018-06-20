package com.example.cfb.googleplaytech.ui.fragment;

import android.view.View;

import com.example.cfb.googleplaytech.ui.view.LoadingPage;

/**
 * Created by cfb on 2018/6/20.
 */

public class GamePageFragment extends BaseFragment {


    @Override
    public LoadingPage.LoadResult onLoad() {
        return LoadingPage.LoadResult.RESULT_EMPTY;
    }

    @Override
    public View onCreateSuccessView() {
        return null;
    }
}
