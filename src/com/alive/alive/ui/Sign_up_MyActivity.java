package com.alive.alive.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alive.alive.AliveEngine.Controllers.UserController;
import com.alive.alive.AliveEngine.Models.Common.Response;
import com.alive.alive.AliveEngine.Models.Common.ResponseState;
import com.alive.alive.AliveEngine.Models.OnModelListener;
import com.alive.alive.AliveEngine.Models.User;
import com.alive.alive.VuforiaSamples.R;

public class Sign_up_MyActivity extends Activity {
    ProgressBar pb;

    protected AutoCompleteTextView firstName;
    protected AutoCompleteTextView lastName;
    protected AutoCompleteTextView email;
    protected EditText password;
    protected AutoCompleteTextView phone;
    protected Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        pb = (ProgressBar) findViewById(R.id.signUpProgressBar);
        pb.setVisibility(View.INVISIBLE);

        firstName = (AutoCompleteTextView)findViewById(R.id.firstName);
        lastName = (AutoCompleteTextView)findViewById(R.id.lastName);
        email = (AutoCompleteTextView)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        phone = (AutoCompleteTextView)findViewById(R.id.phoneNumber);

        button = (Button)findViewById(R.id.email_sign_up_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserController userController = new UserController();

                User user = new User(firstName.getText().toString(), lastName.getText().toString() ,email.getText().toString(), password.getText().toString());

                user.setModelHandler(new OnModelListener() {
                    @Override
                    public void OnModelSent() {
                        User user = User.getLoggedInUser();
                        Toast.makeText(getApplication(), "Registered Successfully with id " + user.getId(), Toast.LENGTH_LONG).show();
                        startARActivity();
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

                    Response response = userController.CreateUser(user);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_up__my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    private void startARActivity()
    {
        Intent i = new Intent();
        i.setClassName("com.alive.alive.VuforiaSamples", "com.alive.alive.VuforiaSamples.app.UserDefinedTargets.UserDefinedTargets");
        startActivity(i);
    }

}
