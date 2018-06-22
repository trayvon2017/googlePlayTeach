package com.example.cfb.googleplaytech.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.cfb.googleplaytech.ui.view.LoadingPage;
import com.example.cfb.googleplaytech.utils.UIUtils;

/**
 * Created by cfb on 2018/6/20.
 */

public class GamePageFragment extends BaseFragment {


    @Override
    public LoadingPage.LoadResult onLoad() {
        return LoadingPage.LoadResult.RESULT_SUCCESS;
    }

    @Override
    public View onCreateSuccessView() {
        TextView textView = new TextView(UIUtils.getContext());
        textView.setText(getClass().getSimpleName());
        return textView;
    }
}
