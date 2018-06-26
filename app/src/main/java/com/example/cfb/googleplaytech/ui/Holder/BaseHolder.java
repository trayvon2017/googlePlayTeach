package com.example.cfb.googleplaytech.ui.Holder;

import android.util.Log;
import android.view.View;


import static android.content.ContentValues.TAG;

/**
 * Created by fbfatboy on 2018/6/21.
 */

public abstract class BaseHolder<T> {
    public View mRootView;

    public BaseHolder(){
        Log.d(TAG, "BaseHolder: "+getClass().getSimpleName());
        mRootView = initItemView();
        findViewById();
        mRootView.setTag(this);
    }

    protected abstract View initItemView();


    public abstract void setData(T t) ;

    public abstract void findViewById();


}
