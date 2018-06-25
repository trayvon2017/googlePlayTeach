package com.example.cfb.googleplaytech.ui.fragment;

import android.view.View;

import com.example.cfb.googleplaytech.domain.ClassificationInfo;
import com.example.cfb.googleplaytech.http.protocol.ClassificationPageProtocol;
import com.example.cfb.googleplaytech.ui.Holder.BaseHolder;
import com.example.cfb.googleplaytech.ui.Holder.ClassificationItemHolder;
import com.example.cfb.googleplaytech.ui.Holder.ClassificationTitleHolder;
import com.example.cfb.googleplaytech.ui.adapter.MybaseAdapter;
import com.example.cfb.googleplaytech.ui.view.LoadingPage;
import com.example.cfb.googleplaytech.ui.view.MyListView;
import com.example.cfb.googleplaytech.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by cfb on 2018/6/20.
 */

public class ClassificationPageFragment extends BaseFragment {


    private ArrayList<ClassificationInfo> data;

    @Override
    public LoadingPage.LoadResult onLoad() {
        ClassificationPageProtocol classificationPageProtocol = new ClassificationPageProtocol();
        data = classificationPageProtocol.getData(0);
        return checkData(data);
    }

    @Override
    public View onCreateSuccessView() {
        MyListView myListView = new MyListView(UIUtils.getContext());
        myListView.setAdapter(new ClassificationAdapter(data));
        return myListView;
    }

    private class ClassificationAdapter extends MybaseAdapter<ClassificationInfo> {
        private static final int ITEM_TYPE_TITLE = 2;

        @Override
        public boolean hasMore() {
            return false;
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position==data.size()){
                return super.getItemViewType(position);
            }else {
                ClassificationInfo info = getItem(position);
                if (info.isTitle){
                    return ITEM_TYPE_TITLE;
                }else{
                    return ITEM_TYPE_NORMAL;
                }
            }
        }

        public ClassificationAdapter(ArrayList<ClassificationInfo> mData) {
            super(mData);
        }


        @Override
        public BaseHolder initHolder(int position) {
            // TODO: 2018/6/25 title/item使用不同的BaseHolder
            if (getItemViewType(position) == ITEM_TYPE_TITLE){
                return new ClassificationTitleHolder();
            }else{
                return new ClassificationItemHolder();
            }
        }

        @Override
        public ArrayList<ClassificationInfo> onLoadMore() {
            return null;
        }
    }
}
