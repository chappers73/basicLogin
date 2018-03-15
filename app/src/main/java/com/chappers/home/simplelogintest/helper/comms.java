package com.chappers.home.simplelogintest.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;


public class comms extends AsyncTask<String, Void, data>{
    private static final String         TAG = "comms";
    private Context                     contextView;
    private TextView                    textview;
    private data                        returnData = new data();


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

        String result                   = "";
        String paramURL                 = urls[0];
        String paramUsername            = urls[1];
        String paramPassword            = urls[2];
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(paramURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            HttpDigestAuth ht = new HttpDigestAuth();
            HttpURLConnection htc = (HttpURLConnection) ht.tryAuth(urlConnection, paramUsername, paramPassword);

            returnData.setHtc(htc);
            htc.disconnect();
            return returnData;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnData;
    }
}
