package com.chappers.home.simplelogintest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TabHost;

import java.util.Objects;

public class MainData extends AppCompatActivity {

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

}
