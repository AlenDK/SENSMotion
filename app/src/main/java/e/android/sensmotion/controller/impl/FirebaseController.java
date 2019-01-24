package e.android.sensmotion.controller.impl;

import android.support.annotation.NonNull;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IFirebaseController;
import e.android.sensmotion.controller.interfaces.ISensorController;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.sensor.Values;
import e.android.sensmotion.entities.user.Patient;
import e.android.sensmotion.views.Patientliste_frag;
import e.android.sensmotion.views.Terapuet_activity;

public class FirebaseController implements IFirebaseController {
    private DatabaseReference database;
    private IUserController uc;
    private ISensorController sc;
    private List<Patient> list;

    public FirebaseController() {
        database = FirebaseDatabase.getInstance().getReference("Patients");

    }

    public void getPatientFirebase(final String id) {
        uc = ControllerRegistry.getUserController();
        sc = ControllerRegistry.getSensorController();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Patient p = null;
                System.out.println("hallo");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    System.out.println("fiiiiiiisse: " + snapshot.child(id).child(id).getValue(String.class));
                    if (snapshot.child("id").getValue(String.class).equals(id)) {
                        p = snapshot.getValue(Patient.class);
                        System.out.println("key: " + dataSnapshot.getKey());

                        for (DataSnapshot snapshotSensor : dataSnapshot.child(snapshot.getKey()).child("sensorer").getChildren()) {
                            List<Sensor> sensorList = new ArrayList<>();
                            Sensor s = snapshotSensor.getValue(Sensor.class);
                            sensorList.add(s);
                            p.setSensors(sensorList);
                        }
                    }
                    System.out.println(list);
                    uc.setPatient(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setValueListener() {

        list = new ArrayList<>();
        uc = ControllerRegistry.getUserController();
        sc = ControllerRegistry.getSensorController();


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Patient p = snapshot.getValue(Patient.class);

                    for (DataSnapshot snapshotSensor : dataSnapshot.child(snapshot.getKey()).child("sensorer").getChildren()) {
                        List<Sensor> sensorList = new ArrayList<>();
                        Sensor s = snapshotSensor.getValue(Sensor.class);
                        sensorList.add(s);
                        p.setSensors(sensorList);
                    }
                    list.add(p);

                }
                System.out.println("hello: " + list);
                uc.setPatientList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void newPatient(Patient p) {

        database.child(p.getId()).setValue(p);

    }

    public void updateViews(View view) {

    }

}
