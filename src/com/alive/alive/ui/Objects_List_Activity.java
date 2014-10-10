package com.alive.alive.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alive.alive.AliveEngine.Models.Object;
import com.alive.alive.AliveEngine.ObjectAdapter;
import com.alive.alive.AliveEngine.parsers.ObjectJSONParser;
import com.alive.alive.AliveEngine.utils.HttpManager;
import com.alive.alive.VuforiaSamples.R;

/**
 * Created by yessir on 05/10/14.
 */
public class Objects_List_Activity extends ListActivity {

    public static final String PHOTOS_BASE_URL =
            "http://zonlinegamescom.ipage.com/alive/user/objects/photos/";

    TextView output;
    ProgressBar pb;
    List<MyTask> tasks;

    List<Object> objectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objects_list);

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<MyTask>();

        if (isOnline()) {
            requestData("http://zonlinegamescom.ipage.com/alive/user/objects/objects.json");
        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);
    }

    protected void updateDisplay() {
        //Use ObjectAdapter to display data
        ObjectAdapter adapter = new ObjectAdapter(this, R.layout.item_object, objectList);
        setListAdapter(adapter);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private class MyTask extends AsyncTask<String, String, List<Object>> {

        @Override
        protected void onPreExecute() {
            if (tasks.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected List<Object> doInBackground(String... params) {

            String content = HttpManager.getData(params[0], "feeduser", "feedpassword");
            objectList = ObjectJSONParser.parseFeed(content);

            return objectList;
        }

        @Override
        protected void onPostExecute(List<Object> result) {

            tasks.remove(this);
            if (tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }

            if (result == null) {
                Toast.makeText(Objects_List_Activity.this, "Web service not available", Toast.LENGTH_LONG).show();
                return;
            }

            objectList = result;
            updateDisplay();

        }

    }

}