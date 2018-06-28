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

    private static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+
            File.separator+"googleplay"+File.separator+"download"+File.separator;
    public static DownloadInfo copyFromAppinfo(AppInfo appInfo){
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.id = appInfo.id;
        downloadInfo.name = appInfo.name;
        downloadInfo.packageName = appInfo.packageName;
        downloadInfo.downloadUrl = appInfo.downloadUrl;
        downloadInfo.size = appInfo.size;
        return downloadInfo;
    }

    public DownloadInfo  getDownloadState() {
        File file = getFile();
        DownloadInfo downloadInfo = new DownloadInfo();
        if (file==null){

        }else {
            if (file.length() == size){

            }else {

            }
        }
    }
    public File getFile(){
        String filePath = DOWNLOAD_PATH+packageName+".apk";
        File file = new File(filePath);
        if (file.exists()&&file.isFile()){
            return file;
        }
        return null;
    }
}
