package e.android.sensmotion.DTO;

import java.util.List;

public class PatientDTO {

    String CPR;
    List<SensorDTO> Sensorer;
    String Mobilitet;
    String Projekt;
    String Navn;
    boolean isActive;


    PatientDTO(String CPR, List<SensorDTO> Sensorer, String Mobilitet, String Projekt, String Navn, boolean isActive) {
        this.CPR = CPR;
        this.Sensorer = Sensorer;
        this.Mobilitet = Mobilitet;
        this.Projekt = Projekt;
        this.isActive = isActive;
        this.Navn = Navn;
    }

    public String getCPR() {
        return CPR;
    }

    public void setCPR(String CPR) {
        this.CPR = CPR;
    }

    public List<SensorDTO> getSensorer() {
        return Sensorer;
    }

    public void setSensorer(List<SensorDTO> sensorer) {
        Sensorer = sensorer;
    }

    public String getMobilitet() {
        return Mobilitet;
    }

    public void setMobilitet(String mobilitet) {
        Mobilitet = mobilitet;
    }

    public String getProjekt() {
        return Projekt;
    }

    public void setProjekt(String projekt) {
        Projekt = projekt;
    }

    public String getNavn() {
        return Navn;
    }

    public void setNavn(String navn) {
        Navn = navn;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
