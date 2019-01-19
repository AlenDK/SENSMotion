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
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.entities.sensor.Values;
import e.android.sensmotion.views.Patient_start_frag;

import static e.android.sensmotion.Notification.NotifikationChannels.CHANNEL_ID1;
import static e.android.sensmotion.Notification.NotifikationChannels.CHANNEL_ID2;
import static e.android.sensmotion.views.Patient_start_frag.PercentExecise;
import static e.android.sensmotion.views.Patient_start_frag.PercentOther;
import static e.android.sensmotion.views.Patient_start_frag.PercentStand;
import static e.android.sensmotion.views.Patient_start_frag.PercentWalk;
import static e.android.sensmotion.views.Patient_start_frag.Percentcycle;
import static e.android.sensmotion.views.Patient_start_frag.setPercentage;


import java.util.ArrayList;
import java.util.List;


public class PostNotifications extends BroadcastReceiver {

    final String TAG = getClass().getName();
    static int PercentDaily, PercentWalk, PercentStand, PercentExecise, Percentcycle, PercentOther;
    double dailyProgress, walkAmount, standAmount, trainAmount, cyclingAmount, otherAmount;
    int totalwalk = 100, totalstand = 100, totalexercise = 100, totalcycling = 100, totalother = 100;
    List<Values> values;

    IUserController bruger = ControllerRegistry.getUserController();
    NotificationManagerCompat notificationManagerCompat;
    static int i = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println(TAG + "    onRecieve() modtog hest   " + context);
        /*if (i == 0) {
            NotifyWhenDone(context);
            i++;
        } else {
            NotifyHalfWayThere(context);
            i = 0;
        }*/
        getStats();
        System.out.println("Gå " + PercentWalk + "hest");
        System.out.println("Stå "+ PercentStand + "hest");
        System.out.println("cykel " +Percentcycle + "hest");
        System.out.println("Motion "+PercentExecise + "hest");
        System.out.println("andet " + PercentOther + "hest");
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

    public void getStats() {
        values = bruger.getPatient("p1").getSensor("s1").getCurrentPeriod().getValuesList();
        walkAmount = Double.parseDouble(values.get(0).getWalk());
        standAmount = Double.parseDouble(values.get(0).getStand());
        cyclingAmount = Double.parseDouble(values.get(0).getCycling());
        trainAmount = Double.parseDouble(values.get(0).getExercise());
        otherAmount = Double.parseDouble(values.get(0).getOther());

        PercentWalk = (int) Math.round(walkAmount / totalwalk * 100);
        PercentStand = (int) Math.round(standAmount / totalstand * 100);
        Percentcycle = (int) Math.round(cyclingAmount / totalcycling * 100);
        PercentExecise = (int) Math.round(trainAmount / totalexercise * 100);
        PercentOther = (int) Math.round(otherAmount / totalother * 100);

    }

}
