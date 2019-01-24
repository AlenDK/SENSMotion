package e.android.sensmotion.controller.interfaces;

import e.android.sensmotion.entities.user.Patient;

public interface IFirebaseController {
    void getPatientFirebase(String id);
    void setValueListener();
    void newPatient(Patient p);
}
