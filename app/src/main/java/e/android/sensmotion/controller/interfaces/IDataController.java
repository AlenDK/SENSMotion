package e.android.sensmotion.controller.interfaces;

public interface IDataController {
    void refreshPatient(final String PROJECT_KEY, final String PATIENT_KEY, final String DAY_COUNT);
    String getPeriode();
}
