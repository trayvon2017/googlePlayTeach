package com.example.cfb.googleplaytech.ui.fragment;

import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;

import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.http.protocol.HomePageProtocol;
import com.example.cfb.googleplaytech.ui.Holder.BaseHolder;
import com.example.cfb.googleplaytech.ui.Holder.HomeHeaderHolder;
import com.example.cfb.googleplaytech.ui.Holder.HomePageHolder;
import com.example.cfb.googleplaytech.ui.activity.AppDetailActivity;
import com.example.cfb.googleplaytech.ui.activity.MainActivity;
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

        HomeHeaderHolder headerHolder = new HomeHeaderHolder();
        //传递数据给HeaderHolder
        headerHolder.setData(mPictures);
        listView.addHeaderView(headerHolder.itemView);
        listView.setAdapter(new MyAdapter(data));
        //listView设置条目点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo appInfo = data.get(position - 1);
                if (appInfo!=null){
                    Intent intent = new Intent(getContext(), AppDetailActivity.class);
                    intent.putExtra("packageName",appInfo.packageName);
                    getContext().startActivity(intent);
                }

            }
        });
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
