package e.android.sensmotion.Notification;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import e.android.sensmotion.R;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.user.Patient;
import e.android.sensmotion.views.Patient_start_frag;

import static e.android.sensmotion.Notification.NotifikationChannels.CHANNEL_ID1;
import static e.android.sensmotion.Notification.NotifikationChannels.CHANNEL_ID2;
import static e.android.sensmotion.Notification.NotifikationChannels.CHANNEL_ID3;
import static e.android.sensmotion.views.Patient_start_frag.PercentExecise;
import static e.android.sensmotion.views.Patient_start_frag.PercentOther;
import static e.android.sensmotion.views.Patient_start_frag.PercentStand;
import static e.android.sensmotion.views.Patient_start_frag.PercentWalk;
import static e.android.sensmotion.views.Patient_start_frag.Percentcycle;


import java.util.ArrayList;
import java.util.List;


public class PostNotifications extends BroadcastReceiver {

    final String TAG = getClass().getName();
    NotificationManagerCompat notificationManagerCompat;
    private SharedPreferences prefs;
    private DatabaseReference database;

    public static int PercentDaily, PercentWalk, PercentStand, PercentExecise, Percentcycle, PercentOther;
    static double walkAmount, standAmount, trainAmount, cyclingAmount, otherAmount;
    static int totalwalk = 100, totalstand = 100, totalexercise = 100, totalcycling = 100, totalother = 100;

    private String userID;
    static boolean walkHalf = false, walk75 = false, walkDone = false;
    static boolean cycleHalf = false, cycle75 = false, cycleDone = false;
    static boolean trainHalf = false, train75 = false, trainDone = false;
    static boolean dailyDone = false;

    @Override
    public void onReceive(Context context, Intent intent) {

        // For test om hentning af data virker
       /* System.out.println(TAG + "    onRecieve() modtog hest   " + context);
        setPercentage(context);
        System.out.println("Gå " + PercentWalk + "hest");
        System.out.println("Stå " + PercentStand + "hest");
        System.out.println("cykel " + Percentcycle + "hest");
        System.out.println("Motion " + PercentExecise + "hest");
        System.out.println("andet " + PercentOther + "hest");
        */

        if (PercentDaily >= 100 && dailyDone == false) {
            NotifyWhenDone(context);
            dailyDone = true;

            walkDone = true;
            walk75 = true;
            walkHalf = true;

            cycleDone = true;
            cycle75 = true;
            cycleHalf = true;

            trainDone = true;
            train75 = true;
            trainHalf = true;
        }

        if (PercentWalk >= 100 && walkDone == false) {
            WalkDone(context);
            walkDone = true;
            walk75 = true;
            walkHalf = true;
        } else if (PercentWalk >= 75 && walk75 == false) {
            Walk75Done(context);
            walk75 = true;
            walkHalf = true;
        } else  if (PercentWalk >= 50 && walkHalf == false) {
            WalkHalfDone(context);
            walkHalf = true;
        }

        if (Percentcycle >= 100 && cycleDone == false) {
             CycleDone(context);
             cycleDone = true;
             cycle75 = true;
             cycleHalf = true;
        } else if (Percentcycle >= 75 && cycle75 == false) {
             Cycle75Done(context);
             cycle75 = true;
             cycleHalf = true;
        } else if (Percentcycle >= 50 && cycleHalf == false) {
             CycleHalfDone(context);
             cycleHalf = true;
        }

        if (PercentExecise >= 100 && trainDone == false) {
             ExerciseDone(context);
             trainDone = true;
             train75 = true;
             trainHalf = true;
        } else if (PercentExecise >= 75 && train75 == false) {
             Exercise75Done(context);
             train75 = true;
             trainHalf = true;
        } else if (PercentExecise >= 50 && trainHalf == false) {
             ExerciseHalfDone(context);
             trainHalf = true;
        }
    }

    private void getFirebasePatient(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        userID = prefs.getString("userID", "p1");
        database = FirebaseDatabase.getInstance().getReference("Patients");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("id").getValue(String.class).equals(userID)) {

                        for (DataSnapshot snapshotSensor : dataSnapshot.child(snapshot.getKey()).child("sensorer").getChildren()) {
                            List<Sensor> sensorList = new ArrayList<>();
                            Sensor s = snapshotSensor.getValue(Sensor.class);

                            walkAmount = Double.parseDouble(s.getCurrentPeriod().getValuesList().get(0).getWalk());
                            standAmount = Double.parseDouble(s.getCurrentPeriod().getValuesList().get(0).getStand());
                            cyclingAmount = Double.parseDouble(s.getCurrentPeriod().getValuesList().get(0).getCycling());
                            trainAmount = Double.parseDouble(s.getCurrentPeriod().getValuesList().get(0).getExercise());
                            otherAmount = Double.parseDouble(s.getCurrentPeriod().getValuesList().get(0).getOther());
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setPercentage(Context context) {

        getFirebasePatient(context);

        Percentcycle = (int) Math.round(cyclingAmount/totalcycling * 100);
        PercentExecise = (int) Math.round(trainAmount/totalexercise * 100);
        PercentWalk = (int) Math.round(walkAmount/totalwalk * 100);
        PercentStand = (int) Math.round(standAmount/totalstand * 100);
        PercentOther = (int) Math.round(otherAmount/totalother * 100);
        PercentDaily = (Percentcycle + PercentExecise + PercentWalk + PercentStand + PercentOther)/5;
    }

    public void NotifyWhenDone(Context context) {
        notificationManagerCompat = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID1)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle("Tillykke!!")
                .setContentText("Du har nu klaret alle dagens mål")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    public void WalkDone(Context context) {

        notificationManagerCompat = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID1)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle("Mega sejt gået!!")
                .setContentText("Du har nu gået så langt som du skulle idag")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    public void CycleDone(Context context) {

        notificationManagerCompat = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID1)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle("Mega sejt gået!!")
                .setContentText("Du har nu nået dagens mål indenfor cykling")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    public void ExerciseDone(Context context) {

        notificationManagerCompat = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID1)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle("Tillykke!!")
                .setContentText("Du har nu klaret dagens træning")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManagerCompat.notify(1, notification);
    }


    public void Walk75Done(Context context) {

        notificationManagerCompat = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID3)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle("Du er der næsten!!")
                .setContentText("Du har er tæt på at have gået dagens distance")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    public void Cycle75Done(Context context) {

        notificationManagerCompat = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID3)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle("Du er der næsten!!")
                .setContentText("Du har er tæt på at have cyklet dagens distance")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    public void Exercise75Done(Context context) {

        notificationManagerCompat = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID3)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle("Du er der næsten!!")
                .setContentText("Du har er tæt på at have gennemført dagens træning")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManagerCompat.notify(1, notification);
    }


    public void WalkHalfDone(Context context) {

        notificationManagerCompat = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID2)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle("Godt arbejde!")
                .setContentText("Du har nu gået halfvejs dit mål idag \n" +
                        "Keep up the good work")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(2, notification);
    }

    public void CycleHalfDone(Context context) {

        notificationManagerCompat = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID2)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle("Godt arbejde!")
                .setContentText("Du har nu cyklet halvejs gennem dagens mål. \n" +
                        "Keep up the good work")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(2, notification);
    }

    public void ExerciseHalfDone(Context context) {

        notificationManagerCompat = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID2)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle("Godt arbejde")
                .setContentText("Du er nu halvejs gennem dagens træning. \n" +
                        "Keep up the good work")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(2, notification);
    }

}
