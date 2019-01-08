package e.android.sensmotion.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.user.Patient;

public class Firebase {

    private DatabaseReference mDatabase;

    public Firebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference("patients");

    }

    public DatabaseReference mDatabase() {
        return mDatabase;
    }

    public void newPatient(Patient p) {

        mDatabase.child(p.getId()).setValue(p);
    }


    public void addSensor(List<Sensor> sensors){

        for(int i = 0; i< sensors.size(); i++){
            mDatabase.child("sensors").child(Integer.toString(i));
        }

    }

    public String pushBruger() {
        return mDatabase.push().getKey();
    }

}
