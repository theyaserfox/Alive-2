package com.alive.alive.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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

public class Login_Activity extends Activity {
    ProgressBar pb;

    final Context context = this;
    protected AutoCompleteTextView email;
    protected EditText password;
    protected Button button;
    protected Button button_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pb = (ProgressBar) findViewById(R.id.loginProgressBar);
        pb.setVisibility(View.INVISIBLE);

        email = (AutoCompleteTextView)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        button = (Button)findViewById(R.id.email_sign_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserController userController = new UserController();

                User user = new User(email.getText().toString(), password.getText().toString());
                user.setModelHandler(new OnModelListener() {
                    @Override
                    public void OnModelRetrieved() {
                        User user = User.getLoggedInUser();
                        Toast.makeText(getApplication(), user.getFirstName(), Toast.LENGTH_LONG).show();
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
                    Response response = userController.UserLogIn(user);

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

        button_sign_up = (Button)findViewById(R.id.email_login_sign_up_button);
        button_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), Sign_up_MyActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_, menu);
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

    // Starts the chosen activity
    private void startARActivity()
    {
        Intent i = new Intent();
        i.setClassName("com.alive.alive.VuforiaSamples", "com.alive.alive.VuforiaSamples.app.UserDefinedTargets.UserDefinedTargets");
        startActivity(i);
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
