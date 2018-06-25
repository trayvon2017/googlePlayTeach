package com.example.cfb.googleplaytech.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by fbfatboy on 2018/6/22.
 */

public class RankPageProtocol extends BaseProtocol<ArrayList<String>> {
    @Override
    protected String getKey() {
        return "hot";
    }

    @Override
    protected String getUrlParams() {
        return "";
    }

    @Override
    protected ArrayList<String> processJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            ArrayList<String> ranklist = new ArrayList<String>();
            for (int i =0;i<jsonArray.length();i++){
                String jstr = (String) jsonArray.get(i);

                ranklist.add(jstr);
            }
            return ranklist;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
