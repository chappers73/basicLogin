package com.chappers.home.simplelogintest.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import static com.chappers.home.simplelogintest.Login.dialog;

public class NetworkChangeReceiver extends BroadcastReceiver
{
    private Context context;
    public NetworkChangeReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            if (isOnline(this.context)) {
                dialog(true);
                Log.e("keshav", "Online Connect Intenet ");
            } else {
                //(false);
                dialog(false);
                Log.e("keshav", "Conectivity Failure !!! ");

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }


}