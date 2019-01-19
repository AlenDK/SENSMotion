package e.android.sensmotion.views;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import e.android.sensmotion.Notification.PostNotifications;

public class PreferenceManagerReset {

    private static AlarmManager alarmMng;
    private static PendingIntent almIntent;

    public static void startAlarm (Context c) {

        System.out.println("hest startalarm kaldt");

        if (alarmMng == null)  alarmMng = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(c, PreferenceMangerResetListener.class);


       almIntent = PendingIntent.getBroadcast(c, 0, intent,  0);

        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 01);


        alarmMng.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), almIntent);

    }
}
