package e.android.sensmotion.entities.bruger;

import java.util.List;

public class Therapist extends User {

    private List<Patient> patients;

    public Therapist(String id, String username, String password, List<Patient> patients) {
        super(id, username, password);
        this.patients = patients;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public Patient getPatient(String id){
        for(Patient p : patients){
            if(p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }

}
