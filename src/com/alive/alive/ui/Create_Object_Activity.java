package com.alive.alive.ui;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.alive.alive.AliveEngine.Controllers.ObjectController;
import com.alive.alive.AliveEngine.Controllers.UserController;
import com.alive.alive.AliveEngine.Models.Object;
import com.alive.alive.AliveEngine.Models.Common.Response;
import com.alive.alive.AliveEngine.Models.Common.ResponseState;
import com.alive.alive.AliveEngine.Models.OnModelListener;
import com.alive.alive.AliveEngine.Models.User;
import com.alive.alive.VuforiaSamples.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yessir on 10/10/14.
 */
public class Create_Object_Activity extends Activity {
    ProgressBar pb;

    final Context context = this;
    protected AutoCompleteTextView name;
    protected AutoCompleteTextView serial_number;
    protected Spinner dropdown;
    protected Button button_create_object;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_object);

        pb = (ProgressBar) findViewById(R.id.create_object_progress);
        pb.setVisibility(View.INVISIBLE);

        name = (AutoCompleteTextView)findViewById(R.id.item_name);

        serial_number = (AutoCompleteTextView)findViewById(R.id.item_serial_number);

        dropdown = (Spinner)findViewById(R.id.object_kind);
        String[] items = new String[]{"5", "10", "20", "50", "100"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);

        button_create_object = (Button)findViewById(R.id.create_object_button);
        button_create_object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectController objectController = new ObjectController();

                Object object = new Object(name.getText().toString(), serial_number.getText().toString(),false, dropdown.getSelectedItem().toString());

                object.setModelHandler(new OnModelListener() {
                    @Override
                    public void OnModelSent() {
                        List<Object> objects = User.getLoggedInUser().getObjects();
                        Object object = objects.get(objects.size()-1);
                        Toast.makeText(getApplication(), object.getName(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void ProgressBarShower() {
                        pb.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void ProgressBarHider() {
                        pb.setVisibility(View.INVISIBLE);
                    }
                });

                if(isOnline()) {
                    Response response = objectController.CreateObject(object);

                    if(response.State == ResponseState.FAIL) {
                        for(Error error: response.Errors) {
                            Toast.makeText(getApplication(), error.getMessage(), Toast.LENGTH_LONG).show();
                            sleep(1000);
                        }
                    }
                } else
                    Toast.makeText(getApplication(), "Network isn't available", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}