package org.nelda.learningjava;

import android.app.Application;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/21 0021.
 */
public class JSONObjectParse extends Application {
    private static List<ViewInfo> list;


    public static List<ViewInfo> parseJSON(String jsonData){
        list = new ArrayList<ViewInfo>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i=0;i<jsonArray.length();i++){
                ViewInfo viewInfo = new ViewInfo();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                viewInfo.setHtmlString(jsonObject.getString("htmlstring"));
                viewInfo.setWriteTime(jsonObject.getString("writeTime"));
                viewInfo.setButtonUrl(jsonObject.getString("buttonUrl"));
                viewInfo.setTitleContent(jsonObject.getString("titleContent"));
                viewInfo.setTextContent(jsonObject.getString("textContent"));
                list.add(viewInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<ViewInfo> getList(){
        return list;
    }

}
