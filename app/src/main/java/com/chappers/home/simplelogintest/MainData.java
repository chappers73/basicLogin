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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.chappers.home.simplelogintest.helper.OnDataSendToActivity;
import com.chappers.home.simplelogintest.helper.OnScreenLog;
import com.chappers.home.simplelogintest.helper.comms;
import com.chappers.home.simplelogintest.helper.data;

import java.net.HttpURLConnection;
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
    private OnScreenLog log;
    private ListView lvTriggers;
    private ListView lvAdmin;

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
        if (username != "" && password != "" && url != "") {
            setRepeatingAsyncTask();
        }

        //log = new OnScreenLog(this, R.id.content_1);
        //log.log("Started log on Activity 1");

        lvTriggers = findViewById(R.id.lv_Triggers);
        lvAdmin = findViewById(R.id.lv_Admin);
        ///*YOUR CHOICE OF COLOR*/
        //            textView.setTextColor(Color.BLUE);



    }

    private void setupTabs() {

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

    // Start the Repeating data collection using the External commsn class
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

        timer.schedule(task, 0, 5 * 1000);  // interval of one minute

    }
    /*
// add values
list.add("one");
list.add("two");

// your code
for (String object: list) {
    System.out.println(object);
}
     */

    // Check for response OK or abort task with warning message
    @Override
    public void sendData(data res) {
        final Integer statusCode = res.getRespCode();

        switch (statusCode) {
            case HttpURLConnection.HTTP_OK:
                //Log.i(TAG, "MainData - sendData: time started - " + res.getStartTime().toString());

                //res.getTriggerItems().forEach((a)-> log.log(a)); //Lambda not in this version
                for (String obj: res.getTriggerItems()){
                    //log.log(obj);
                }
                ArrayAdapter<String> adapterTrigger = new ArrayAdapter<String>(this,
                        R.layout.custom_list, res.getTriggerItems());
                lvTriggers.setAdapter(adapterTrigger);

                ArrayAdapter<String> adapterAdmin = new ArrayAdapter<String>(this,
                        R.layout.custom_list, res.getAdminItems());
                lvAdmin.setAdapter(adapterAdmin);


                break;
            default:
                Toast.makeText(getApplicationContext(), "Error - " + statusCode.toString() + "\nAborting connection...", Toast.LENGTH_LONG).show();
                break; // abort
        }


        Log.i(TAG, "MainData - sendData: time started - " + res.getStartTime().toString());

    }
}
