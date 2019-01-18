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
import e.android.sensmotion.controller.interfaces.ISensorController;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.sensor.Values;
import e.android.sensmotion.entities.user.Patient;

public class FirebaseController implements IFirebaseController {
    private DatabaseReference database;
    private String test = "";
    private IUserController uc;
    private ISensorController sc;
    private List<Patient> list;

    public FirebaseController() {
        database = FirebaseDatabase.getInstance().getReference("Patients");
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

                    for(DataSnapshot snapshotSensor: dataSnapshot.child(snapshot.getKey()).child("sensorer").getChildren()){
                        List<Sensor> sensorList = new ArrayList<>();
                        Sensor s = snapshotSensor.getValue(Sensor.class);
                        sensorList.add(s);
                        p.setSensors(sensorList);
                    }
                    list.add(p);

                }
                uc.setPatientList(list);


                /*
                for(DataSnapshot snapshot : dataSnapshot.child("p1").child("sensorer").getChildren()){
                    Sensor s = snapshot.getValue(Sensor.class);
                    System.out.println("///////////////////////////////////////////////////////");
                    System.out.println("///////////////////////////////////////////////////////");
                    System.out.println("///////////////////////////////////////////////////////");
                    System.out.println(s.getId());
                    System.out.println("///////////////////////////////////////////////////////");
                    System.out.println("///////////////////////////////////////////////////////");
                    System.out.println("///////////////////////////////////////////////////////");

                    sensorList.add(s);

                }
                uc.addSensorToPatient(sensorList, "p1");
                */

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
