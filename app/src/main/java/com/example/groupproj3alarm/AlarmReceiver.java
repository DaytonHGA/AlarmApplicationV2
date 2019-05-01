package com.example.groupproj3alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Notification notificationHelper = new Notification(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        if(!LocationActivity.switch4){LocationActivity.locationTrigger = true;}
        notificationHelper.getManager().notify(1, nb.build());
    }
}