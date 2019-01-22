package e.android.sensmotion.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import e.android.sensmotion.Notification.Alarm;
import e.android.sensmotion.R;
import e.android.sensmotion.adapters.ProgressBar_adapter;
import e.android.sensmotion.controller.impl.DataController;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.sensor.Values;
import e.android.sensmotion.entities.user.Patient;
import e.android.sensmotion.views.ProgressBars.ProgBar;
import e.android.sensmotion.views.ProgressBars.ProgBarAnimation;


public class Patient_start_frag extends Fragment implements View.OnClickListener, RecyclerViewAdapter.onClickRecycle {

    private TextView circleBarText, completeText, titleName;
    private ProgressBar circlebar;
    private ImageButton profile_button;

    private RecyclerView recyclerView;
    private ProgBar walk, stand, cycling, exercise, other;
    private ListView complete, incomplete;


    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("Patients");
    private DataController dataController = new DataController();

    Calendar c = Calendar.getInstance();
    int today = c.get(Calendar.DAY_OF_YEAR);


    int yesterday, streakCount;
    private ProgressDialog loading;

    ArrayList<String> days;
    ArrayList<Integer> images;
    List<ProgBar> progBarsIncom = new ArrayList<>();
    List<ProgBar> progBarsCom = new ArrayList<>();
    List<Float> previousProgress = new ArrayList<>();

    ProgressBar_adapter IncomAdapter, comAdapter;
    ViewGroup view;
    ConstraintLayout constraintLayout;

    int totalProgressGoal, circleProgress, previousCircleProgress;
    public static int PercentWalk, PercentStand, PercentExecise, Percentcycle, PercentOther;
    static double dailyProgress, walkAmount, standAmount, exerciseAmount, cyclingAmount, otherAmount;
    static int totalwalk, totalstand, totalexercise, totalcycling, totalother;

    private Patient patient;
    Values values;
    String userID, json;
    String mobility = "0", status = "3";
    int tasksCompleted;
    boolean saved;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_patient, container, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = prefs.edit();

        if(prefs.getInt("pop_up", 0) == 0) {
            android.support.v4.app.Fragment fragment = new tooltip();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentindhold, fragment)
                    .commit();
        }
        loading = new ProgressDialog(view.getContext());

        getFirebaseStartingDate();
        setRecyclerViewFromFireBase();

        setStreak();
        inisializeElements();
        opdaterData();

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == profile_button) {
            Intent act = new Intent(getActivity(), Patient_setting_Activity.class);
            startActivity(act);
        } else if (view == constraintLayout) {
            mobility = prefs.getString("mobility", "0");
            walkAmount = prefs.getFloat("walk", 0.0f);
            standAmount = prefs.getFloat("stand", 0.0f);
            cyclingAmount = prefs.getFloat("cycle", 0.0f);
            exerciseAmount = prefs.getFloat("exercise", 0.0f);
            otherAmount = prefs.getFloat("other", 0.0f);

            // /Check wether any progress amounts exceeds the goal
            setExpectedAmount(Integer.parseInt(mobility));
            resultsExceeded();

            //Save status to SP
            if (tasksCompleted > Integer.parseInt(prefs.getString("status", "0"))) {
                editor.putString("status", tasksCompleted + "");
                editor.apply();
                editor.commit();
            }
            showElements();


            //Skal kun køres når det er nødvendigt! Det tager lang tid!
            //opdaterData();
        }
    }

    private void inisializeElements() {
        titleName = view.findViewById(R.id.nameText);
        titleName.setText(prefs.getString("name", ""));
        completeText = view.findViewById(R.id.completeText);
        circleBarText = view.findViewById(R.id.progressBarText);
        circlebar = view.findViewById(R.id.circlebar);
        constraintLayout = view.findViewById(R.id.constraintLayout);
        constraintLayout.setOnClickListener(this);

        days = new ArrayList<>();
        images = new ArrayList<>();

        userID = prefs.getString("userID", "p1");
        mobility = prefs.getString("mobility", "0");
        walkAmount = prefs.getFloat("walk", 0.0f);
        standAmount = prefs.getFloat("stand", 0.0f);
        cyclingAmount = prefs.getFloat("cycle", 0.0f);
        exerciseAmount = prefs.getFloat("exercise", 0.0f);
        otherAmount = prefs.getFloat("other", 0.0f);
        setExpectedAmount(Integer.parseInt(mobility));

        System.out.println("SP walk: " + walkAmount);
        System.out.println("SP stand: " + standAmount);
        System.out.println("SP cycle: " + cyclingAmount);
        System.out.println("SP exercise: " + exerciseAmount);
        System.out.println("SP other: " + otherAmount);

        //Alarm.alarmSaveData(getContext(), userID);

        createButtons(view);
        createLists();
        createProgressbar();
        setCirleProgress();
    }

    private void showElements() {
        completeText.setVisibility(View.VISIBLE);
        incomplete.setVisibility(View.VISIBLE);
        complete.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        progBarsIncom.clear();  //Ny dag derfor skal arraysne tømmes
        progBarsCom.clear();    //Samme grund
        createLists();
        createProgressbar();
        setCirleProgress();

    }

    public void createLists() {
        complete = view.findViewById(R.id.completeList);
        incomplete = view.findViewById(R.id.incompleteList);
        setPreviousProgress();
        //getPreviousProgres();
        IncomAdapter = new ProgressBar_adapter(getActivity(), progBarsIncom, previousProgress);
        comAdapter = new ProgressBar_adapter(getActivity(), progBarsCom, previousProgress);

        incomplete.setAdapter(IncomAdapter);
        complete.setAdapter(comAdapter);
    }

    private void createProgressbar() {
        if (progBarsIncom.size() == 0) {
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
            for (ProgBar pb : progBarsIncom) {
                if (pb.getName() == "walk") {
                    pb.setProgress((int) Math.round(walkAmount));
                } else if (pb.getName() == "stand") {
                    pb.setProgress((int) Math.round(standAmount));
                } else if (pb.getName() == "cycle") {
                    pb.setProgress((int) Math.round(cyclingAmount));
                } else if (pb.getName() == "exercise") {
                    pb.setProgress((int) Math.round(exerciseAmount));
                } else if (pb.getName() == "other") {
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

    private void createRecyclerview(String date, String status) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM");

        //Find the date of yesterday in format "dd-mm"
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = dateFormat.format(cal.getTime());
        System.out.println("format: " + yesterday);

        if (date.equals(yesterday)) {
            days.add(0, "i går");
        } else {
            days.add(0, date);
        }

        //Decide which smiley to display
        if (status.equals("0")) {
            images.add(0, R.drawable.baseline_sentiment_very_satisfied_black_48);
        } else if (status.equals("1")) {
            images.add(0, R.drawable.greensmiley1);
        } else if (status.equals("2")) {
            images.add(0, R.drawable.greensmiley2);
        } else if (status.equals("3")) {
            images.add(0, R.drawable.baseline_sentiment_very_satisfied_black_48);
        }


        recyclerView = view.findViewById(R.id.previousList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), recyclerView, days, images, this);
        recyclerView.setAdapter(adapter);

        /*
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        recyclerView.startAnimation(animation);
        */
    }

    private void setCirleProgress() {
        dailyProgress = walkAmount + standAmount + cyclingAmount + exerciseAmount + otherAmount;
        totalProgressGoal = totalwalk + totalstand + totalcycling + totalexercise + totalother;
        circleProgress = (int) Math.round(dailyProgress / totalProgressGoal * 100);


        System.out.println("Daily: " + dailyProgress);
        System.out.println("Daily goal: " + totalProgressGoal);


        circleBarText.setText(circleProgress + "%");
        circlebar.setRotation(-90);
        ProgBarAnimation anim = new ProgBarAnimation(circlebar, previousCircleProgress, circleProgress);
        anim.setDuration(300);
        circlebar.startAnimation(anim);

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

    private void setExpectedAmount(int m) {
        switch (m) {
            case 0:
                totalwalk = 10;
                totalstand = 15;
                totalcycling = 2;
                totalexercise = 5;
                totalother = 10;
                break;

            case 1:
                totalwalk = 12;
                totalstand = 17;
                totalcycling = 4;
                totalexercise = 7;
                totalother = 12;
                break;

            case 2:
                totalwalk = 16;
                totalstand = 21;
                totalcycling = 8;
                totalexercise = 11;
                totalother = 16;
                break;

            case 3:
                totalwalk = 24;
                totalstand = 29;
                totalcycling = 16;
                totalexercise = 19;
                totalother = 24;
                break;

            case 4:
                totalwalk = 40;
                totalstand = 45;
                totalcycling = 32;
                totalexercise = 35;
                totalother = 40;
                break;

            case 5:
                totalwalk = 72;
                totalstand = 77;
                totalcycling = 64;
                totalexercise = 67;
                totalother = 72;
                break;

            default:
                totalwalk = 10;
                totalstand = 15;
                totalcycling = 2;
                totalexercise = 5;
                totalother = 10;
                break;
        }
    }

    private void resultsExceeded() {
        tasksCompleted = 0;
        if (walkAmount > totalwalk) {
            walkAmount = totalwalk;
            tasksCompleted++;
        }
        if (standAmount > totalstand) {
            standAmount = totalstand;
            tasksCompleted++;
        }
        if (cyclingAmount > totalcycling) {
            cyclingAmount = totalcycling;
            tasksCompleted++;
        }
        if (exerciseAmount > totalexercise) {
            exerciseAmount = totalexercise;
            tasksCompleted++;
        }
        if (otherAmount > totalother) {
            otherAmount = totalother;
            tasksCompleted++;
        }
    }

    private void opdaterData() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Get Patient
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("id").getValue(String.class).equals(userID)) {
                        patient = snapshot.getValue(Patient.class);

                        //Get Sensor
                        for (final DataSnapshot snapshotSensor : dataSnapshot.child(snapshot.getKey()).child("sensorer").getChildren()) {

                            //Get API data
                            AsyncTask atask = new AsyncTask() {
                                @Override
                                protected void onPreExecute() {
                                    loading.setMessage("\t Henter data...");
                                    loading.show();
                                }

                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    try {
                                        String myFormat = "yyyy-MM-dd";
                                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                        String dato = sdf.format(c.getTime());
                                        //String dato = "2018-10-01";

                                        json = dataController.getApiDATA(patient, dato);
                                        return null;
                                    } catch (Exception e) {
                                        return e;
                                    }
                                }

                                @Override
                                protected void onPostExecute(Object titler) {
                                    JSONObject data = null;
                                    try {
                                        data = new JSONObject(json);
                                        values = new Values();
                                        values.getAPIdata(data);

                                        values.setMobility(patient.getMobility());
                                        mobility = values.getMobility();
                                        System.out.println("Mobilitet: " + mobility);
                                        setExpectedAmount(Integer.parseInt(mobility));

                                        walkAmount = Double.parseDouble(values.getWalk());
                                        standAmount = Double.parseDouble(values.getStand());
                                        cyclingAmount = Double.parseDouble(values.getCycling());
                                        exerciseAmount = Double.parseDouble(values.getExercise());
                                        otherAmount = Double.parseDouble(values.getOther());
                                        resultsExceeded();
                                        loading.dismiss();

                                        //Save mobility to SP
                                        editor.putString("mobility", mobility);

                                        //Skal laves om så vi tjekker mod api'en...
                                        if (prefs.getFloat("walk", 0.0f) != walkAmount ||
                                                prefs.getFloat("stand", 0.0f) != standAmount ||
                                                prefs.getFloat("cycle", 0.0f) != cyclingAmount ||
                                                prefs.getFloat("exercise", 0.0f) != exerciseAmount ||
                                                prefs.getFloat("other", 0.0f) != otherAmount) {

                                            editor.putFloat("walk", (float) walkAmount);
                                            editor.putFloat("stand", (float) standAmount);
                                            editor.putFloat("cycle", (float) cyclingAmount);
                                            editor.putFloat("exercise", (float) exerciseAmount);
                                            editor.putFloat("other", (float) otherAmount);
                                            editor.apply();
                                            editor.commit();

                                            showElements();

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.execute();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setRecyclerViewFromFireBase() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                //days.add("i går");
                //Get Patient
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("id").getValue(String.class).equals(userID)) {
                        patient = snapshot.getValue(Patient.class);

                        //For each Sensor in database
                        for (DataSnapshot snapshotSensor : dataSnapshot.child(snapshot.getKey()).child("sensorer").getChildren()) {
                            List<Sensor> sensorList = new ArrayList<>();

                            //For each "Day value" in database
                            for (final DataSnapshot snapshotValues : snapshotSensor.child("currentPeriod").child("valuesList").getChildren()) {
                                String date = snapshotValues.child("date").getValue(String.class);
                                String status = snapshotValues.child("status").getValue(String.class);
                                createRecyclerview(date, status);
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

    private void getFirebasePatient(final String day) {
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
                            for (DataSnapshot snapshotValues : snapshotSensor.child("currentPeriod").child("valuesList").getChildren()) {

                                //This is necessary because we reverse the order of days in the recyclerview's array
                                String formatedDay = Math.round(snapshotSensor.child("currentPeriod").child("valuesList").getChildrenCount())-1 - Integer.parseInt(day)+"";
                                if (snapshotValues.getKey().equals(formatedDay)) {
                                    Values values = snapshotValues.getValue(Values.class);
                                    mobility = values.getMobility();
                                    status = values.getStatus();
                                    walkAmount = Double.parseDouble(values.getWalk());
                                    standAmount = Double.parseDouble(values.getStand());
                                    cyclingAmount = Double.parseDouble(values.getCycling());
                                    exerciseAmount = Double.parseDouble(values.getExercise());
                                    otherAmount = Double.parseDouble(values.getOther());

                                    System.out.println("mobilitet: " + mobility);
                                    setExpectedAmount(Integer.parseInt(mobility));
                                    resultsExceeded();

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

    private void getFirebaseStartingDate() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //For each Patient in database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("id").getValue(String.class).equals(userID)) {
                        patient = snapshot.getValue(Patient.class);

                        //For each Sensor in database
                        for (DataSnapshot snapshotSensor : dataSnapshot.child(snapshot.getKey()).child("sensorer").getChildren()) {

                            String startingDate = snapshotSensor.child("currentPeriod").child("startingDate").getValue(String.class);
                            System.out.println("Staring date: " + startingDate);

                            editor.putString("startingDate", startingDate);
                            editor.apply();
                            editor.commit();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveDataFirebase() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Get Patient
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("id").getValue(String.class).equals(userID)) {
                        patient = snapshot.getValue(Patient.class);

                        //Get Sensor
                        for (final DataSnapshot snapshotSensor : dataSnapshot.child(snapshot.getKey()).child("sensorer").getChildren()) {
                            if (snapshotSensor.child("id").getValue(String.class).equals("s1")) {
                                //Get API data
                                AsyncTask atask = new AsyncTask() {
                                    @Override
                                    protected Object doInBackground(Object[] objects) {
                                        try {
                                            String myFormat = "yyyy-MM-dd";
                                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                            String dato = sdf.format(c.getTime());

                                            json = dataController.getApiDATA(patient, dato);
                                            return null;
                                        } catch (Exception e) {
                                            return e;
                                        }
                                    }

                                    @Override
                                    protected void onPostExecute(Object titler) {
                                        JSONObject data = null;
                                        try {
                                            data = new JSONObject(json);
                                            values = new Values();
                                            values.getAPIdata(data);
                                            values.setMobility(patient.getMobility());
                                            values.setStatus(prefs.getString("status", "0"));

                                            String dayCount = Math.round(snapshotSensor.child("currentPeriod").child("valuesList").getChildrenCount()) + "";
                                            database.child(userID).child("sensorer").child("0").child("currentPeriod").child("valuesList").child(dayCount).setValue(values);
                                            System.out.println("Data saved...");


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }.execute();
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
        setPreviousProgress();
        getFirebasePatient("" + position);
    }

    public void setPreviousProgress() {
        for (ProgBar pb : progBarsCom)
            previousProgress.add((float) pb.getPercent());

        for (ProgBar pb : progBarsIncom)
            previousProgress.add((float) pb.getPercent());

        previousCircleProgress = circleProgress;



        /*
        Set<String> prefPrevProgress = new HashSet<String>();
        List<String> prevString = new ArrayList<>();
        for(ProgBar pb: progBarsIncom){
            previousProgress.add((float)pb.getPercent());
        }
        for(ProgBar pb: progBarsCom){
            previousProgress.add((float)pb.getPercent());
        }
        */
    }
}