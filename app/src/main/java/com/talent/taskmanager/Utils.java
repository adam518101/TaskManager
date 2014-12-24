package com.talent.taskmanager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.talent.taskmanager.network.NetworkState;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by acmllaugh on 14-11-21.
 */
public class Utils {

    private static EventBus mEventBus = EventBus.getDefault();

    private Utils() {
        // This class should not be initialize or have sub classes.
        throw new AssertionError();
    }

    /**
     *  Check if service is running.
     * */
    public static boolean isServiceRunning(Context context, String serviceClassName){
        final ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceClassName)){
                return true;
            }
        }
        return false;
    }

    public static void log(String className, String logText) {
        if (Constants.IN_DEBUG_MODE) {
            Log.d("acmllaugh1", className + " : " + logText);
        }
    }

    public static Toast showToast(Toast toast, String message, Context context) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }else {
            toast.setText(message);
        }
        toast.show();
        return toast;
    }

    public static void registerToEventBus(Activity activity) {
        if (!mEventBus.isRegistered(activity)) {
            try {
                mEventBus.register(activity);
            } catch (Exception e) {
                Log.d("acmllaugh1", "registerToEventBus (line 60): register to event bus error.");
                e.printStackTrace();
            }
        }
    }

    public static void unRegisterEventBus(Activity activity) {
        if (mEventBus.isRegistered(activity)) {
            try {
                mEventBus.unregister(activity);
            } catch (Exception e) {
                Log.d("acmllaugh1", "registerToEventBus (line 60): unregister to event bus error.");
                e.printStackTrace();
            }
        }
    }


    public static NetworkState getCurrentNetworkState(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean wifiConnected = false;
        boolean mobileConnected = false;
        boolean networkConnected;
        if (mWifi.isConnected()) {
            wifiConnected = true;
        }
        if (mMobile.isConnected()) {
            mobileConnected = true;
        }
        networkConnected = wifiConnected || mobileConnected;
        return new NetworkState(networkConnected, wifiConnected, mobileConnected);
    }
}
