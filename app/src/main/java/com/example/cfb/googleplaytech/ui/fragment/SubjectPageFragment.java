package com.example.cfb.googleplaytech.ui.fragment;

import android.mtp.MtpConstants;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cfb.googleplaytech.domain.SubjectInfo;
import com.example.cfb.googleplaytech.http.protocol.SubjectPageProtocol;
import com.example.cfb.googleplaytech.ui.Holder.BaseHolder;
import com.example.cfb.googleplaytech.ui.Holder.SubjectPageHolder;
import com.example.cfb.googleplaytech.ui.adapter.MybaseAdapter;
import com.example.cfb.googleplaytech.ui.view.LoadingPage;
import com.example.cfb.googleplaytech.ui.view.MyListView;
import com.example.cfb.googleplaytech.utils.UIUtils;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by cfb on 2018/6/20.
 */

public class SubjectPageFragment extends BaseFragment {


    private ArrayList<SubjectInfo> mSubjectInfos;

    @Override
    public LoadingPage.LoadResult onLoad() {
        SubjectPageProtocol subjectPageProtocol = new SubjectPageProtocol();
        mSubjectInfos = subjectPageProtocol.getData(0);
//        Log.d(TAG, "onLoad: 主题个数"+mSubjectInfos.size());
        return checkData(mSubjectInfos);
    }

    @Override
    public View onCreateSuccessView() {

        /*MyListView listView = new MyListView(UIUtils.getContext());

        listView.setAdapter(new SubjectAdapter(mSubjectInfos));*/

        TextView textView = new TextView(UIUtils.getContext());
        textView.setText(getClass().getSimpleName());
        return textView;
    }
    class SubjectAdapter extends MybaseAdapter<SubjectInfo>{

        public SubjectAdapter(ArrayList<SubjectInfo> mData) {
            super(mData);
        }

        @Override
        public BaseHolder initHolder() {
            return new SubjectPageHolder();
        }

        @Override
        public ArrayList<SubjectInfo> onLoadMore() {
            SubjectPageProtocol subjectPageProtocol = new SubjectPageProtocol();
            ArrayList<SubjectInfo> moreData = subjectPageProtocol.getData(getListSize());
            return moreData;
        }

        @Override
        public boolean hasMore() {
            return false;
        }
    }
}
