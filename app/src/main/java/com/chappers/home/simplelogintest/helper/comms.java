package com.chappers.home.simplelogintest.helper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class comms extends AsyncTask<String, Void, data> {
    private static final String TAG = "comms";
    private Context contextView;
    private TextView textview;
    public data returnData = new data();

    OnDataSendToActivity dataSendToActivity;

    public comms(Activity activity) {
        dataSendToActivity = (OnDataSendToActivity) activity;
    }

    @Override
    protected void onPostExecute(data data) {
        //super.onPostExecute(data);
        try {
            dataSendToActivity.sendData(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        textview.setText("Connecting...");
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected data doInBackground(String... urls) {

        String result = "";
        String paramURL = urls[0];
        String paramUsername = urls[1];
        String paramPassword = urls[2];
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(paramURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            HttpDigestAuth ht = new HttpDigestAuth();
            HttpURLConnection htc = ht.tryAuth(urlConnection, paramUsername, paramPassword);

            returnData.setHtc(htc);
            htc.disconnect();
            //Log.i(TAG, "doInBackground: about to return");
            return returnData;

        } catch (IOException e) {
            e.printStackTrace();
            //Log.i(TAG, "doInBackground: Catch ERROR");
            return returnData;
        }

        //return returnData;
    }

    //@Override
    //protected void onPostExecute(String result) {
    //   super.onPostExecute(result);
    //   dataSendToActivity.sendData(result);
    //}
}

