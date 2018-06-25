package com.example.cfb.googleplaytech.http.protocol;

import com.example.cfb.googleplaytech.domain.ClassificationInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by fbfatboy on 2018/6/22.
 */

public class ClassificationPageProtocol extends BaseProtocol<ArrayList<ClassificationInfo>> {
    @Override
    protected String getKey() {
        return "category";
    }

    @Override
    protected String getUrlParams() {
        return "";
    }

    @Override
    protected ArrayList<ClassificationInfo> processJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            ArrayList<ClassificationInfo> classfications = new ArrayList<ClassificationInfo>();
            for (int i =0;i<jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                //title的item
                ClassificationInfo infoTitle = new ClassificationInfo();
                infoTitle.title = jsonObject.getString("title");
                infoTitle.isTitle = true;
                classfications.add(infoTitle);
                //infos
                JSONArray jsArray2 = (JSONArray) jsonObject.get("infos");
                for (int j = 0;j<jsArray2.length();j++){
                    ClassificationInfo  classificationInfo= new ClassificationInfo();
                    JSONObject jsonObject2 = (JSONObject) jsArray2.get(j);
                    classificationInfo.name1 = jsonObject2.getString("name1");
                    classificationInfo.name2 = jsonObject2.getString("name2");
                    classificationInfo.name3 = jsonObject2.getString("name3");
                    classificationInfo.url1 = jsonObject2.getString("url1");
                    classificationInfo.url2 = jsonObject2.getString("url2");
                    classificationInfo.url3 = jsonObject2.getString("url3");
                    classificationInfo.isTitle = false;
                    classfications.add(classificationInfo);
                }

            }
            return classfications;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
