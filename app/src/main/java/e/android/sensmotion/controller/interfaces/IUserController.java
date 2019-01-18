package e.android.sensmotion.controller.interfaces;

import java.util.List;

import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.user.User;
import e.android.sensmotion.entities.user.Patient;

public interface IUserController {

    List<Patient> getPatientList();
    Patient getPatient(String id);
    void setPatientList(List<Patient> list);
    void savePatient(Patient p);
    void addSensorToPatient(List<Sensor> sl, String patientId);



}
