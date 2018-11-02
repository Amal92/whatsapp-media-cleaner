package com.amal.whatsyclean.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.amal.whatsyclean.Services.AlarmReceiverService;
import com.amal.whatsyclean.Utils.Constants;

import java.util.Calendar;

/**
 * Created by amal on 09/05/16.
 */
public class SetAlarmBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 17); //18
        calendar.set(Calendar.MINUTE, 42);  //48
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

       // cancelAlarm(context);
        Intent alarmIntent = new Intent(context.getApplicationContext(), AlarmReceiverService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context.getApplicationContext(), Constants.DAILY_ALARM_REQUEST_CODE, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }

    private void cancelAlarm(Context context) {
        Intent alarmIntent = new Intent(context.getApplicationContext(), AlarmReceiverService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), Constants.DAILY_ALARM_REQUEST_CODE, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }
}
