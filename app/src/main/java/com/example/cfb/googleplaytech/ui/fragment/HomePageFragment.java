package com.example.cfb.googleplaytech.ui.fragment;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.http.protocol.HomePageProtocol;
import com.example.cfb.googleplaytech.ui.Holder.BaseHolder;
import com.example.cfb.googleplaytech.ui.Holder.HomePageHolder;
import com.example.cfb.googleplaytech.ui.adapter.MybaseAdapter;
import com.example.cfb.googleplaytech.ui.view.LoadingPage;
import com.example.cfb.googleplaytech.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by cfb on 2018/6/20.
 */

public class HomePageFragment extends BaseFragment {
    String[] strs = new String[20];
//    ArrayList<String> mArraylist = new ArrayList<String>();
    private ArrayList<AppInfo> data;

    @Override
    public LoadingPage.LoadResult onLoad() {
        HomePageProtocol homePageProtocol = new HomePageProtocol();
        data = homePageProtocol.getData(0);
        if (data!=null){
            return LoadingPage.LoadResult.RESULT_SUCCESS;
        }
       /* // TODO: 2018/6/22 加载数据
        for (int i =0;i<20;i++){
            strs[i] = "text数据"+i;
            mArraylist.add("text数据"+i);
        }*/
        return LoadingPage.LoadResult.RESULT_ERROR;
    }

    @Override
    public View onCreateSuccessView() {
        ListView listView = new ListView(UIUtils.getContext());
        listView.setAdapter(new MyAdapter(data));
        TextView textView = new TextView(UIUtils.getContext());
        textView.setText(getClass().getSimpleName());
        return listView;
    }

    private class MyAdapter extends MybaseAdapter<AppInfo>{


        public MyAdapter(ArrayList<AppInfo> mArraylist) {
            super(mArraylist);
        }

        @Override
        public BaseHolder initHolder() {
            return new HomePageHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
            HomePageProtocol homePageProtocol = new HomePageProtocol();
            ArrayList<AppInfo> moreData = homePageProtocol.getData(HomePageFragment.this.data.size());
            /*ArrayList<String> list= new ArrayList<String>();
            for (int i=0;i<10;i++){
                list.add("moreData"+i);
            }*/
            SystemClock.sleep(2000);
            return moreData;
        }


    }

}
