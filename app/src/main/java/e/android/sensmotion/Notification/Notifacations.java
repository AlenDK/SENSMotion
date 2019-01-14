package e.android.sensmotion.Notification;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import e.android.sensmotion.R;
import e.android.sensmotion.views.PatientActivity;
import e.android.sensmotion.views.Patient_start_frag;

import static e.android.sensmotion.Notification.NotifikationChannels.CHANNEL_ID1;
import static e.android.sensmotion.Notification.NotifikationChannels.CHANNEL_ID2;

public class Notifacations {

    private NotificationManagerCompat notificationManagerCompat;
    private Context context;
    String Notifikation_Titel, Notifikation_Besked;

    public Notifacations(Context context) {
        this.context = context;
        notificationManagerCompat = NotificationManagerCompat.from(context);
    }

    public void NotifyWhenDone() {
        Notifikation_Titel = "Mega sejt gået!!";
        Notifikation_Besked = "Du har klaret én af dagens udfordinger";

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID1)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle(Notifikation_Titel)
                .setContentText(Notifikation_Besked)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    public void NotifyHalfWayThere() {
        Notifikation_Titel = "Du er der næsten!";
        Notifikation_Besked = "Du er nu halvejs gennem én af dagens udfordinger. \n" +
                "Keep up the good work";

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID2)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle(Notifikation_Titel)
                .setContentText(Notifikation_Besked)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(2, notification);
    }
}
