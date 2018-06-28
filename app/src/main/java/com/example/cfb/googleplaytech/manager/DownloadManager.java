package com.example.cfb.googleplaytech.manager;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.domain.DownloadInfo;
import com.example.cfb.googleplaytech.http.HttpHelper;
import com.example.cfb.googleplaytech.utils.IOUtils;
import com.example.cfb.googleplaytech.utils.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cfb on 2018/6/28.
 */

public class DownloadManager {
    //懒加载的单例,不用考虑线程安全的问题
    private static DownloadManager mDM = new DownloadManager();

    private DownloadManager() {
    }

    public static DownloadManager getInstance() {
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
    //下载信息的集合,使用线程安全的HASHMAP CONCURRENTHASHMAP
    private HashMap<String, DownloadInfo> mDownloadInfoMap = new HashMap<String, DownloadInfo>();
    //下载任务的集合
    private HashMap<String, DownloadTask> mDownloadTaskMap = new HashMap<String, DownloadTask>();

    /**
     * 监听类,用于实现监听,状态变化和下载进度
     */
    public interface DownloadObserver {
        void onDownloadStateChanged(DownloadInfo info);

        void onDownloadProgress(DownloadInfo info);

    }

    /**
     * 通知观察者下载状态变化
     */
    private void notifyDownloadStateChanged(DownloadInfo info) {
        for (DownloadObserver observer :
                mOservers) {
            observer.onDownloadStateChanged(info);
        }
    }

    /**
     * 通知观察者下载进度
     */
    private void notifyDownloadProgress(DownloadInfo info) {
        for (DownloadObserver observer :
                mOservers) {
            observer.onDownloadProgress(info);
        }
    }

    /**
     * 注册监听
     *
     * @param observer
     */
    public void registerDownloadObserver(DownloadObserver observer) {
        if (!mOservers.contains(observer) && observer != null) {
            mOservers.add(observer);
        }
    }

    /**
     * 取消注册
     *
     * @param observer
     */
    public void unRegisterDownloadObserver(DownloadObserver observer) {
        if (mOservers.contains(observer) && observer != null) {
            mOservers.remove(observer);
        }
    }

    /**
     * @param appinfo
     */
    public synchronized void  download(AppInfo appinfo) {
        String id = appinfo.id;
        //之前有可能已经下载过,所以需要判断
        DownloadInfo downloadInfo;
        if (mDownloadInfoMap.containsKey(id)) {
            downloadInfo = mDownloadInfoMap.get(id);
        } else {
            //拷贝一个downloadInfo
            downloadInfo = DownloadInfo.copy(appinfo);
            mDownloadInfoMap.put(id, downloadInfo);//放入到放在的map中
        }
        //状态需要改变
        downloadInfo.currentState = DOWNLOAD_STATE_WAIT;//不一定正在下载,因为可能在排队
        notifyDownloadStateChanged(downloadInfo);
        Log.d("DownloadManager", "download: 准备下载");
        //新建下载任务,安排线程池执行下载
        DownloadTask downloadTask = new DownloadTask(downloadInfo);

        ThreadManager.getThreadPool().execute(downloadTask);

        //添加任务到任务map
        mDownloadTaskMap.put(id, downloadTask);


    }

    class DownloadTask implements Runnable {
        DownloadInfo downloadInfo;

        DownloadTask(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void run() {
            Log.d("DownloadManager", "run: 开始下载了");
            downloadInfo.currentState =DOWNLOAD_STATE_DOING;
            notifyDownloadStateChanged(downloadInfo);
            //判断文件存不存在
            File file = new File(downloadInfo.getFilePath());
            HttpHelper.HttpResult result;
            //文件不存在;存储的长度和本地不一样;本地长度为0
            if (!file.exists() || downloadInfo.currentPos != file.length() || file.length() == 0) {
                //删除本地文件-没有的时候也没关系,不会报错
                file.delete();
                //从头开始下载
                result = HttpHelper.download(HttpHelper.URL + "download?name=" +
                        downloadInfo.downloadUrl);

            } else {
                //断点下载
                result = HttpHelper.download(HttpHelper.URL + "download?name=" +
                        downloadInfo.downloadUrl + "&range=" + downloadInfo.currentPos);
            }
            InputStream inputStream ;
            FileOutputStream outputStream =null;
            if (result!=null&&(inputStream=result.getInputStream())!=null){
                try {
                    //要在原有文件上追加数据,否则已有文件的时候回写失败new FileOutputStream(file,true)
                    outputStream =
                            new FileOutputStream(file,true);
                    byte[] buffer = new byte[1024*4];
                    int length ;
                    while(((length = inputStream.read(buffer))!=-1)&&
                            downloadInfo.currentState == DOWNLOAD_STATE_DOING){
                        outputStream.write(buffer,0,length);
                        outputStream.flush();
                        //更新下载进度
                        downloadInfo.currentPos += length;
                        notifyDownloadProgress(downloadInfo);

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    IOUtils.close(inputStream);
                    IOUtils.close(outputStream);
                }
                if (file.length()==downloadInfo.size){
                    //下载成功
                    downloadInfo.currentState =DOWNLOAD_STATE_SUCCESS;
                    notifyDownloadStateChanged(downloadInfo);
                }else if (downloadInfo.currentState==DOWNLOAD_STATE_PAUSE){
                    //中途暂停
                    notifyDownloadStateChanged(downloadInfo);
                }else {
                    //下载失败,
                    file.delete();
                    downloadInfo.currentState = DOWNLOAD_STATE_ERROR;
                    downloadInfo.currentPos = 0;
                    notifyDownloadStateChanged(downloadInfo);
                }
            }else {
                //网络异常
                file.delete();
                downloadInfo.currentState = DOWNLOAD_STATE_ERROR;
                downloadInfo.currentPos = 0;
                notifyDownloadStateChanged(downloadInfo);
            }
            //从集合中移除
            mDownloadTaskMap.remove(downloadInfo.id);
        }
    }

    public synchronized void pause(AppInfo appinfo) {
        String id = appinfo.id;
        DownloadInfo downloadInfo = mDownloadInfoMap.get(id);
        //状态是等待下载或者正在下载的时候暂停/取消
        if (downloadInfo.currentState == DOWNLOAD_STATE_DOING ||
                downloadInfo.currentState == DOWNLOAD_STATE_WAIT) {
            downloadInfo.currentState = DOWNLOAD_STATE_PAUSE;
            notifyDownloadStateChanged(downloadInfo);
            DownloadTask downloadTask = mDownloadTaskMap.get(id);
            if (downloadTask !=null){
                //这个操作仅仅是把从他从线程池的队列(未执行)里面移除
                // 所以如果当前下载已经在执行,这个会不起作用
                // 所以应该在下载的时候在再判断
                ThreadManager.getThreadPool().cancel(downloadTask);
            }
        }

    }

    public synchronized void install(AppInfo appInfo) {
        DownloadInfo downloadInfo = mDownloadInfoMap.get(appInfo.id);
        if (downloadInfo != null) {
            // 跳到系统的安装页面进行安装
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + downloadInfo.path),
                    "application/vnd.android.package-archive");
            UIUtils.getContext().startActivity(intent);
        }
    }
    public DownloadInfo getDownloadInfo(AppInfo appInfo){
        DownloadInfo downloadInfo = mDownloadInfoMap.get(appInfo.id);
        return downloadInfo;
    }

}
