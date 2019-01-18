package e.android.sensmotion.controller.interfaces;

import e.android.sensmotion.entities.user.Patient;

public interface IFirebaseController {
    void setValueListener();
    void newPatient(Patient p);
}
