package e.android.sensmotion.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import e.android.sensmotion.entities.sensor.Period;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.sensor.Values;
import e.android.sensmotion.entities.user.Patient;
import e.android.sensmotion.entities.user.User;

public class Firebase {

    private DatabaseReference mDatabase, dTest;

    public Firebase() {
        dTest = FirebaseDatabase.getInstance().getReference("test");
        mDatabase = FirebaseDatabase.getInstance().getReference("Period");
    }

    public DatabaseReference mDatabase() {
        return mDatabase;
    }

    public DatabaseReference dTest() {
        return dTest;
    }


    public void newPeriod(List<Values> valuesList, Values values, int day_count) {
        Period period = new Period(day_count);

        mDatabase.child("valuesList").setValue(period);
    }

    /*
    public void newBruger(String id, String brugernavn, String adgangskode, String cpr, List<Sensor> sensors, String mobility, String project_key, String patient_key) {
        Patient patient = new Patient(id, brugernavn, adgangskode, cpr, sensors, mobility, project_key, patient_key);

        dTest.child("patient").setValue(patient);
    } */

    public void newPatient (Patient p) {

        dTest.child(p.getId()).setValue(p);

    }


    public void newSensor ( List<Sensor> sensors) {

        for (int i = 0; i < sensors.size(); i++) {
            dTest.child(""+i).child(sensors.get(i).getId()).setValue(sensors.get(i));
        }
    }


   // public void newTest() {
     //   Sensor sensor = new Sensor(id, placement);
//
  //      dTest.child("testing").setValue(sensor);
  //  }

/*
        mDatabase.addValueEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Patient bruger = dataSnapshot.getValue(Patient.class);
                System.out.println("Sensor " + bruger.getSensorer());



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }

*/






    public String pushBruger() {
        return mDatabase.push().getKey();
    }

}