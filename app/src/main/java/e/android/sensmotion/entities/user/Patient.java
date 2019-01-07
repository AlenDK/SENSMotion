package e.android.sensmotion.entities.user;

import java.util.List;

import e.android.sensmotion.entities.sensor.Sensor;

public class Patient extends User {

    private String cpr, mobility, project_key, patient_key;
    private List<Sensor> sensors;

    public Patient(String id, String brugernavn, String adgangskode, String cpr, List<Sensor> sensors, String mobility, String project_key, String patient_key) {
        super(id, brugernavn, adgangskode);
        this.cpr = cpr;
        this.sensors = sensors;
        this.mobility = mobility;
        this.project_key = project_key;
        this.patient_key = patient_key;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getMobility() {
        return mobility;
    }

    public void setMobility(String mobility) {
        this.mobility = mobility;
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
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public Sensor getSensor(String id){

        for(Sensor s : sensors){
            if(s.getId().equals(id)){
                return s;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "cpr: "+cpr+
                ", mobility: "+mobility+
                ", project_key: "+project_key+
                ", patient_key: "+patient_key+
                ", sensors: "+sensors+
                '}';
    }
}
