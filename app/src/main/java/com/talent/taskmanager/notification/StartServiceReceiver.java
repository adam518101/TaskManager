package com.talent.taskmanager.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.talent.taskmanager.Constants;
import com.talent.taskmanager.Utils;

/**
 * Created by acmllaugh on 14-11-21.
 */
public class StartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Utils.isServiceRunning(context, Constants.SERVICE_NAME)) {
            Intent serviceIntent = new Intent(context, TaskManagerService.class);
            context.startService(serviceIntent);
        }
    }

}
