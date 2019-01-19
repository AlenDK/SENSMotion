package e.android.sensmotion.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import e.android.sensmotion.R;
import e.android.sensmotion.adapters.ProgressBar_adapter;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.impl.FirebaseController;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.controller.interfaces.IFirebaseController;
import e.android.sensmotion.controller.interfaces.ISensorController;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.sensor.Values;
import e.android.sensmotion.entities.user.Patient;
import e.android.sensmotion.views.ProgressBars.ProgBar;


public class Patient_start_frag extends Fragment implements View.OnClickListener {

    private TextView circleBarText, completeText;
    private ProgressBar circlebar;
    private ImageButton profile_button;
    private RecyclerView recyclerView;
    private ProgBar walk, stand, cycling, train, other;
    private ListView complete, incomplete;


    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private DatabaseReference database;

    Calendar c = Calendar.getInstance();
    int today = c.get(Calendar.DAY_OF_YEAR);


    int yesterday, streakCount;

    List<Values> values;
    ArrayList<String> days;
    ArrayList<Integer> images;
    List<ProgBar> progBarsIncom = new ArrayList<>();
    List<ProgBar> progBarsCom = new ArrayList<>();


    ProgressBar_adapter IncomAdapter, comAdapter;


    int totalProgressGoal = 500, circleProgress;
    public static int PercentDaily, PercentWalk, PercentStand, PercentExecise, Percentcycle, PercentOther;
    static double dailyProgress, walkAmount, standAmount, trainAmount, cyclingAmount, otherAmount;
    static int totalwalk = 100, totalstand = 100, totalexercise = 100, totalcycling = 100, totalother = 100;

    private Patient patient;
    private String userID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_patient, container, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = prefs.edit();

        userID = prefs.getString("userID", "p1");

        setStreak();
        Log.d("test", "" + prefs.getInt("streakCounter", 0));

        Log.d("test", "" + streakCount);
        Log.d("test", "" + today);
        Log.d("test", "" + yesterday);


        days = new ArrayList<>();
        images = new ArrayList<>();

        dailyProgress = 0;


        inisializeElements(view);
        getFirebasePatient("0");


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

    private void inisializeElements(View view) {
        completeText  = view.findViewById(R.id.completeText);
        circleBarText = view.findViewById(R.id.progressBarText);
        circlebar     = view.findViewById(R.id.circlebar);

        walkAmount  = prefs.getFloat("walk", 0.0f);
        standAmount = prefs.getFloat("stand", 0.0f);
        cyclingAmount = prefs.getFloat("cycle", 0.0f);
        trainAmount = prefs.getFloat("exercise", 0.0f);
        otherAmount = prefs.getFloat("other", 0.0f);



        createButtons(view);
        createRecyclerview(view);
        createProgressbar();
        createLists(view);
        setCirleProgress();

        //hideElements();
    }

    private void showElements(){
        completeText.setVisibility(View.VISIBLE);
        incomplete.setVisibility(View.VISIBLE);
        complete.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        createProgressbar();
        setCirleProgress();
    }

    private void hideElements(){
        completeText.setVisibility(View.GONE);
        incomplete.setVisibility(View.GONE);
        complete.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    public void createLists(View view) {
        complete = view.findViewById(R.id.completeList);
        incomplete = view.findViewById(R.id.incompleteList);
        IncomAdapter = new ProgressBar_adapter(getActivity(), progBarsIncom);
        comAdapter = new ProgressBar_adapter(getActivity(), progBarsCom);

        incomplete.setAdapter(IncomAdapter);
        complete.setAdapter(comAdapter);
    }

    private void createProgressbar() {
        walk = new ProgBar("walk", (int) Math.round(walkAmount), totalwalk);
        stand = new ProgBar("stand", (int) Math.round(standAmount), totalstand);
        cycling = new ProgBar("bike", (int) Math.round(cyclingAmount), totalcycling);
        train = new ProgBar("exercise", (int) Math.round(trainAmount), totalexercise);
        other = new ProgBar("other", (int) Math.round(otherAmount), totalother);

        if(progBarsIncom.size() == 0){
            progBarsIncom.add(walk);
            progBarsIncom.add(stand);
            progBarsIncom.add(cycling);
            progBarsIncom.add(train);
            progBarsIncom.add(other);
        } else {
            walk.setProgress((int) Math.round(walkAmount));
            stand.setProgress((int) Math.round(standAmount));
            cycling.setProgress((int) Math.round(cyclingAmount));
            train.setProgress((int) Math.round(trainAmount));
            other.setProgress((int) Math.round(otherAmount));
        }

        sortProgressbars(progBarsIncom);
        completeProgressbars();


    }

    private void sortProgressbars(List<ProgBar> bars) {
        int length = bars.size();

        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (bars.get(j).getProgress() < bars.get(j + 1).getProgress()) {
                    ProgBar temp = bars.get(j);
                    bars.remove(j);
                    bars.add(j + 1, temp);
                }
            }
        }
    }

    private void completeProgressbars() {
        if (progBarsCom.size() == 0) {
            completeText.setVisibility(View.GONE);
        }
        for (int i = 0; i < progBarsIncom.size(); i++) {
            if (progBarsIncom.get(i).getProgress() >= progBarsIncom.get(i).getGoal()) {
                progBarsIncom.get(i).setComplete(true);
                progBarsCom.add(progBarsIncom.get(i));
                progBarsIncom.remove(i);
                i--;    //Ellers springer vi over hver anden
            }
        }
    }

    private void createButtons(View view) {
        profile_button = (ImageButton) view.findViewById(R.id.knap_profil);
        profile_button.setOnClickListener(this);
    }

    private void createRecyclerview(View view) {
        days.add("i går");
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
        days.add(getYesterdayDateString(13));
        images.add(R.drawable.greensmileyrounded);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);
        images.add(R.drawable.baseline_sentiment_very_satisfied_black_48);

        recyclerView = view.findViewById(R.id.previousList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), recyclerView, days, images);
        recyclerView.setAdapter(adapter);


        /*
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        recyclerView.startAnimation(animation);
        */
    }

    private void setCirleProgress(){
        dailyProgress = walkAmount + standAmount + cyclingAmount + trainAmount + otherAmount;
        circleProgress = (int) Math.round(dailyProgress / totalProgressGoal * 100);

        circleBarText.setText(circleProgress + "%");
        circlebar.setProgress(circleProgress);
        circlebar.setRotation(-90);
    }

    public void setStreak() {
        yesterday = prefs.getInt("yesterday", 0);
        streakCount = prefs.getInt("streakCounter", 0);

        if (today - 1 == yesterday) {
            streakCount++;
            prefs.edit().putInt("yesterday", today).commit();
            prefs.edit().putInt("streakCounter", streakCount).commit();
        } else if (today == yesterday) {
   /*        if(prefs.getInt("streakCounter", 0) == 0) {
               prefs.edit().putInt("streakCounter", 1).commit();
           }
*/
        } else {
            prefs.edit().putInt("yesterday", today).commit();
            prefs.edit().putInt("streakCounter", 1).commit();
        }
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

    private void getFirebasePatient(final String today) {
        database = FirebaseDatabase.getInstance().getReference("Patients");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //For each Patient in database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("id").getValue(String.class).equals(userID)) {
                        patient = snapshot.getValue(Patient.class);

                        //For each Sensor in database
                        for (DataSnapshot snapshotSensor : dataSnapshot.child(snapshot.getKey()).child("sensorer").getChildren()) {
                            List<Sensor> sensorList = new ArrayList<>();

                            //For each "Day value" in database
                            for(DataSnapshot snapshotValues : snapshotSensor.child("currentPeriod").child("valuesList").getChildren()){
                                if(snapshotValues.getKey().equals(today)) {

                                    Values values = snapshotValues.getValue(Values.class);
                                    walkAmount    = Double.parseDouble(values.getWalk());
                                    standAmount   = Double.parseDouble(values.getStand());
                                    cyclingAmount = Double.parseDouble(values.getCycling());
                                    trainAmount   = Double.parseDouble(values.getExercise());
                                    otherAmount   = Double.parseDouble(values.getOther());

                                    /*
                                    Sensor s = snapshotSensor.getValue(Sensor.class);
                                    walkAmount = Double.parseDouble(s.getCurrentPeriod().getValuesList().get(0).getWalk());
                                    standAmount = Double.parseDouble(s.getCurrentPeriod().getValuesList().get(0).getStand());
                                    cyclingAmount = Double.parseDouble(s.getCurrentPeriod().getValuesList().get(0).getCycling());
                                    trainAmount = Double.parseDouble(s.getCurrentPeriod().getValuesList().get(0).getExercise());
                                    otherAmount = Double.parseDouble(s.getCurrentPeriod().getValuesList().get(0).getOther());
                                    */

                                    if(prefs.getFloat("walk", 0.0f) != walkAmount   ||
                                            prefs.getFloat("stand", 0.0f)!= standAmount  ||
                                            prefs.getFloat("cycle", 0.0f)!= cyclingAmount||
                                            prefs.getFloat("exercise", 0.0f) != trainAmount ||
                                            prefs.getFloat("other", 0.0f)!= otherAmount){

                                        editor.putFloat("walk", (float) walkAmount);
                                        editor.putFloat("stand",(float) standAmount);
                                        editor.putFloat("cycle",(float) cyclingAmount);
                                        editor.putFloat("exercise",(float) trainAmount);
                                        editor.putFloat("other",(float) otherAmount);
                                        editor.apply();
                                        editor.commit();

                                        showElements();
                                    }

                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}