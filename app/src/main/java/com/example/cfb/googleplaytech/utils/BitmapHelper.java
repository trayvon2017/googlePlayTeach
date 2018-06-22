package com.example.cfb.googleplaytech.utils;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by fbfatboy on 2018/6/22.
 */

public class BitmapHelper {

    private static BitmapUtils mBitmapUtils = null;

    // 单例, 懒汉模式
    public static BitmapUtils getBitmapUtils() {
        if (mBitmapUtils == null) {
            synchronized (BitmapHelper.class) {
                if (mBitmapUtils == null) {
                    mBitmapUtils = new BitmapUtils(UIUtils.getContext());
                }
            }
        }

        return mBitmapUtils;
    }
}
/*public class BitmapHelper {
    private static BitmapUtils mBitmapUtils;

    public static BitmapUtils getBitmapUtils() {
        if (mBitmapUtils==null){
            synchronized (BitmapHelper.class){
                if (mBitmapUtils == null){
                    mBitmapUtils = new BitmapUtils(UIUtils.getContext());
                }
            }
        }
        return mBitmapUtils;
    }

}*/
