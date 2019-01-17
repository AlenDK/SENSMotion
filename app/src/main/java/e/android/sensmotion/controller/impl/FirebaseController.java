package e.android.sensmotion.controller.impl;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IFirebaseController;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.sensor.Values;
import e.android.sensmotion.entities.user.Patient;

public class FirebaseController implements IFirebaseController {
    private DatabaseReference database;
    private String test = "";
    private IUserController uc;
    private List<Patient> list;

    public FirebaseController() {
        database = FirebaseDatabase.getInstance().getReference("Patients");
    }

    public void setValueListener() {

        list = new ArrayList<>();
        uc = ControllerRegistry.getUserController();


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Patient p = snapshot.getValue(Patient.class);
                    list.add(p);
                }
                uc.setPatientList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void newPatient (Patient p) {

        database.child(p.getId()).setValue(p);

    }

}
