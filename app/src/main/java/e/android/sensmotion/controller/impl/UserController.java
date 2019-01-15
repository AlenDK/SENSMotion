package e.android.sensmotion.controller.impl;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.controller.interfaces.ISensorController;
import e.android.sensmotion.data.Firebase;
import e.android.sensmotion.data.FirebaseController;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.user.User;
import e.android.sensmotion.entities.user.Patient;
import e.android.sensmotion.entities.user.Therapist;

public class UserController implements IUserController {

    private List<User> userList = new ArrayList<User>();
    private List<Patient> patientList = new ArrayList<Patient>();
    private ISensorController sc;
    private DatabaseReference database;
    FirebaseController fbc;

    String hent = null;
    Patient patient;

    public UserController(){

        database = FirebaseDatabase.getInstance().getReference();
        sc = ControllerRegistry.getSensorController();
        fbc = new FirebaseController();


        List<Sensor> p1Sensorer = new ArrayList<>();
        p1Sensorer.add(sc.getSensor("s1"));

        Patient patient1 = new Patient("p1","p1", "p1", "p1", p1Sensorer,
                null, "k5W2uX", "6rT39u");
        userList.add(patient1);

        Patient patient2 = new Patient("p2", "p2", "p2", "p2",
                null, null, null, null);
        userList.add(patient2);

        Patient patient3 = new Patient("p3", "p3", "p3", "p3",
                null, null, null, null);
        userList.add(patient3);

        patientList.add(patient1);
        //patientList.add(patient2);
        //patientList.add(patient3);

        Therapist therapist1 = new Therapist("t1", "t1", "t1", patientList);
        userList.add(therapist1);
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<Patient> getPatientList() { return patientList; }

    public User getUser(String id){
        for(User b : userList){
            if(b.getId().equals(id)){
                return b;
            }
        }
        return null;
    }

    public Patient getPatient(final String id){
        //Direkte fra API
        for(Patient p : patientList){
            if(p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }

    public void savePatient(Patient p){
        Firebase firebase = new Firebase();
        firebase.newPatient(p);
    }
}
