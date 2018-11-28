package e.android.sensmotion.controller.interfaces;

import e.android.sensmotion.entities.Values;
import e.android.sensmotion.entities.bruger.Patient;

public interface IDataController {
    void refreshPatient(Patient patient, final String DAY_COUNT);
    String getPeriode();
    void serviceSuccess(Values value);
    void serviceFailure(Exception exception);
}
