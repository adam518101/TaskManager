package com.talent.taskmanager.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import de.greenrobot.event.EventBus;

/**
 * Created by acmllaugh on 14-12-24.
 */
public class NetworkStateReceiver extends BroadcastReceiver {
    EventBus mEventBus = EventBus.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        Log.d("acmllaugh1", "onReceive (line 84): network is connected : " + noConnectivity);
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        NetworkState state = new NetworkState(!noConnectivity, haveConnectedWifi, haveConnectedMobile);
        mEventBus.post(state);
    }
}
