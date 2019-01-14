package e.android.sensmotion.Notification;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import e.android.sensmotion.R;

import static e.android.sensmotion.Notification.NotifikationChannels.CHANNEL_ID2;

public class NotificationService extends Service {
    Timer timer;
    TimerTask timerTask;
    String TAG = getClass().getName();
    Thread Lytter;
    boolean run;
    int Your_X_SECS = 5;

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


        return START_STICKY;
    }


    @Override
    public void onCreate() {
        Toast.makeText(this, TAG + " onCreate", Toast.LENGTH_LONG).show();
        run = true;
        Lytter = new Thread(new Runnable() {
            @Override
            public void run() {
                while (run) {

                }
            }
        });
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, TAG + " onDestroy", Toast.LENGTH_LONG).show();
        run = false;
    }

}
