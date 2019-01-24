package e.android.sensmotion.Notification;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class NotifikationChannels extends Application {
    final public static String CHANNEL_ID1 = "Channel1";
    final public static String CHANNEL_ID2 = "Channel2";
    final public static String CHANNEL_ID3 = "Channel3";

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        createNotificationchannels();
    }

    public void createNotificationchannels() {
        //Bliver kun kaldt hvis telefonens sdk er version 26 eller ældre.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_ID1,
                    "channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("Dette er channel 1");
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_ID2,
                    "channel 2",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel2.setDescription("Dette er channel 2");
            NotificationChannel channel3 = new NotificationChannel(
                    CHANNEL_ID3,
                    "channel 3",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel3.setDescription("Dette er channel 3");


            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
            manager.createNotificationChannel(channel3);

        }
    }

}
