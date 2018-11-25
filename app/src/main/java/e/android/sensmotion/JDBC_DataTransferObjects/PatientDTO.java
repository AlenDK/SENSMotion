package e.android.sensmotion.JDBC_DataTransferObjects;

import java.util.List;

public class PatientDTO {

    public String CPR;
    public int Id;
//    public List<SensorDTO> Sensorer;
    public int Mobilitet;
    public String Projekt;
    public String Navn;
    public String Terapeut;
    public boolean isActive;


    public PatientDTO () {

    }


    public PatientDTO(int Id, String CPR, String Projekt, String Terapeut, String Navn, int Mobilitet) {
        this.CPR = CPR;
        this.Id = Id;
  //      this.Sensorer = Sensorer;
        this.Projekt = Projekt;
        this.Terapeut = Terapeut;
        this.Navn = Navn;
        this.Mobilitet = Mobilitet;
    }

    public void setId(int Id) {Id = Id; }

    public int getId() {
        return Id;
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

 /*   public List<SensorDTO> getSensorer() {
        return Sensorer;
    }

    public void setSensorer(List<SensorDTO> sensorer) {
        Sensorer = sensorer;
    }
*/
    public int getMobilitet() {
        return Mobilitet;
    }

    public void setMobilitet(int mobilitet) {
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


}
