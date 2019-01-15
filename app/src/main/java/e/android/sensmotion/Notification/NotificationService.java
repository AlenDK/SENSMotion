package e.android.sensmotion.Notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import e.android.sensmotion.views.Patient_start_frag;

import static e.android.sensmotion.views.Patient_start_frag.PercentExecise;
import static e.android.sensmotion.views.Patient_start_frag.PercentOther;
import static e.android.sensmotion.views.Patient_start_frag.PercentStand;
import static e.android.sensmotion.views.Patient_start_frag.PercentWalk;
import static e.android.sensmotion.views.Patient_start_frag.Percentcycle;
import static e.android.sensmotion.views.Patient_start_frag.setPercentage;

public class NotificationService extends Service {
    Patient_start_frag patient;
    String TAG = getClass().getName();
    Thread Lytter;
    boolean run;

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);
        Log.e(TAG, "onStartCommand");
        Toast.makeText(this, "StartID: " + startId, Toast.LENGTH_SHORT).show();
        Lytter.start();
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        Toast.makeText(this, TAG + " onCreate", Toast.LENGTH_LONG).show();
        patient = new Patient_start_frag();
        run = true;
        Lytter = new Thread(new Runnable() {
            @Override
            public void run() {
                while (run) {
                    setPercentage();
                    if (PercentWalk == 50 || PercentStand == 50 || Percentcycle == 50 || PercentExecise == 50 || PercentOther == 50) {
                        patient.NotifyHalfWayThere();
                    }
                    if (PercentWalk == 100 || PercentStand == 100 || Percentcycle == 100 || PercentExecise == 100 || PercentOther == 10) {
                        patient.NotifyWhenDone();
                    }
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, TAG + " onDestroy", Toast.LENGTH_LONG).show();
        run = false;
        System.out.print(Lytter.isAlive());

        patient = null;
    }
}
