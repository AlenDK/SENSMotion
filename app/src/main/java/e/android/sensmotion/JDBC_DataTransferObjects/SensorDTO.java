package e.android.sensmotion.JDBC_DataTransferObjects;

public class SensorDTO {

    String ID;
    int Patient_id;
    int Placering;
    boolean isActive;

    public SensorDTO(String ID, int Patient_id, int Placering, boolean isActive) {
        this.ID = ID;
        this.Patient_id = Patient_id;
        this.Placering = Placering;
        this.isActive = isActive;
    }

    public String getID() {
        return ID;
    }

    public int getPatient_id() {
        return Patient_id;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getPlacering() {
        return Placering;
    }

    public void setPlacering(int placering) {
        Placering = placering;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
