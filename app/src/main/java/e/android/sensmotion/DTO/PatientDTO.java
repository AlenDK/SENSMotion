package e.android.sensmotion.DTO;

import java.util.List;

public class PatientDTO {

    public String CPR;
    public List<SensorDTO> Sensorer;
    public String Mobilitet;
    public String Projekt;
    public String Navn;
    public String Terapeut;
    public boolean isActive;


    public PatientDTO(String CPR, List<SensorDTO> Sensorer, String Mobilitet, String Projekt, String Terapeut, String Navn, boolean isActive) {
        this.CPR = CPR;
        this.Sensorer = Sensorer;
        this.Mobilitet = Mobilitet;
        this.Projekt = Projekt;
        this.isActive = isActive;
        this.Terapeut = Terapeut;
        this.Navn = Navn;
    }

    public String getTerapeut() {
        return Terapeut;
    }

    public void setTerapeut(String terapeut) {
        Terapeut = terapeut;
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
