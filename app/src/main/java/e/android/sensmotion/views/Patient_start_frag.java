package e.android.sensmotion.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import e.android.sensmotion.R;
import e.android.sensmotion.adapters.ProgressBar_adapter;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.entities.sensor.Values;
import e.android.sensmotion.views.ProgressBars.ProgBar;


public class Patient_start_frag extends Fragment implements View.OnClickListener {

    private TextView circleText, completeText;
    private ProgressBar circlebar;
    private ImageButton profile_button;
    private RecyclerView recyclerView;
    private ProgBar walk, stand, cycling, train, other;
    private ListView complete, incomplete;

    Date currentDay = Calendar.getInstance().getTime();
    SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
    String today = format.format(currentDay);

    List<Values> values;
    ArrayList<String> days;
    ArrayList<Integer> images;
    List<ProgBar> progBarsIncom = new ArrayList<>();
    List<ProgBar> progBarsCom = new ArrayList<>();


    ProgressBar_adapter IncomAdapter, comAdapter;


    int day = Integer.parseInt(today.substring(0, 1));
    int month = Integer.parseInt(today.substring(3, 4));
    int year = Integer.parseInt(today.substring(6, 9));

    SharedPreferences prefs;

    int totalProgressGoal = 500, circleProgress;
    public static int PercentDaily, PercentWalk, PercentStand, PercentExecise, Percentcycle, PercentOther;
    static double dailyProgress, walkAmount, standAmount, trainAmount, cyclingAmount, otherAmount;
    static int totalwalk = 100, totalstand = 100, totalexercise = 100, totalcycling = 100, totalother = 100;


    IDataController data = ControllerRegistry.getDataController();
    IUserController bruger = ControllerRegistry.getUserController();

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_patient, container, false);
          ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_patient, container, false);


        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        values = bruger.getPatient("p1").getSensor("s1").getCurrentPeriod().getValuesList();
        days = new ArrayList<>();
        images = new ArrayList<>();

        System.out.println("Bruger: " + bruger.getPatient("p1").toString());
        System.out.println("Sensor: " + bruger.getPatient("p1").getSensor("s1").toString());


        walkAmount = Double.parseDouble(values.get(0).getWalk());
        standAmount = Double.parseDouble(values.get(0).getStand());
        cyclingAmount = Double.parseDouble(values.get(0).getCycling());
        trainAmount = Double.parseDouble(values.get(0).getExercise());
        otherAmount = Double.parseDouble(values.get(0).getOther());

        dailyProgress = walkAmount + standAmount + cyclingAmount + trainAmount + otherAmount;
        circleProgress = (int) Math.round(dailyProgress / totalProgressGoal * 100);

        completeText = view.findViewById(R.id.completeText);
        complete =view.findViewById(R.id.completeList);
        incomplete = view.findViewById(R.id.incompleteList);


        //createText(view);
        //createImages(view);
        createButtons(view);
        createProgressbar();
        previousData(view);

        for(int i = 0; i < progBarsIncom.size(); i++){
            if(progBarsIncom.get(i).getProgress()>= progBarsIncom.get(i).getGoal()) {
                progBarsIncom.get(i).setComplete(true);
                System.out.println("We done here? " + progBarsIncom.get(i).getComplete());
                progBarsCom.add(progBarsIncom.get(i));
                progBarsIncom.remove(i);
            }
        }

        IncomAdapter = new ProgressBar_adapter(getActivity(), progBarsIncom);
        comAdapter = new ProgressBar_adapter(getActivity(),progBarsCom);

        incomplete.setAdapter(IncomAdapter);
        complete.setAdapter(comAdapter);

        final Toast akt_klaret = Toast.makeText(getActivity(), "Godt klaret. Du har nået en af dine" +
                "daglige mål for i dag!", Toast.LENGTH_LONG);


        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == profile_button) {
            Fragment fragment = new patient_setting_frag();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

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
    private List<ProgBar> createProgressbar() {
        walk = new ProgBar("walk",(int) Math.round(walkAmount), totalwalk);
        stand = new ProgBar("stand",(int) Math.round(standAmount), totalstand);
        cycling = new ProgBar("cycling",(int) Math.round(cyclingAmount), totalcycling);
        train = new ProgBar("train", (int) Math.round(trainAmount), totalexercise);
        other = new ProgBar("other", (int) Math.round(otherAmount), totalother);

        progBarsIncom.add(walk);
        progBarsIncom.add(stand);
        progBarsIncom.add(cycling);
        progBarsIncom.add(train);
        progBarsIncom.add(other);

        return progBarsIncom;
    }

  /*  private void createImages(View view) {
        actionbar_image = (ImageView) view.findViewById(R.id.actionbar_image);
        today_smiley = (ImageView) view.findViewById(R.id.facetoday_image);
        stickman_walk = (ImageView) view.findViewById(R.id.walking_stickman);
        stickman_stand = (ImageView) view.findViewById(R.id.standing_stickman);
        stickman_bike = (ImageView) view.findViewById(R.id.biking_stickman);
        stickman_train = (ImageView) view.findViewById(R.id.training_stickman);
        stickman_other = (ImageView) view.findViewById(R.id.other_stickman);
    }

    }*/

    private void createButtons(View view) {
        profile_button = (ImageButton) view.findViewById(R.id.knap_profil);
        profile_button.setOnClickListener(this);
    }
/*
    public static void setPercentage() {

        PercentWalk = (int) Math.round(walkAmount / totalwalk * 100);
        PercentStand = (int) Math.round(standAmount / totalstand * 100);
        Percentcycle = (int) Math.round(cyclingAmount / totalcycling * 100);
        PercentExecise = (int) Math.round(exerciseAmount / totalexercise * 100);
        PercentOther = (int) Math.round(otherAmount / totalother * 100);

    }


    public void streakNotification(){
        int streak;
        UsageStats usageStats = new UsageStats();
        Date date = new Date();
        long currentTime = date.getTime();
        if(usageStats.getLastTimeUsed()- currentTime == 43200000){

        }

    }
    */

    public void previousData(View view) {
        days.add("i går");
        days.add(getYesterdayDateString(1));
        days.add(getYesterdayDateString(2));
        days.add(getYesterdayDateString(3));
        days.add(getYesterdayDateString(4));
        days.add(getYesterdayDateString(5));
        days.add(getYesterdayDateString(6));
        days.add(getYesterdayDateString(7));
        days.add(getYesterdayDateString(8));
        days.add(getYesterdayDateString(9));
        days.add(getYesterdayDateString(10));
        days.add(getYesterdayDateString(11));
        days.add(getYesterdayDateString(12));
        images.add(R.drawable.greensmileyrounded);
        images.add(R.drawable.greensmileyrounded);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);
        images.add(R.drawable.greensmileyrounded);
        images.add(R.drawable.greensmileyrounded);
        images.add(R.drawable.greensmileyrounded);
        images.add(R.drawable.greensmileyrounded);
        images.add(R.drawable.greensmileyrounded);
        images.add(R.drawable.greensmileyrounded);
        images.add(R.drawable.greensmileyrounded);
        images.add(R.drawable.greensmileyrounded);
        images.add(R.drawable.greensmileyrounded);
        images.add(R.drawable.greensmileyrounded);
        images.add(R.drawable.greensmileyrounded);
        images.add(R.drawable.greensmileyrounded);

        recyclerView = view.findViewById(R.id.previousList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), days, images);

        recyclerView.setAdapter(adapter);

        }

    private Date previousDay(int day) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -day);
        System.out.println(cal.getTime());
        return cal.getTime();
    }

    private String getYesterdayDateString(int day) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM");
        return dateFormat.format(previousDay(day));
    }

}