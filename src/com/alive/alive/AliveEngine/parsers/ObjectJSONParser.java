package com.alive.alive.AliveEngine.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.alive.alive.AliveEngine.Models.File;
import com.alive.alive.AliveEngine.Models.Object;

/**
 * Created by yessir on 04/10/14.
 */

public class ObjectJSONParser {

    public static List<Object> parseFeed(String content) {

        try {
            JSONArray ar = new JSONArray(content);
            List<Object> objectList = new ArrayList<Object>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                Object object = new Object();

                object.setId(obj.getString("id"));
                object.setName(obj.getString("name"));
                object.setBarcode(obj.getString("barcode"));
                object.setSerialNumber(obj.getString("serialNumber"));

                String filesContent = obj.getJSONArray("files").toString();
                JSONArray files = new JSONArray(filesContent);
                ArrayList<File> filesArrayList = new ArrayList<File>();
                for(int ii = 0; ii < files.length(); ii++) {
                    File file = new File();
                    JSONObject fileObject = files.getJSONObject(ii);
                    file.setPath(fileObject.getString("path"));
                    filesArrayList.add(file);
                }
                object.setFiles(filesArrayList);

                objectList.add(object);
            }

            return objectList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}

