package e.android.sensmotion.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;

import java.util.Calendar;

public class Alarm {

    private static AlarmManager alarmMgr;
    private static PendingIntent alarmIntent;

    public static void startAlarm (Context c) {

        System.out.println("hest startalarm kaldt");
/*
        ComponentName receiver = new ComponentName(c, BootLytter.class);
        PackageManager pm = c.getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
*/
        if (alarmMgr == null)  alarmMgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(c, AlarmLytter.class);

        //intent.setAction("com.alarm.notitest.START_ALARM"); //lille trick som gør at det bliver forskellige intents hvis det er to notifikationer samtidig
        alarmIntent = PendingIntent.getBroadcast(c, 0, intent,  0);

        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 5);

        //alarmMgr.set(AlarmManager.RTC, t.dato.getMillis(), alarmIntent);
        /*alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()
                                , AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);
        */
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+100, 60000, alarmIntent);/* SystemClock.elapsedRealtime() +
                5 * 1000, 5 * 1000, alarmIntent);*/
    }

    public static void stopAlarm() {
        if (alarmMgr != null) {
            alarmMgr.cancel(alarmIntent);
        }
    }
}
