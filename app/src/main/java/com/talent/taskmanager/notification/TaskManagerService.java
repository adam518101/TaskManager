package com.talent.taskmanager.notification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.talent.taskmanager.Constants;
import com.talent.taskmanager.R;
import com.talent.taskmanager.location.LocationManager;
import com.coal.black.bc.socket.client.handlers.UserSignHandler;
import com.coal.black.bc.socket.client.returndto.SignInResult;
import com.coal.black.bc.socket.dto.SignInDto;
import com.coal.black.bc.socket.enums.SignInType;

import java.util.ArrayList;
import java.util.List;

public class TaskManagerService extends Service {

    private static final String TASK_NOTIFICATION_SERVICE = "task_notification_service";
    private static final int LOCATION_UPDATE_INTERVAL = 5; // every 5 minutes we update locations to database.
    private static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private PowerManager.WakeLock mWakeLock;
    private StartServiceReceiver mReceiver;
    private ActivityManager mActivityManager;
    private LocationManager mLocationManager;
    private UserSignHandler mLocationHandler;
    private ArrayList<SignInDto> mRecordedLocations;
    private int mUpdateCountDown;
    private boolean mLastUpdateSuccess;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initVars();
        registerToTimeCount();
//        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
//        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "WAKE_LOCK");
//        mWakeLock.acquire();
        startRecordLocation();
        new MyTask().execute();
        return START_STICKY; // Make sure our service will start when it is closed by system due to low memory.
    }

    private void initVars() {
        mActivityManager = (ActivityManager) TaskManagerService.this.getSystemService(ACTIVITY_SERVICE);
        mLocationHandler = new UserSignHandler();
        mRecordedLocations = new ArrayList<SignInDto>();
        mLocationManager = new LocationManager(this.getApplicationContext(), null);
    }

    private void registerToTimeCount() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
        mReceiver = new StartServiceReceiver();
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        stopRecordLocation();
        super.onDestroy();
    }

    private void startRecordLocation() {
        mLocationManager.recordLocation(true);
    }

    private void stopRecordLocation() {
        mLocationManager.recordLocation(false);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            // In background, we update new positions and see if there is new task arriving.
//            try {
//                Random random = new Random();
//                int i = 0;
//                while (true) {
//                    if (!isTaskListInFront()) {
//                        Thread.sleep(5000);
//                        showNotification(++i);
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            try {
                while (true) {
                    updateLocationInformation();
                    getNewTasks();
                    Thread.sleep(60000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            mWakeLock.release();
            super.onPostExecute(aVoid);
        }
    }

    private void getNewTasks() {
        // We check if there is a new task.
    }

    private void updateLocationInformation() {
        SignInDto dto = new SignInDto();
        Location location = mLocationManager.getCurrentLocation();
        if (location == null) {
            Log.d("acmllaugh1", "updateLocationInformation (line 120): location is null.");
            return;
        }
        dto.setLatitude(location.getLatitude());
        dto.setLongitude(location.getLongitude());
        dto.setTime(System.currentTimeMillis());
        dto.setType(SignInType.ReportPosition);
        mRecordedLocations.add(dto);
        if (mLastUpdateSuccess) {
            mUpdateCountDown--;
        }
        if (mUpdateCountDown <= 0 || !mLastUpdateSuccess) {
            Log.d("acmllaugh1", "updateLocationInformation (line 147): it time to upload location information");
            SignInResult result = mLocationHandler.signIn(mRecordedLocations);
            if (result.isSuccess()) {
                mRecordedLocations.clear();
                mLastUpdateSuccess = true;
                mUpdateCountDown = LOCATION_UPDATE_INTERVAL;
                Log.d("acmllaugh1", "updateLocationInformation (line 153): upload location information success.");
                return;
            }
            mLastUpdateSuccess = false;
            Log.d("acmllaugh1", "updateLocationInformation (line 155): upload location failed.");
        }
    }

    private boolean isTaskListInFront() {
        // get the info from the currently running activity
        List<ActivityManager.RunningTaskInfo> taskInfo = mActivityManager.getRunningTasks(1);
        if (taskInfo.get(0).topActivity.getClassName().equals(Constants.TASK_LIST_ACTIVITY_NAME)) {
            return true;
        }
        return false;
    }

    private void showNotification(int i) {
        Intent emptyIntent = new Intent();
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, emptyIntent, 0);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = android.R.drawable.stat_notify_chat;
        CharSequence text = getString(R.string.new_task_coming) + " " + i;
        long when = System.currentTimeMillis();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification =
                new Notification.Builder(this).
                        setSmallIcon(icon)
                        .setShowWhen(true)
                        .setWhen(when)
                        .setContentText(text)
                        .setContentIntent(pIntent)
                        .setContentTitle(getString(R.string.notification_title))
                        .setSound(soundUri)
                        .build();
        //startForeground(NOTIFICATION_ID, notification);
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }


}
