package com.example.cfb.googleplaytech.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by cfb on 2018/6/20.
 */

public class GooglePlayApplication extends Application {
    private static Context context;
    private static Handler handler;
    private static int mainThreadID;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThreadID = android.os.Process.myTid();
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadID() {
        return mainThreadID;
    }
}
