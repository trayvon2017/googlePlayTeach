package com.example.cfb.googleplaytech.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.cfb.googleplaytech.ui.view.LoadingPage;
import com.example.cfb.googleplaytech.utils.UIUtils;

import org.w3c.dom.Text;

/**
 * Created by cfb on 2018/6/20.
 */

public class ApplicationPageFragment extends BaseFragment {


    @Override
    public LoadingPage.LoadResult onLoad() {
        return LoadingPage.LoadResult.RESULT_ERROR;
    }

    @Override
    public View onCreateSuccessView() {
        return null;
    }
}
