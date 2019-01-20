package e.android.sensmotion.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.sensor.Values;
import e.android.sensmotion.entities.user.Patient;
import e.android.sensmotion.views.ProgressBars.ProgBar;


public class Patient_start_frag extends Fragment implements View.OnClickListener, RecyclerViewAdapter.onClickRecycle {

    private TextView circleBarText, completeText;
    private ProgressBar circlebar;
    private ImageButton profile_button;
    private RecyclerView recyclerView;
    private ProgBar walk, stand, cycling, exercise, other;
    private ListView complete, incomplete;


    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private DatabaseReference database;
    private DataController dataController;

    Calendar c = Calendar.getInstance();
    int today = c.get(Calendar.DAY_OF_YEAR);


    int yesterday, streakCount;

    ArrayList<String> days;
    ArrayList<Integer> images;
    List<ProgBar> progBarsIncom = new ArrayList<>();
    List<ProgBar> progBarsCom = new ArrayList<>();


    ProgressBar_adapter IncomAdapter, comAdapter;
    ViewGroup view;

    int totalProgressGoal = 500, circleProgress;
    public static int PercentWalk, PercentStand, PercentExecise, Percentcycle, PercentOther;
    static double dailyProgress, walkAmount, standAmount, exerciseAmount, cyclingAmount, otherAmount;
    static int totalwalk = 100, totalstand = 100, totalexercise = 100, totalcycling = 100, totalother = 100;

    private Patient patient;
    private String userID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_patient, container, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = prefs.edit();

        userID = prefs.getString("userID", "p1");
        days = new ArrayList<>();
        images = new ArrayList<>();

        setStreak();
        inisializeElements();
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

    private void inisializeElements() {
        completeText  = view.findViewById(R.id.completeText);
        circleBarText = view.findViewById(R.id.progressBarText);
        circlebar     = view.findViewById(R.id.circlebar);


        walkAmount  = prefs.getFloat("walk", 0.0f);
        standAmount = prefs.getFloat("stand", 0.0f);
        cyclingAmount = prefs.getFloat("cycle", 0.0f);
        exerciseAmount = prefs.getFloat("exercise", 0.0f);
        otherAmount = prefs.getFloat("other", 0.0f);


        createButtons(view);
        createRecyclerview(view);
        createLists();
        createProgressbar();
        setCirleProgress();
    }

    private void showElements(){
        completeText.setVisibility(View.VISIBLE);
        incomplete.setVisibility(View.VISIBLE);
        complete.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        createLists();
        createProgressbar();
        setCirleProgress();
    }

    public void createLists() {
        complete = view.findViewById(R.id.completeList);
        incomplete = view.findViewById(R.id.incompleteList);
        IncomAdapter = new ProgressBar_adapter(getActivity(), progBarsIncom);
        comAdapter = new ProgressBar_adapter(getActivity(), progBarsCom);

        incomplete.setAdapter(IncomAdapter);
        complete.setAdapter(comAdapter);
    }

    private void createProgressbar() {
        if(progBarsIncom.size() == 0){
            System.out.println("the fuck");
            walk = new ProgBar("walk", (int) Math.round(walkAmount), totalwalk);
            stand = new ProgBar("stand", (int) Math.round(standAmount), totalstand);
            cycling = new ProgBar("cycle", (int) Math.round(cyclingAmount), totalcycling);
            exercise = new ProgBar("exercise", (int) Math.round(exerciseAmount), totalexercise);
            other = new ProgBar("other", (int) Math.round(otherAmount), totalother);

            progBarsIncom.add(walk);
            progBarsIncom.add(stand);
            progBarsIncom.add(cycling);
            progBarsIncom.add(exercise);
            progBarsIncom.add(other);
        } else {
            System.out.println("yes");
            for(ProgBar pb : progBarsIncom){
                if(pb.getName() == "walk"){
                    pb.setProgress((int) Math.round(walkAmount));
                } else if(pb.getName() == "stand"){
                    pb.setProgress((int) Math.round(standAmount));
                } else if(pb.getName() == "cycle"){
                    pb.setProgress((int) Math.round(cyclingAmount));
                } else if(pb.getName() == "exercise"){
                    pb.setProgress((int) Math.round(exerciseAmount));
                } else if(pb.getName() == "other"){
                    pb.setProgress((int) Math.round(otherAmount));
                }
            }
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
        for (int i = 0; i < progBarsIncom.size(); i++) {
            if (progBarsIncom.get(i).getProgress() >= progBarsIncom.get(i).getGoal()) {
                progBarsIncom.get(i).setComplete(true);
                progBarsCom.add(progBarsIncom.get(i));
                progBarsIncom.remove(i);
                i--;    //Ellers springer vi over hver anden
            }
        }
        if (progBarsCom.size() == 0) {
            completeText.setVisibility(View.GONE);
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
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), recyclerView, days, images,this);
        recyclerView.setAdapter(adapter);


        /*
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        recyclerView.startAnimation(animation);
        */
    }

    private void setCirleProgress(){
        dailyProgress = walkAmount + standAmount + cyclingAmount + exerciseAmount + otherAmount;
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

    private void getFirebasePatient(final String day) {
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
                                if(snapshotValues.getKey().equals(day)) {

                                    Values values = snapshotValues.getValue(Values.class);
                                    walkAmount    = Double.parseDouble(values.getWalk());
                                    standAmount   = Double.parseDouble(values.getStand());
                                    cyclingAmount = Double.parseDouble(values.getCycling());
                                    exerciseAmount = Double.parseDouble(values.getExercise());
                                    otherAmount   = Double.parseDouble(values.getOther());

                                    System.out.println("før");
                                    System.out.println("otherAmount: "+(int) otherAmount);
                                    System.out.println("otherTotal: "+totalother);
                                    if((int) walkAmount > totalwalk) {
                                        walkAmount = totalwalk;
                                    } else if ((int) standAmount > totalstand) {
                                        standAmount = totalstand;
                                    } else if ((int) cyclingAmount > totalcycling) {
                                        cyclingAmount = totalcycling;
                                    } else if ((int) exerciseAmount > totalexercise) {
                                        exerciseAmount = totalexercise;
                                    } else if (otherAmount > totalother) {
                                        System.out.println("yes hello");
                                        otherAmount = totalother;
                                    }
                                    System.out.println("efter");

                                    /*
                                    Sensor s = snapshotSensor.getValue(Sensor.class);
                                    walkAmount = Double.parseDouble(s.getCurrentPeriod().getValuesList().get(0).getWalk());
                                    standAmount = Double.parseDouble(s.getCurrentPeriod().getValuesList().get(0).getStand());
                                    cyclingAmount = Double.parseDouble(s.getCurrentPeriod().getValuesList().get(0).getCycling());
                                    exerciseAmount = Double.parseDouble(s.getCurrentPeriod().getValuesList().get(0).getExercise());
                                    otherAmount = Double.parseDouble(s.getCurrentPeriod().getValuesList().get(0).getOther());
                                    */


                                    //Skal laves om så vi tjekker mod api'en...
                                    if(prefs.getFloat("walk", 0.0f) != walkAmount   ||
                                            prefs.getFloat("stand", 0.0f)!= standAmount  ||
                                            prefs.getFloat("cycle", 0.0f)!= cyclingAmount||
                                            prefs.getFloat("exercise", 0.0f) != exerciseAmount ||
                                            prefs.getFloat("other", 0.0f)!= otherAmount){

                                        editor.putFloat("walk", (float) walkAmount);
                                        editor.putFloat("stand",(float) standAmount);
                                        editor.putFloat("cycle",(float) cyclingAmount);
                                        editor.putFloat("exercise",(float) exerciseAmount);
                                        editor.putFloat("other",(float) otherAmount);
                                        editor.apply();
                                        editor.commit();
                                    }
                                    showElements();
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

    @Override
    public void clickItem(int position) {
        progBarsIncom.clear();  //Ny dag derfor skal arraysne tømmes
        progBarsCom.clear();    //Samme grund
        getFirebasePatient(""+position);
    }

    public void opdaterData(){
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