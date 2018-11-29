package e.android.sensmotion.controller.interfaces;

import e.android.sensmotion.entities.Sensor;
import e.android.sensmotion.entities.Value;
import e.android.sensmotion.entities.Values;
import e.android.sensmotion.entities.bruger.Patient;

public interface IDataController {
    void refreshPatient(final Patient patient, Sensor sensor, final String DAY_COUNT);
    void serviceSuccess(Value value);
    void serviceFailure(Exception exception);
}


