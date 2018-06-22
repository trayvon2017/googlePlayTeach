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

public class SubjectPageProtocol extends BaseProtocol<ArrayList<SubjectInfo>> {
    @Override
    protected String getKey() {
        return "subject";
    }

    @Override
    protected String getUrlParams() {
        return "";
    }

    @Override
    protected ArrayList<SubjectInfo> processJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            ArrayList<SubjectInfo> subjects = new ArrayList<SubjectInfo>();
            for (int i =0;i<jsonArray.length();i++){
                SubjectInfo subjectInfo = new SubjectInfo();
                JSONObject jso = (JSONObject) jsonArray.get(i);
                subjectInfo.des = jso.getString("des");
                subjectInfo.url = jso.getString("url");
                subjects.add(subjectInfo);
            }
            return subjects;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
