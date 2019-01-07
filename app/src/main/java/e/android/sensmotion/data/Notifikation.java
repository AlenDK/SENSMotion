package e.android.sensmotion.data;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notifikation extends Application {
    final public static String CHANNEL_ID1 = "Channel1";
    final public static String CHANNEL_ID2 = "Channel2";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationchannels();
    }

    public void createNotificationchannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_ID1,
                    "channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is channel 1");
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_ID2,
                    "channel 2",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel2.setDescription("This is channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);

        }
    }

}
