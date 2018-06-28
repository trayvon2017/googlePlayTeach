package com.example.cfb.googleplaytech.domain;

import android.os.Environment;

import com.example.cfb.googleplaytech.manager.DownloadManager;
import com.lidroid.xutils.db.sqlite.DbModelSelector;

import java.io.File;

/**
 * Created by cfb on 2018/6/28.
 */

public class DownloadInfo {
    public static final int NONE = 1;
    public static final int COMPLETED = 2;
    public static final int UNCOMPLETED = 3;
    public String id;
    public String name;
    public String packageName;
    public String downloadUrl;
    public long size;
    public long currentPos;
    public int currentState;
    public static final String GOOGLE_MARKET = "google_market";
    public static final String DOWNLOAD = "download";

    public String path;//存储路径

    /**
     *
     * @return 下载进度 浮点数
     */
    public float getProgress(){
        if (size ==0){
            return 0;
        }
        return currentPos/(float)size;
    }
    public String getFilePath(){
        StringBuffer sb = new StringBuffer();
        String sdCard = Environment.getExternalStorageDirectory().getAbsolutePath();
        sb.append(sdCard);
        sb.append(File.separator);
        sb.append(GOOGLE_MARKET);
        sb.append(File.separator);
        sb.append(DOWNLOAD);
        if (createFileDir(sb.toString())){
            sb.append(File.separator);
            sb.append(name+".apk");
            return sb.toString();
        }
        return null;
    }

    /**
     * 如果文件夹不存在需要先创建文件夹
     * @param string
     * @return
     */
    private boolean createFileDir(String string) {
        File file = new File(string);
        if (!file.exists()||!file.isDirectory()){
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 从appinfo 里面拷贝出来一个downloadInfo
     * @param appInfo
     * @return
     */
    public static DownloadInfo copy(AppInfo appInfo){
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.id = appInfo.id;
        downloadInfo.name = appInfo.name;
        downloadInfo.packageName = appInfo.packageName;
        downloadInfo.downloadUrl = appInfo.downloadUrl;
        downloadInfo.size = appInfo.size;
        //其他appinfo里面没有的也初始化一下
        downloadInfo.currentPos = 0;
        downloadInfo.currentState = DownloadManager.DOWNLOAD_STATE_UNDO;
        downloadInfo.path = downloadInfo.getFilePath();

        return downloadInfo;
    }
}
