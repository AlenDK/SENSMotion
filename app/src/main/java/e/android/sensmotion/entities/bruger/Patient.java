package e.android.sensmotion.entities.bruger;

import java.util.List;

import e.android.sensmotion.entities.Sensor;

public class Patient extends Bruger {

    private String cpr, mobilitet, projekt;
    private List<Sensor> sensorer;

    public Patient(String brugernavn, String adgangskode, String cpr, List<Sensor> sensorer, String mobilitet, String projekt) {
        super(brugernavn, adgangskode);
        this.cpr = cpr;
        this.sensorer = sensorer;
        this.mobilitet = mobilitet;
        this.projekt = projekt;
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

    public String getProjekt() {
        return projekt;
    }

    public void setProjekt(String projekt) {
        this.projekt = projekt;
    }

    public List<Sensor> getSensorer() {
        return sensorer;
    }

    public void setSensorer(List<Sensor> sensorer) {
        this.sensorer = sensorer;
    }
}
