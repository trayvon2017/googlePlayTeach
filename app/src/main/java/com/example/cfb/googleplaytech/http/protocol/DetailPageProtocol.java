package com.example.cfb.googleplaytech.http.protocol;

import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.domain.SubjectInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by fbfatboy on 2018/6/22.
 */

public class DetailPageProtocol extends BaseProtocol<AppInfo> {
    private String mParams;
    @Override
    protected String getKey() {
        return "detail";
    }

    public void setParams (String params){
        mParams = params;
    }

    /**
     * 需要先调用setParams
     * @return
     */
    @Override
    protected String getUrlParams() {
        return mParams;
    }

    @Override
    protected AppInfo processJson(String json) {
        try {
            JSONObject jsonObj = new JSONObject(json);
            AppInfo appInfo = new AppInfo();
            appInfo.author = jsonObj.getString("author");
            appInfo.date = jsonObj.getString("date");
            appInfo.des = jsonObj.getString("des");
            appInfo.downloadNum = jsonObj.getString("downloadNum");
            appInfo.downloadUrl = jsonObj.getString("downloadUrl");
            appInfo.iconUrl = jsonObj.getString("iconUrl");
            appInfo.id = jsonObj.getString("id");
            appInfo.name = jsonObj.getString("name");
            appInfo.packageName = jsonObj.getString("packageName");
            appInfo.stars = (float) jsonObj.getDouble("stars");
            appInfo.version = jsonObj.getString("version");
            appInfo.size = jsonObj.getLong("size");
            JSONArray safeJsArray = (JSONArray) jsonObj.get("safe");
            appInfo.safe = new ArrayList<AppInfo.SafeInfo>();
            for (int i = 0;i<safeJsArray.length();i++){
                AppInfo.SafeInfo safeInfo = new AppInfo.SafeInfo();
                JSONObject json1 = (JSONObject) safeJsArray.get(i);
                safeInfo.safeDes = json1.getString("safeDes");
                safeInfo.safeDesUrl = json1.getString("safeDesUrl");
                safeInfo.safeUrl = json1.getString("safeUrl");
                appInfo.safe.add(safeInfo);
            }
            JSONArray screenJsArray = (JSONArray) jsonObj.get("screen");
            appInfo.screen = new ArrayList<String>();
            for (int i = 0;i<screenJsArray.length();i++){
                String s = (String) screenJsArray.get(i);
                appInfo.screen.add(s);
            }
            return appInfo;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
