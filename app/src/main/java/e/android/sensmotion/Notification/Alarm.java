package e.android.sensmotion.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;

import java.util.Calendar;

public class Alarm {

    private static AlarmManager alarmMgr;
    private static PendingIntent alarmIntent;

    public static void startAlarm (Context c) {
        System.out.println("hest startalarm kaldt");

        if (alarmMgr == null)  alarmMgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(c, PostNotifications.class);

        //intent.setAction("com.alarm.notitest.START_ALARM"); //lille trick som gør at det bliver forskellige intents hvis det er to notifikationer samtidig
        alarmIntent = PendingIntent.getBroadcast(c, 0, intent,  0);

        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()
                                , AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);
        //For at teste om AlarmManageren virker
        /*
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+100, 60000, alarmIntent);
        */
    }

    public static void alarmSaveData(Context c, final String id){
        alarmMgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(c, SaveToFirebase.class);

        alarmIntent = PendingIntent.getBroadcast(c, 0, intent,  0);

        // Set the alarm to start at 23:50
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 00);
    }

    public static void stopAlarm() {
        if (alarmMgr != null) {
            alarmMgr.cancel(alarmIntent);
        }
    }
}
