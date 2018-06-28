package com.example.cfb.googleplaytech.manager;

import java.util.ArrayList;

/**
 * Created by cfb on 2018/6/28.
 */

public class DownloadManager {
    //懒加载的单例,不用考虑线程安全的问题
    private static DownloadManager mDM= new DownloadManager();
    public static DownloadManager getInstance(){
        return mDM;
    }
    //下载状态:未下载,等待下载,正在下载,暂停下载,下载出错,下载成功
    public static final int DOWNLOAD_STATE_UNDO = 1;
    public static final int DOWNLOAD_STATE_WAIT = 2;
    public static final int DOWNLOAD_STATE_DOING = 3;
    public static final int DOWNLOAD_STATE_PAUSE = 4;
    public static final int DOWNLOAD_STATE_ERROR = 5;
    public static final int DOWNLOAD_STATE_SUCCESS = 6;
    //存储观察者
    private ArrayList<DownloadObserver> mOservers = new ArrayList<DownloadObserver>();


    /**
     * 监听类,用于实现监听,状态变化和下载进度
     */
    interface DownloadObserver {
        void onDownloadStateChanged();

        void onDownloadProgress();

    }

    /**
     * 注册监听
     * @param observer
     */
    public void registerDownloadObserver(DownloadObserver observer) {
        if (!mOservers.contains(observer) && observer != null) {
            mOservers.add(observer);
        }
    }

    /**
     * 取消注册
     * @param observer
     */
    public void unRegisterDownloadObserver(DownloadObserver observer) {
        if (mOservers.contains(observer) && observer != null) {
            mOservers.remove(observer);
        }
    }

}
