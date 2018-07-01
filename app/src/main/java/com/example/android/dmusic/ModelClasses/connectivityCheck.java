package com.example.android.dmusic.ModelClasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class connectivityCheck  {
    Context context;

    public connectivityCheck(Context context) {
        this.context = context;
    }

    public boolean connectivity(){
        ConnectivityManager connection = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkStauts = connection.getActiveNetworkInfo();
        return networkStauts!=null && networkStauts.isConnectedOrConnecting();
    }
}
