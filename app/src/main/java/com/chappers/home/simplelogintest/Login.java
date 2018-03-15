package com.chappers.home.simplelogintest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private EditText                    etUsername;
    private EditText                    etPassword;
    private EditText                    etTitle;
    private Switch                      swRememberme;
    private Button                      btnLogin;
    private ProgressDialog              dialog;
    private static final String         TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);

        // get the shared pref's
        final SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);

        // find the items
        etUsername =            findViewById(R.id.etUsername);
        etPassword =            findViewById(R.id.etPassword);
        etTitle =               findViewById(R.id.etTitle);
        btnLogin =              findViewById(R.id.btnLogin);
        swRememberme =          findViewById(R.id.swRememberme);

        // Set the progress Dialog up
        dialog =                new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Logging in, hang fire!");

        etTitle.setEnabled(false);

        // If the Remember me dialog is true load the saved values
        if (prefs.getBoolean("rememberme", true)) {
            etUsername.setText(prefs.getString("username", "Username"));
            etPassword.setText(prefs.getString("password", ""));
            swRememberme.setChecked(prefs.getBoolean("rememberme", true));
        }
        // else put the cursor focus in the username editText box
        else {
            etUsername.requestFocus();
            etUsername.setActivated(true);
        }


        //region Remember me button onClick Listener

        swRememberme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swRememberme.isChecked()){
                    Toast.makeText(Login.this,"Username and Password to be saved",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Login.this,"Username and Password NOT to be saved",Toast.LENGTH_LONG).show();
                }
            }
        });
        //endregion

        //region Login Button setOnClickListener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* hide keyboard */
                dialog.show();
                SharedPreferences.Editor editor = prefs.edit();
                if (swRememberme.isChecked()) {
                    editor.putString("username", etUsername.getText().toString());
                    editor.putString("password", etPassword.getText().toString());
                    editor.putBoolean("rememberme", true);
                    editor.apply();
                 }else {
                    editor.putBoolean("rememberme", false);
                    editor.apply();

                }

                InputMethodManager imm = (InputMethodManager) Login.this.getSystemService(Context.INPUT_METHOD_SERVICE);

                assert imm != null;
                if (imm.isAcceptingText()) {
                    closeKeyboard();
                }
            }
        });
        //endregion

    }

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
}
