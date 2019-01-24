package e.android.sensmotion.controller.impl;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import e.android.sensmotion.controller.interfaces.ISensorController;
import e.android.sensmotion.entities.sensor.Sensor;

public class SensorController implements ISensorController {

    private List<Sensor> sensorList = new ArrayList<>();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    Sensor sensor;

    public SensorController() {

        Sensor sensor1 = new Sensor("s1", 0);

        sensorList.add(sensor1);

    }

    public Sensor getSensor(final String id) {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Object> sensorer = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    if (dataSnapshot.child("p1").child("sensorer").child("0").child("id").getValue(String.class) == id) {
                        System.out.println("suck my peepee");
                    }
                    //Sensorer
                    //sensor = new Sensor(    dataSnapshot.child("p1").child("sensorer").child("0").child("id").getValue(String.class),
                    //dataSnapshot.child("p1").child("sensorer").child("0").child("placement").getValue(Integer.class));
                    //sensorer.add(sensor);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //Direkte fra API
        for (Sensor s : sensorList) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }


}
