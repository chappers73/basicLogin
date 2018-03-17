package com.chappers.home.simplelogintest.helper;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class data {

    private static final String             TAG = "data";
    private HttpURLConnection               htc;
    private ArrayList<String>               adminItems = new ArrayList<>();     // List of Admin Users detected
    private ArrayList<String>               triggerItems = new ArrayList<String>();   // List of tiggers
    private String                          adminStatus;    // Re-scanning for Admin -> House Empty
    private String                          systemStatus;   // Whats happening, Admin Found..., Still searching..., Searching Network
    private String                          startTime;      // When did the Alarm System Start
    private String                          alarmStatus;    // String If AlarmActive -> "House Alarm - Active" or Disabled
    private StringBuilder                   result = new StringBuilder();
    private Integer                         respCode = 0;


    // region Public Getters
    public HttpURLConnection getHtc() {
        return htc;
    }

    public Integer getRespCode() {
        return respCode;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setHtc(HttpURLConnection htc) throws IOException {
        this.htc = htc;
        String msg = htc.getResponseMessage();
        respCode = htc.getResponseCode();
        //Log.i(TAG, "setHtc: Response Code - " + Integer.toString(s2));
        //Log.i(TAG, "setHtc: msg - " + msg);
        if (respCode == HttpURLConnection.HTTP_OK)// code 200
            extractData();
    }

    public ArrayList<String> getAdminItems() {
        return adminItems;
    }
    public ArrayList<String> getTriggerItems() {
        return triggerItems;
    }
    public String getAdminStatus() {
        return adminStatus;
    }
    public String getSystemStatus() {
        return systemStatus;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getAlarmStatus() {
        return alarmStatus;
    }

    // endregion


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void extractData() {

        try {
            InputStream in = new BufferedInputStream(htc.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            //Add {} to string "{" + result.toString() +"}"
            JSONObject obj = new JSONObject(result.toString());

            // Get System Status
            systemStatus = (String) obj.get("status");
            //Log.i(TAG, "extractData: status - " + systemStatus);

            // Get system Start Time
            startTime = (String) obj.get("StartTime");
            //Log.i(TAG, "extractData: StartTime - " + startTime);

            // Get Recent trigger
            JSONArray triggers = new JSONArray(obj.getJSONArray("baz").toString());

            // Add the Triggers found to the triggerItems
            for (int i = 0; i < triggers.length(); i++) {
                //System.out.println("Triggers: " + triggers.getString(i));
                //Log.i(TAG, "extractData: Triggers - " + triggers.getString(i));
                triggerItems.add(triggers.getString(i));
            }

            // Get Recent trigger
            JSONArray adminUsers = new JSONArray(obj.getJSONArray("admin").toString());

            // Add the Triggers found to the AdminUsers
            for (int i = 0; i < adminUsers.length(); i++) {
                //System.out.println("adminUsers: " + adminUsers.getString(i));
                //Log.i(TAG, "extractData: adminUsers - " + adminUsers.getString(i));
                adminItems.add(adminUsers.getString(i));
            }
            //for(Object what : triggers)
            //    Log.i(TAG, "extractData: Trigger - " + what.toString());

        } catch (IOException | JSONException e) {
            respCode = 999;
            e.printStackTrace();
        } finally {
            //Log.d(TAG, "connectHome: Disconnecting htc connection");

            htc.disconnect();
        }

    }
}
