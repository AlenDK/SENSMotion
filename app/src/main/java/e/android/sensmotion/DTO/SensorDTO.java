package e.android.sensmotion.DTO;

public class SensorDTO {

    String ID;
    int Placering;
    boolean isActive;

    public SensorDTO(String ID, int Placering, boolean isActive) {
        this.ID = ID;
        this.Placering = Placering;
        this.isActive = isActive;
    }

    public String getID() {
        return ID;
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
