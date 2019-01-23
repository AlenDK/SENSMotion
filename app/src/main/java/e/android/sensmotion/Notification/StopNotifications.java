package e.android.sensmotion.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StopNotifications extends BroadcastReceiver {

    /*
    When recived, the notification alarm is stopped, and then started again immidiately.
    So that notifications will restart the next morning
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Alarm.stopAlarm();
        Alarm.startNotifications(context);
    }
}
