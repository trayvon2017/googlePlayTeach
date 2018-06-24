package com.example.cfb.googleplaytech.http.protocol;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.cfb.googleplaytech.http.HttpHelper;
import com.example.cfb.googleplaytech.utils.IOUtils;
import com.example.cfb.googleplaytech.utils.StringUtils;
import com.example.cfb.googleplaytech.utils.UIUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by fbfatboy on 2018/6/21.
 */

public abstract class BaseProtocol<T> {
    public T getData(int index){
        String cache = getCache(index);
        Log.d(TAG, "getData: cache"+cache);
        if (StringUtils.isEmpty(cache)){
            cache = getDataFromServer(index);
        }
        if (!StringUtils.isEmpty(cache)){
            return processJson(cache);
        }
       return null;
    }

    private String getCache(int index) {
        String subsURL = getSubUrl(index);
        String path = UIUtils.getContext().getCacheDir().getPath();
        File file = new File(path, getKey()+subsURL);
        if (file.exists()){
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String deadLineStr = reader.readLine();
                float deadLine = Float.parseFloat(deadLineStr);
                if (deadLine<System.currentTimeMillis()){
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while((line=reader.readLine())!=null){
                        sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }
        }

        return null;
    }


    public String getDataFromServer(int index){
        String subsURL = getSubUrl(index);

        HttpHelper.HttpResult result = HttpHelper.get(HttpHelper.URL +getKey()+ subsURL);
        if (result !=null){
            String json = result.getString();
            saveCache(json,subsURL);
            return json;
        }
        return null;

    }

    protected abstract String getKey();

    @NonNull
    private String getSubUrl(int index) {
        //String URL = "http://127.0.0.1:8090/ ?index=0&
        return "?index=" + index + getUrlParams();
    }

    /**
     * 缓存json到本地
     * @param json
     * @param subsURL
     */
    private void saveCache(String json, String subsURL) {
        if (!StringUtils.isEmpty(json)) {
            String path = UIUtils.getContext().getCacheDir().getPath();
            File file = new File(path, getKey() + subsURL);
            FileWriter writer = null;
            try {
                writer = new FileWriter(file);
                //设置有效期
                long deadLine = System.currentTimeMillis() + 30 * 60 * 100;
                writer.write(deadLine + "\n");

                writer.write(json);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(writer);
            }
        }
    }

    /**
     * @return 如果连接需要带参数,此处返回"&a=2&b=3"格式的字符串,否则返回""
     */
    protected abstract String getUrlParams();

    /**
     * 处理json为要使用的对象
     * @param json
     * @return
     */
    protected abstract T processJson(String json);

}
