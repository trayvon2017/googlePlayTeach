package com.example.cfb.googleplaytech.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.view.View;

import com.example.cfb.googleplaytech.global.GooglePlayApplication;

/**
 * Created by cfb on 2018/6/20.
 */

public class UIUtils {
    //提供获取context,handler,manThreadId的方法
    public static Context getContext(){
        return GooglePlayApplication.getContext();
    }
    public static Handler getHandler(){
        return GooglePlayApplication.getHandler();
    }
    public static int getMainThreadID(){
        return GooglePlayApplication.getMainThreadID();
    }
    //提供获取资源文件内容的方法

    /**
     *
     * @param resourceId 资源id
     * @return 返回资源id对应的字符串内容
     */
    public static String getString(int resourceId){
        return getContext().getResources().getString(resourceId);
    }

    /**
     * @param resourceId 资源id
     * @return 返回资源id对应的字符串数组
     */
    public static String[] getStringArray(int resourceId){
        return getContext().getResources().getStringArray(resourceId);
    }

    /**
     *
     * @param resourceId 资源id
     * @return 返回资源id对应的drawable
     */
    public static Drawable getDrawable(int resourceId){
        return getContext().getResources().getDrawable(resourceId);
    }

    /**
     *
     * @param resourceId 资源id
     * @return 返回资源id对应的颜色色值
     */
    public static int getColor(int resourceId){
        return getContext().getResources().getColor(resourceId);
    }

    /**
     *
     * @param resourceId 资源id
     * @return 返回资源id对应的dimen像素值
     */
    public static int getDimen(int resourceId){
        return getContext().getResources().getDimensionPixelSize(resourceId);
    }
    //dp和px互换

    /**
     * 通过设备的像素密度把dip转换为pix
     * @param dip dp值
     * @return 转换之后的像素值
     */
    public static int dip2px(float dip){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dip +0.5f);
    }
    /**
     * 通过设备的像素密度把dip转换为pix
     * @param pix dp值
     * @return 转换之后的像素值
     */
    public static float px2dip(int pix){
        float density = getContext().getResources().getDisplayMetrics().density;
        return pix/density;
    }
    //加载布局文件

    /**
     * 根据布局文件的id加载对应的view
     * @param layoutId 布局id
     * @return 加载到的view
     */
    public static View inflate(int layoutId){
        return View.inflate(getContext(),layoutId,null);
    }

    /**
     *
     * @return 判断当前线程是否运行在主线程 true代表运行在主线程可以更新ui
     */
    public static boolean isRunOnUiThread(){
        int myTid = Process.myTid();
        return myTid == getMainThreadID();
    }

    /**
     * 执行更新UI的操作,当前线程为主线程的时候回直接执行操作,
     * 当前线不是主线程的时候发给handler去执行
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable){
        if (isRunOnUiThread()){
            runnable.run();
        }else {
            getHandler().post(runnable);
        }
    }

    /**
     * 返回色值列表
     * @param resourceId
     * @return
     */
    public static ColorStateList getColorStateList(int resourceId) {
        return getContext().getResources().getColorStateList(resourceId);
    }
}
