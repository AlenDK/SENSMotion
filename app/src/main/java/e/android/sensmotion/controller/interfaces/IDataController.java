package e.android.sensmotion.controller.interfaces;

import e.android.sensmotion.entities.Value;

public interface IDataController {
    void refreshPatient(final String PROJECT_KEY, final String PATIENT_KEY, final String DAY_COUNT);
    String getPeriode();
    void serviceSuccess(Value value);
    void serviceFailure(Exception exception);
}
