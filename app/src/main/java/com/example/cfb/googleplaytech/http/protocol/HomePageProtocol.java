package com.example.cfb.googleplaytech.http.protocol;

import com.example.cfb.googleplaytech.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by fbfatboy on 2018/6/22.
 */

public class HomePageProtocol extends BaseProtocol<ArrayList<AppInfo>> {
    @Override
    protected String getKey() {
        return "home";
    }

    @Override
    protected String getUrlParams() {
        return "";
    }

    @Override
    protected ArrayList<AppInfo> processJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = (JSONArray) jsonObject.get("list");
            ArrayList<AppInfo> apps = new ArrayList<AppInfo>();
            for (int i =0;i<jsonArray.length();i++){
                AppInfo appInfo = new AppInfo();
                JSONObject obj = (JSONObject) jsonArray.get(i);
                appInfo.des = obj.getString("des");
                appInfo.downloadUrl = obj.getString("downloadUrl");
                appInfo.iconUrl = obj.getString("iconUrl");
                appInfo.id = obj.getString("id");
                appInfo.name = obj.getString("name");
                appInfo.packageName = obj.getString("packageName");
                appInfo.size = obj.getLong("size");
                appInfo.stars = (float) obj.getDouble("stars");
                apps.add(appInfo);
            }
            return apps;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
