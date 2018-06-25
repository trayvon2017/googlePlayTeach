package com.example.cfb.googleplaytech.http.protocol;

import com.example.cfb.googleplaytech.domain.SubjectInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by fbfatboy on 2018/6/22.
 */

public class RecommendPageProtocol extends BaseProtocol<ArrayList<String>> {
    @Override
    protected String getKey() {
        return "recommend";
    }

    @Override
    protected String getUrlParams() {
        return "";
    }

    @Override
    protected ArrayList<String> processJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            ArrayList<String> recommends = new ArrayList<String>();
            for (int i =0;i<jsonArray.length();i++){
                String jstr = (String) jsonArray.get(i);

                recommends.add(jstr);
            }
            return recommends;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
