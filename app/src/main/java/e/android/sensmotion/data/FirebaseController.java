package e.android.sensmotion.data;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.sensor.Values;
import e.android.sensmotion.entities.user.Patient;

public class FirebaseController {
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    String test = "";

    public FirebaseController() {
    }

    public Patient getPatient(){
        return null;
    }

    public Sensor getSensor(){
        return null;
    }

    public Values getValues(){
        Values values = new Values();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    test = d.child("p1").child("sensorer").child("0").child("currentPeriod").child("valuesList").child("0").child("cycling").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        System.out.println("Testotot:  "+test);
        return null;
    }
}
