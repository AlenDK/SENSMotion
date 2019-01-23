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

    private static AlarmManager stopAlarmMgr;
    private static PendingIntent stopAlarmIntent;

    private static AlarmManager alarmMng;
    private static PendingIntent almIntent;

    public static void stopAlarm() {
        if (alarmMgr != null) {
            alarmMgr.cancel(alarmIntent);
        }
    }

    /**
     *
     * @param c
     */
    public static void startNotifications (Context c) {
        System.out.println("hest startalarm kaldt");

        if (alarmMgr == null)  alarmMgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(c, PostNotifications.class);

        intent.setAction("com.alarm.notitest.START_ALARM"); //lille trick som gør at det bliver forskellige intents hvis det er to notifikationer samtidig
        alarmIntent = PendingIntent.getBroadcast(c, 0, intent,  0);

        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);

        //Checks if the time has already passed 8:30 a.m.
        if (System.currentTimeMillis() - 30600000 >= 0) {
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()
                    , AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);
        } else {
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, 1000
                    , AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);
        }
        //For at teste om AlarmManageren virker
        /*
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+100, 60000, alarmIntent);
        */
    }

    public static void stopNotifications(Context context) {
        if (stopAlarmMgr == null)  stopAlarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, PostNotifications.class);

        //intent.setAction("com.alarm.notitest.START_ALARM"); //lille trick som gør at det bliver forskellige intents hvis det er to notifikationer samtidig
        stopAlarmIntent = PendingIntent.getBroadcast(context, 0, intent,  0);

        // Set the alarm to go of at 00:00 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);

        stopAlarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()
                                            , AlarmManager.INTERVAL_DAY, stopAlarmIntent);

    }

    public static void alarmSaveData(Context c/*, final String id*/){

        System.out.println("hest startalarm kaldt");

        if (alarmMng == null)  alarmMng = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(c, SaveToFirebase.class);

        almIntent = PendingIntent.getBroadcast(c, 0, intent,  0);

        // Set the alarm to go of at 00:01 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 01);


        alarmMng.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()
                                            , AlarmManager.INTERVAL_DAY, almIntent);
    }

}
