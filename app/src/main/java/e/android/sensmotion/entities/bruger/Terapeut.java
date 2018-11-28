package e.android.sensmotion.entities.bruger;

import java.util.List;

public class Terapeut extends Bruger {

    private List<Patient> patienter;

    public Terapeut(String id, String brugernavn, String adgangskode, List<Patient> patienter) {
        super(id, brugernavn, adgangskode);
        this.patienter = patienter;
    }

    public List<Patient> getPatienter() {
        return patienter;
    }

    public Patient getPatient(String id){
        for(Patient p : patienter){
            if(p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }

}
