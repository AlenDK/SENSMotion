package e.android.sensmotion.entities.sensor;

import org.json.JSONObject;

public class Sensor {

    private String id;
    private int placement;
    private Period currentPeriod;

    public Sensor(String id, int placement){
        this.id = id;
        this.placement = placement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPlacement() {
        return placement;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }

    public Period getCurrentPeriod() {
        return currentPeriod;
    }

    public void populate(JSONObject json) {
        Period period = new Period(13);
        period.populate(json);
        currentPeriod = period;

    }
}