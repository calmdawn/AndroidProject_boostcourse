package com.example.boostcoursemoblieproject.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

public class NetworkState {

    public static final int TYPE_CONNECT = 1;

    public static final int TYPE_NOT_CONNECTED = 2;

    public static int status;

    public static void getConnectivityStatus(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(manager.getActiveNetwork());

        if (networkCapabilities == null) {
            status = TYPE_NOT_CONNECTED;
        } else {
            status = TYPE_CONNECT;
        }

    }

}
