package com.chappers.home.simplelogintest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Objects;

public class Login extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener{

    private EditText                    etUsername;
    private EditText                    etPassword;
    private EditText                    etTitle;
    private Button                      btnLogin;
    private ImageView                   img_menu;
    private ProgressDialog              dialog;
    private static final String         TAG = "Login";
    private SharedPreferences           prefs;
    private SharedPreferences           getPrefs;
    private boolean                     rememberme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);

        initSystem();
        btnLogin.setOnClickListener(this);
        img_menu.setOnClickListener(this);


    }

    private void initSystem(){
        // get the shared pref's
        prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);

        // find the items
        etUsername =            findViewById(R.id.etUsername);
        etPassword =            findViewById(R.id.etPassword);
        etTitle =               findViewById(R.id.etTitle);
        btnLogin =              findViewById(R.id.btnLogin);
        img_menu =              findViewById(R.id.img_prefs);

        // Set the progress Dialog up
        dialog =                new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Checking Credentials....");

        etTitle.setEnabled(false);

        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        rememberme = getPrefs.getBoolean("prefs_cb_rememberMe", false);

        // If the Remember me dialog is true load the saved values
        //if (prefs.getBoolean("rememberme", true)) {
        if (rememberme) {
            etUsername.setText(getPrefs.getString("prefs_et_UserInfo_Username", "Username"));
            //etPassword.setText(prefs.getString("password", ""));
        }
        // else put the cursor focus in the username editText box
        else {
            etUsername.requestFocus();
            etUsername.setActivated(true);
        }
    }

    //region onCreateOptionsMenu(android.view.Menu menu)
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater blowUp = getMenuInflater();
        blowUp.inflate(R.menu.cool_menu,menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }
    //endregion
    //region onMenuItemClick(MenuItem item)
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preferences:
                Log.i(TAG, "onOptionsItemSelected: Pref selected");
                Intent i = new Intent(this,Prefs.class);
                startActivity(i);
                return true;
            case R.id.exit:
                Log.i(TAG, "onOptionsItemSelected: exit called");
                ((Activity)this).finish();
                System.exit(0);
                return true;
            default:
                return false;
        }
    }
    //endregion

    //region private void openKeyboard()
    private void openKeyboard() {
        InputMethodManager imm = (InputMethodManager) Login.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null){
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }
    //endregion
    //region private void closeKeyboard()
    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)Login.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(Objects.requireNonNull(Login.this.getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }
    //endregion

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogin:

                if(TextUtils.isEmpty(etUsername.getText())) {
                    etUsername.requestFocus();
                    etUsername.setActivated(true);
                    etUsername.setError("You must enter a username");
                    return;
                }
                if(TextUtils.isEmpty(etPassword.getText())) {
                    etPassword.requestFocus();
                    etPassword.setActivated(true);
                    etPassword.setError("You must enter a password");
                    return;
                }
                dialog.show();
                SharedPreferences.Editor editor = getPrefs.edit();
                if (getPrefs.getBoolean("prefs_cb_rememberMe", false)) {
                    editor.putString("prefs_et_UserInfo_Username", etUsername.getText().toString());
                    editor.apply();
                }
                InputMethodManager imm = (InputMethodManager) Login.this.getSystemService(Context.INPUT_METHOD_SERVICE);

                assert imm != null;
                if (imm.isAcceptingText()) {
                    closeKeyboard();
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                        dialog.dismiss();
                        Intent i = new Intent(Login.this, MainData.class);
                        startActivity(i);
                    }
                }, 5000);   //5 seconds
                break;

            case R.id.img_prefs:
                PopupMenu popup = new PopupMenu(this, v);
                popup.setOnMenuItemClickListener(this);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.cool_menu, popup.getMenu());
                popup.show();
                break;

        }

    }

}
