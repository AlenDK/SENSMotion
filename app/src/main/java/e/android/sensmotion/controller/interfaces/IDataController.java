package e.android.sensmotion.controller.interfaces;

import e.android.sensmotion.entities.Sensor;
import e.android.sensmotion.entities.Value;
import e.android.sensmotion.entities.Values;
import e.android.sensmotion.entities.bruger.Patient;

public interface IDataController {
    String getDataString(Patient patient);
    void saveData(String s, Sensor sensor);
    void serviceSuccess(Value value);
    void serviceFailure();
}


