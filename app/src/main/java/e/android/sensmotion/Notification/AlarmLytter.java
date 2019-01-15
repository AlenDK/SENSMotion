package e.android.sensmotion.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import e.android.sensmotion.views.Patient_start_frag;

import static e.android.sensmotion.views.Patient_start_frag.PercentExecise;
import static e.android.sensmotion.views.Patient_start_frag.PercentOther;
import static e.android.sensmotion.views.Patient_start_frag.PercentStand;
import static e.android.sensmotion.views.Patient_start_frag.PercentWalk;
import static e.android.sensmotion.views.Patient_start_frag.Percentcycle;
import static e.android.sensmotion.views.Patient_start_frag.setPercentage;

import java.util.ArrayList;


public class AlarmLytter extends BroadcastReceiver {

    final String TAG = getClass().getName();
    Patient_start_frag patient;

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println(TAG + "    onRecieve() modtog hest");
        Toast.makeText(context, "Alarm modtaget", Toast.LENGTH_SHORT).show();
        patient = new Patient_start_frag();
        //patient.NotifyWhenDone();
        /*setPercentage();
        if (PercentWalk == 50 || PercentStand == 50 || Percentcycle == 50 || PercentExecise == 50 || PercentOther == 50) {
            patient.NotifyHalfWayThere();
        }
        if (PercentWalk == 100 || PercentStand == 100 || Percentcycle == 100 || PercentExecise == 100 || PercentOther == 10) {
            patient.NotifyWhenDone();
        }*/
    }
}
