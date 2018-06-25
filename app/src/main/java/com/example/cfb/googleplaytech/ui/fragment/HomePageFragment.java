package com.example.cfb.googleplaytech.ui.fragment;

import android.os.SystemClock;
import android.view.View;

import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.http.protocol.HomePageProtocol;
import com.example.cfb.googleplaytech.ui.Holder.BaseHolder;
import com.example.cfb.googleplaytech.ui.Holder.HomeHeaderHolder;
import com.example.cfb.googleplaytech.ui.Holder.HomePageHolder;
import com.example.cfb.googleplaytech.ui.adapter.MybaseAdapter;
import com.example.cfb.googleplaytech.ui.view.LoadingPage;
import com.example.cfb.googleplaytech.ui.view.MyListView;
import com.example.cfb.googleplaytech.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by cfb on 2018/6/20.
 */

public class HomePageFragment extends BaseFragment {
    String[] strs = new String[20];
//    ArrayList<String> mArraylist = new ArrayList<String>();
    private ArrayList<AppInfo> data;
    private ArrayList<String> mPictures;

    @Override
    public LoadingPage.LoadResult onLoad() {
        HomePageProtocol homePageProtocol = new HomePageProtocol();
        data = homePageProtocol.getData(0);
        mPictures = homePageProtocol.getPictures();
        return checkData(data);
    }

    @Override
    public View onCreateSuccessView() {
        MyListView listView = new MyListView(UIUtils.getContext());
        listView.setAdapter(new MyAdapter(data));
        HomeHeaderHolder headerHolder = new HomeHeaderHolder();
        listView.addView(headerHolder.itemView);
//        listView.setDivider(null);
       /* TextView textView = new TextView(UIUtils.getContext());
        textView.setText(getClass().getSimpleName());*/
        return listView;
    }
    private class MyAdapter extends MybaseAdapter<AppInfo>{


        public MyAdapter(ArrayList<AppInfo> mArraylist) {
            super(mArraylist);
        }

        @Override
        public BaseHolder initHolder(int position) {
            return new HomePageHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
            HomePageProtocol homePageProtocol = new HomePageProtocol();
            ArrayList<AppInfo> moreData = homePageProtocol.getData(getListSize());
            ArrayList<String> list= new ArrayList<String>();
            for (int i=0;i<10;i++){
                list.add("moreData"+i);
            }
            SystemClock.sleep(2000);
            return moreData;
        }


    }
}
