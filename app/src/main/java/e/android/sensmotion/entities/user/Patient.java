package e.android.sensmotion.entities.user;

import java.util.List;

import e.android.sensmotion.entities.sensor.Sensor;

public class Patient extends User {

    private String name, cpr, mobility, project_key, patient_key;
    private List<Sensor> sensors;

    public Patient() {

    }

    public Patient(String id, String name, String username, String password, String cpr, List<Sensor> sensors, String mobility, String project_key, String patient_key) {
        super(id, username, password);
        this.name = name;
        this.cpr = cpr;
        this.sensors = sensors;
        this.mobility = mobility;
        this.project_key = project_key;
        this.patient_key = patient_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
