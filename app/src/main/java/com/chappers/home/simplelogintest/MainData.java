package com.chappers.home.simplelogintest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TabHost;

import com.chappers.home.simplelogintest.helper.OnDataSendToActivity;
import com.chappers.home.simplelogintest.helper.comms;
import com.chappers.home.simplelogintest.helper.data;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainData extends AppCompatActivity implements OnDataSendToActivity {

    private comms commsTest;
    private Bundle bundle;
    private String password;
    private String username;
    private String url;
    private SharedPreferences getPrefs;
    private TimerTask task;

    private static final String TAG = "MainData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Hide the title bar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_data);

        setupTabs();

        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        username = getPrefs.getString("prefs_et_UserInfo_Username", "username");
        url = getPrefs.getString("prefs_et_Server_URL", "url");

         bundle = getIntent().getExtras();
        if (bundle != null) {
            password = bundle.getString("password");
        }
        if (username != "" && password != "" && url != ""){
            setRepeatingAsyncTask();
        }


    }

    private void setupTabs(){

        TabHost host = findViewById(R.id.tabhost_data);
        host.setBackgroundResource(R.drawable.dmitriyilkevich434297unsplash_smaller);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Activity");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Activity");

        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Whos In");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Whos In");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Cameras");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Cameras");
        host.addTab(spec);

        //host.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.failedgoals); //fro first tab

    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        Log.i(TAG, "onCreateOptionsMenu: DID we get here?");
        MenuInflater blowUp = getMenuInflater();
        blowUp.inflate(R.menu.cool_menu, menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.preferences:
                Intent i = new Intent(this, Prefs.class);
                startActivity(i);
                break;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ONPAUSE");
        task.cancel();

    }

    private void setRepeatingAsyncTask() {

        final Handler handler = new Handler();
        Timer timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            //i.putExtra("password", etPassword.getText().toString());
                            commsTest = new comms(MainData.this);
                            String[] myParams = {url, username, password};
                            commsTest.execute(myParams);
                            //AsyncTaskParseJson jsonTask = new AsyncTaskParseJson();
                            //jsonTask.execute();
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 5*1000);  // interval of one minute

    }

    @Override
    public void sendData(data str) {
        Log.i(TAG, "MainData - sendData: time started - " + str.getStartTime().toString());

    }
}
