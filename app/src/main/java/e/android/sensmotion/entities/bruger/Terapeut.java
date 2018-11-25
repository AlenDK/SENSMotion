package e.android.sensmotion.entities.bruger;

import java.util.List;

public class Terapeut extends Bruger {

    private List<Patient> patienter;

    public Terapeut(String brugernavn, String adgangskode, List<Patient> patienter) {
        super(brugernavn, adgangskode);
        this.patienter = patienter;
    }

    public List<Patient> getPatienter() {
        return patienter;
    }

    public void setPatienter(List<Patient> patienter) {
        this.patienter = patienter;
    }
}
