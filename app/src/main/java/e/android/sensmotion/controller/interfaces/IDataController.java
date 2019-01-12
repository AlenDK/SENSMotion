package e.android.sensmotion.controller.interfaces;

import e.android.sensmotion.entities.sensor.Sensor;
import e.android.sensmotion.entities.sensor.Period;
import e.android.sensmotion.entities.user.Patient;

public interface IDataController {
    String getDataString(Patient patient, String date);
    void saveData(String s, Sensor sensor);
    void serviceSuccess(Period period);
    void serviceFailure();
}


