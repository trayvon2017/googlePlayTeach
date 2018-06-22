package com.example.cfb.googleplaytech.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.http.protocol.AppPageProtocol;
import com.example.cfb.googleplaytech.ui.Holder.AppPageHolder;
import com.example.cfb.googleplaytech.ui.Holder.BaseHolder;
import com.example.cfb.googleplaytech.ui.adapter.MybaseAdapter;
import com.example.cfb.googleplaytech.ui.view.LoadingPage;
import com.example.cfb.googleplaytech.ui.view.MyListView;
import com.example.cfb.googleplaytech.utils.UIUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by cfb on 2018/6/20.
 */

public class ApplicationPageFragment extends BaseFragment {


    private ArrayList<AppInfo> mApps;

    @Override
    public LoadingPage.LoadResult onLoad() {
        AppPageProtocol appPageProtocol = new AppPageProtocol();
        mApps = appPageProtocol.getData(0);
        return checkData(mApps);
    }

    @Override
    public View onCreateSuccessView() {
        /*MyListView listView = new MyListView(UIUtils.getContext());
        listView.setAdapter(new MyAppAdapter(mApps));*/

        TextView textView = new TextView(UIUtils.getContext());
        textView.setText(getClass().getSimpleName());
        return textView;
    }
    class MyAppAdapter extends MybaseAdapter<AppInfo>{

        public MyAppAdapter(ArrayList<AppInfo> mData) {
            super(mData);
        }

        @Override
        public BaseHolder initHolder() {
            return new AppPageHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
            AppPageProtocol appPageProtocol = new AppPageProtocol();
            ArrayList<AppInfo> moreData = appPageProtocol.getData(getListSize());
            return moreData;
        }
    }
}
