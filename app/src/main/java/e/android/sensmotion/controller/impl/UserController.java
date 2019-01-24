package e.android.sensmotion.controller.impl;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.controller.interfaces.ISensorController;
import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.user.User;
import e.android.sensmotion.entities.user.Patient;
import e.android.sensmotion.entities.user.Therapist;

public class UserController implements IUserController {

    private List<Patient> patientList = new ArrayList<>();
    private Patient patient;

    public UserController() {
    }

    public void setPatient(Patient p) {
        patient = p;
    }

    public void setPatientList(List<Patient> list) {
        patientList = list;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public Patient getPatient(final String id) {

        for (Patient p : patientList) {
            System.out.println(p.getId());
            if (p.getId().equals(id)) {
                System.out.println("id: " + p.getId());
                return p;
            }
        }

        return null;
    }


    public void savePatient(Patient p) {
        FirebaseController fbc = new FirebaseController();
        fbc.newPatient(p);
    }

    public void addSensorToPatient(List<Sensor> sl, String patientId) {
        for (Patient p : patientList) {
            if (p.getId().equals(patientId)) {
                p.setSensors(sl);
            }
        }
    }
}
