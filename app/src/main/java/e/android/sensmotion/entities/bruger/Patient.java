package e.android.sensmotion.entities.bruger;

import java.util.List;

import e.android.sensmotion.entities.Sensor;

public class Patient extends Bruger {

    private String cpr, mobilitet, project_key, patient_key;
    private List<Sensor> sensorer;

    public Patient(String brugernavn, String adgangskode, String cpr, List<Sensor> sensorer, String mobilitet, String project_key, String patient_key) {
        super(brugernavn, adgangskode);
        this.cpr = cpr;
        this.sensorer = sensorer;
        this.mobilitet = mobilitet;
        this.project_key = project_key;
        this.patient_key = patient_key;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getMobilitet() {
        return mobilitet;
    }

    public void setMobilitet(String mobilitet) {
        this.mobilitet = mobilitet;
    }

    public String getProject_key() {
        return project_key;
    }

    public void setProject_key(String project_key) {
        this.project_key = project_key;
    }

    public String getPatient_key() {
        return patient_key;
    }

    public void setPatient_key(String patient_key) {
        this.patient_key = patient_key;
    }

    public List<Sensor> getSensorer() {
        return sensorer;
    }

    public void setSensorer(List<Sensor> sensorer) {
        this.sensorer = sensorer;
    }
}
