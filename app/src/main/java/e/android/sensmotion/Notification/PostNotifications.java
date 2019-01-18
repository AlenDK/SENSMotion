package e.android.sensmotion.Notification;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import e.android.sensmotion.R;
import e.android.sensmotion.views.Patient_start_frag;

import static e.android.sensmotion.Notification.NotifikationChannels.CHANNEL_ID1;
import static e.android.sensmotion.Notification.NotifikationChannels.CHANNEL_ID2;
import static e.android.sensmotion.views.Patient_start_frag.PercentExecise;
import static e.android.sensmotion.views.Patient_start_frag.PercentOther;
import static e.android.sensmotion.views.Patient_start_frag.PercentStand;
import static e.android.sensmotion.views.Patient_start_frag.PercentWalk;
import static e.android.sensmotion.views.Patient_start_frag.Percentcycle;


import java.util.ArrayList;


public class PostNotifications extends BroadcastReceiver {

    final String TAG = getClass().getName();
    NotificationManagerCompat notificationManagerCompat;
    static int i = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println(TAG + "    onRecieve() modtog hest   " + context);
        if (i == 0) {
            NotifyWhenDone(context);
            i++;
        } else {
            NotifyHalfWayThere(context);
            i = 0;
        }
        /*setPercentage();
        if (PercentWalk == 50 || PercentStand == 50 || Percentcycle == 50 || PercentExecise == 50 || PercentOther == 50) {
            patient.NotifyHalfWayThere();
        }
        if (PercentWalk == 100 || PercentStand == 100 || Percentcycle == 100 || PercentExecise == 100 || PercentOther == 10) {
            patient.NotifyWhenDone();
        }*/
    }

    public void NotifyWhenDone(Context context) {

        notificationManagerCompat = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID1)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle("Mega sejt gået!!")
                .setContentText("Du har nu klaret én af dagens udfordinger")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    public void NotifyHalfWayThere(Context context) {

        notificationManagerCompat = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID2)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle("Du er der næsten!")
                .setContentText("Du er nu halvejs gennem én af dagens udfordinger. \n" +
                        "Keep up the good work")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(2, notification);
    }

}
