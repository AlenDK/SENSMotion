package e.android.sensmotion.views;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.Notification;
import android.app.usage.UsageStats;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import e.android.sensmotion.R;
import e.android.sensmotion.adapters.MotionDetection;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.controller.interfaces.IDataController;

import static e.android.sensmotion.Notification.NotifikationChannels.CHANNEL_ID1;
import static e.android.sensmotion.Notification.NotifikationChannels.CHANNEL_ID2;


public class Patient_start_frag extends Fragment implements View.OnClickListener{

    private ImageView actionbar_image, today_smiley, stickman_walk, stickman_stand, stickman_bike, stickman_train, stickman_other;
    private ImageButton profile_button;
    private TextView textView, textView1, textView2, textView3, textView4, textView5,circleText;
    private ProgressBar circlebar, walk,stand,bike,train,other;

    Date currentDay = Calendar.getInstance().getTime();
    SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
    String today = format.format(currentDay);

    int day = Integer.parseInt(today.substring(0,1));
    int month =  Integer.parseInt(today.substring(3,4));
    int year =  Integer.parseInt(today.substring(6,9));

    SharedPreferences prefs;

    int totalProgressGoal = 500, circleProgress;
    private int walk_prog = 0;
    public static int PercentDaily, PercentWalk, PercentStand, PercentExecise, Percentcycle, PercentOther;
    static double dailyProgress, walkAmount,standAmount,exerciseAmount,cyclingAmount,otherAmount;
    static int totalwalk =100, totalstand =100, totalexercise =100, totalcycling =100, totalother =100;


    IDataController data = ControllerRegistry.getDataController();
    IUserController bruger = ControllerRegistry.getUserController();
    private Handler progHandle = new Handler();

    private NotificationManagerCompat notificationManagerCompat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient, container, false);

        notificationManagerCompat = NotificationManagerCompat.from(getActivity());

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        LinearLayout dates = view.findViewById(R.id.dates);

        System.out.println("Bruger: "+bruger.getPatient("p1").toString());
        System.out.println("Sensor: "+bruger.getPatient("p1").getSensor("s1").toString());



      //  for (int i = 0; i <data.getPeriode().getValuesList().size(); i++) {
            for (int i = 0; i <6; i++) {

            View views = inflater.inflate(R.layout.array_adapter, dates, false);
            TextView textview = views.findViewById(R.id.facetoday_text);
            textview.setText("Dag: " + 1);


            today_smiley = views.findViewById(R.id.facetoday_image);
            today_smiley.setImageResource(R.drawable.baseline_sentiment_very_satisfied_black_48);



            dates.addView(views);
        }


        walkAmount = Double.parseDouble(bruger.getPatient("p1").getSensor("s1").getCurrentPeriod().getValuesList().get(0).getWalk());
        standAmount = Double.parseDouble(bruger.getPatient("p1").getSensor("s1").getCurrentPeriod().getValuesList().get(0).getStand());
        cyclingAmount = Double.parseDouble(bruger.getPatient("p1").getSensor("s1").getCurrentPeriod().getValuesList().get(0).getCycling());
        exerciseAmount = Double.parseDouble(bruger.getPatient("p1").getSensor("s1").getCurrentPeriod().getValuesList().get(0).getExercise());
        otherAmount = Double.parseDouble(bruger.getPatient("p1").getSensor("s1").getCurrentPeriod().getValuesList().get(0).getOther());

        dailyProgress = walkAmount + standAmount + cyclingAmount + exerciseAmount +otherAmount;
        circleProgress = (int) Math.round(dailyProgress/totalProgressGoal*100);

        createText(view);
        createImages(view);
        createButtons(view);
        createProgressbar(view);


        final Toast akt_klaret =  Toast.makeText(getActivity(), "Godt klaret. Du har nået en af dine" +
                "daglige mål for i dag!", Toast.LENGTH_LONG);

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (walk_prog < 100){
                    walk_prog++;
                    android.os.SystemClock.sleep(50);
                    progHandle.post(new Runnable() {
                        @Override
                        public void run() {
                            walk.setProgress(walk_prog);
                        }
                    });
                }

                if(walk_prog == 100){
                   akt_klaret.show();
                }}
        }).start();
        */



        view.setOnTouchListener(new MotionDetection(getActivity()) {
            @Override
            public void onSwipeUp() {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, new Achievement_frag())
                        .commit();
            }
            });


        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == profile_button){
            getFragmentManager().beginTransaction()
            .replace(R.id.fragmentindhold, new patient_setting_frag())
            .commit();

        }
    }
/*
    private void changeSmiley(overallProgess) {
        if(overallProgress <= 33.33) {
            imageView.setImageResource(R.drawable.baseline_sentiment_very_unsatisfied_red_48);
        } else if (33.33 < overallProgress | overallProgress <= 66.66) {
            imageView.setImageResource(R.drawable.baseline_sentiment_very_moderat_yellow_48);
        } else if (66.66 < overallProgress | overallProgress <= 100) {
            imageView.setImageResource(R.drawable.baseline_sentiment_very_satisfied_black_48);
        }
    }
*/


//get dag, og tjek år, måned, dag

    public void setDates(int day, int month, int year) {
        prefs.edit().putInt("day", day);
        prefs.edit().putInt("month", month);
        prefs.edit().putInt("year", year);
    }

    private boolean checkDate() {
        if (prefs.getInt("day", 0) == 0) {
           setDates(day, month, year);
        }
        if ((day - prefs.getInt("day", 0)) >= 1) {

            setDates(day, month, year);
            return true;
        }

        if ((month - prefs.getInt("month", 0)) >= 1) {
            setDates(day, month, year);
            return true;
        }
        if ((year - prefs.getInt("year", 0)) >= 1) {
            setDates(day, month, year);
            return true;
        }
        return false;
    }

/*
    private void saveAchievemnt( ) {
        prefs.edit().putInt("overallWalk", )
        prefs.getInt("walk", 0)
        if ((prefs.getInt("walk", 0)) == 0) {
            prefs.edit().putInt("walk",0);
        } else {
        }
        500
                520 - 500 = 20
                        500 + 20
                                300 - 520
    }
*/
    private void createProgressbar(View view){
        circlebar = (ProgressBar) view.findViewById(R.id.circlebar);
        walk = (ProgressBar) view.findViewById(R.id.progbar_walk);
        stand = (ProgressBar) view.findViewById(R.id.progbar_stand);
        bike = (ProgressBar) view.findViewById(R.id.progbar_bike);
        train = (ProgressBar) view.findViewById(R.id.progbar_train);
        other = (ProgressBar) view.findViewById(R.id.progbar_other);

        int walkPercent = (int) Math.round(walkAmount/totalwalk*100);
        int standPercent = (int) Math.round(standAmount/totalstand*100);
        int cyclingPercent = (int) Math.round(cyclingAmount/totalcycling*100);
        int exercisePercent = (int) Math.round(exerciseAmount/totalexercise*100);
        int otherPercent = (int) Math.round(otherAmount/totalother*100);

        System.out.println(walkPercent);
        System.out.println(standPercent);
        System.out.println(cyclingPercent);
        System.out.println(exercisePercent);
        System.out.println(otherPercent);

        walk.setProgress(walkPercent);
        stand.setProgress(standPercent);
        bike.setProgress(cyclingPercent);
        train.setProgress(exercisePercent);
        other.setProgress(otherPercent);


        circlebar.setProgress(circleProgress);
        circlebar.setRotation(270); //Make the progressbar start at the top

    }

    private void createImages(View view){
        actionbar_image = (ImageView) view.findViewById(R.id.actionbar_image);
        today_smiley = (ImageView) view.findViewById(R.id.facetoday_image);
        stickman_walk = (ImageView) view.findViewById(R.id.walking_stickman);
        stickman_stand = (ImageView) view.findViewById(R.id.standing_stickman);
        stickman_bike = (ImageView) view.findViewById(R.id.biking_stickman);
        stickman_train = (ImageView) view.findViewById(R.id.training_stickman);
        stickman_other = (ImageView) view.findViewById(R.id.other_stickman);
    }

    private void createText(View view){
        textView = (TextView) view.findViewById(R.id.nameText);
        textView1 = (TextView) view.findViewById(R.id.textView1);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        textView5 = (TextView) view.findViewById(R.id.textView5);
        circleText =(TextView) view.findViewById(R.id.progressBarText);

        textView1.setText(Math.round(otherAmount)+"/100m");
        textView2.setText(Math.round(standAmount)+"/100min");
        textView3.setText(Math.round(cyclingAmount)+"/100m");
        textView4.setText(Math.round(exerciseAmount)+"/100min");
        textView5.setText(Math.round(walkAmount)+"/100min");
        circleText.setText(circleProgress+"%");
    }

    private void createButtons(View view){
        profile_button = (ImageButton) view.findViewById(R.id.knap_profil);
        profile_button.setOnClickListener(this);
    }

    public void NotifyWhenDone() {

        Notification notification = new NotificationCompat.Builder(getActivity(), CHANNEL_ID1)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle("Mega sejt gået!!")
                .setContentText("Du har nu klaret én af dagens udfordinger")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    public void NotifyHalfWayThere() {

        Notification notification = new NotificationCompat.Builder(getActivity(), CHANNEL_ID2)
                .setSmallIcon(R.drawable.sensmotionblack)
                .setContentTitle("Du er der næsten!")
                .setContentText("Du er nu halvejs gennem én af dagens udfordinger. \n" +
                        "Keep up the good work")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(2, notification);
    }

    public static void setPercentage() {

        PercentWalk = (int) Math.round(walkAmount/totalwalk*100);
        PercentStand = (int) Math.round(standAmount/totalstand*100);
        Percentcycle = (int) Math.round(cyclingAmount/totalcycling*100);
        PercentExecise = (int) Math.round(exerciseAmount/totalexercise*100);
        PercentOther = (int) Math.round(otherAmount/totalother*100);

    }

    /*
    public void streakNotification(){
        int streak;
        UsageStats usageStats = new UsageStats();
        Date date = new Date();
        long currentTime = date.getTime();
        if(usageStats.getLastTimeUsed()- currentTime == 43200000){

        }

    }
    */
}
