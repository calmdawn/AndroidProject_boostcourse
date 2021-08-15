package com.calmdawn.boostcoursemobileproject.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

public class NetworkState {

    public static boolean getConnectivityStatus(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(manager.getActiveNetwork());

        if (networkCapabilities == null) {  //네트워크 연결된 경우 true, 끊긴 경우 false
            return false;
        } else {
            return true;
        }

    }

}
