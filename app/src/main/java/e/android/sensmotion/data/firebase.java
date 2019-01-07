package e.android.sensmotion.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import e.android.sensmotion.entities.user.Patient;

public class firebase {

    private DatabaseReference mDatabase;

    public firebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference("brugere");

    }

    public DatabaseReference mDatabase() {
        return mDatabase;
    }

    public void newPatient(String id, String name, int score) {
         Patient patient = new Patient(id, name, score);

        mDatabase.child(id).setValue(patient);
    }


    public String pushBruger() {
        return mDatabase.push().getKey();
    }

}
