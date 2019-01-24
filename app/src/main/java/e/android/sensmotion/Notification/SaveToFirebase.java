package e.android.sensmotion.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import e.android.sensmotion.controller.impl.DataController;
import e.android.sensmotion.entities.sensor.Values;
import e.android.sensmotion.entities.user.Patient;

public class SaveToFirebase extends BroadcastReceiver {

    final String TAG = getClass().getName();
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("Patients");

    DataController dc = new DataController();
    ;

    Patient patient;
    Values values;

    String userID, json;
    Calendar calender = Calendar.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
        userID = prefs.getString("userID", "p1");

        //Bliver ikke brugt, men er lavet så den gemmer en bruger til firebase, dog er der en bug, som tilføjer 300 brugere.
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
                                            String dato = sdf.format(calender.getTime());

                                            json = dc.getApiDATA(patient, dato);
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
                                            resetValues();


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

    private void resetValues() {
        editor.remove("walk");
        editor.remove("stand");
        editor.remove("cycle");
        editor.remove("exercise");
        editor.remove("other");
        editor.apply();
        editor.commit();
    }
}
