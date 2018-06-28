package com.example.cfb.googleplaytech.manager;

import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

/**线程管理
 * Created by cfb on 2018/6/28.
 */

public class ThreadManager {
    private static ThreadPool mThreadPool;

    /**
     * 单例,懒汉模式获取线程池
     * @return
     */
    public static ThreadPool getThreadPool() {
        if (mThreadPool == null){
            synchronized (ThreadManager.class){
                if (mThreadPool == null){
                    int cpuCount = Runtime.getRuntime().availableProcessors();
                    Log.d(TAG, "getThreadPool: CPU数量"+cpuCount);
                    mThreadPool = new ThreadPool(10,10,1L);
                }
            }
        }
        return mThreadPool;
    }
    //线程池
    public  static class ThreadPool{

        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;

        private ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        private ThreadPoolExecutor executor;

        /*创建ThreadPoolExecutor时候的参数解释
        int corePoolSize   核心线程数,int maximumPoolSize  最大线程数,
        long keepAliveTime  线程休息时间 ,TimeUnit unit  休息时间的时间单位,
        BlockingQueue<Runnable> workQueue 线程队列,添加任务会暂时被存储在这里,
        ThreadFactory threadFactory 生产线程的工厂,
        RejectedExecutionHandler handler  线程异常处理的策略*/

        /**
         *
         * @param runnable 需要开辟线程运行的操作
         */
        public void execute(Runnable runnable){
            if (executor == null){
                executor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, TimeUnit.SECONDS,
                        new LinkedBlockingDeque<Runnable>(),
                        Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.AbortPolicy());
            }
            executor.execute(runnable);
        }
        public void cancel(Runnable runnable){
            if (executor != null){
                executor.remove(runnable);
            }
        }


    }
}
