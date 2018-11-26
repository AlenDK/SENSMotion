package e.android.sensmotion.controller.interfaces;

import e.android.sensmotion.entities.Value;

public interface IDataController {

    void refreshPatient(final String PATIENT_KEY, final String PROJECT_KEY, final int DAY_COUNT);
    void serviceSuccess(Value value);
    void serviceFailure(Exception exception);

    public class LocalPatientException extends Exception{
        public LocalPatientException(String detailMessage) {
            super(detailMessage);
        }
    }
}


