package e.android.sensmotion.entities.sensor;

import org.json.JSONObject;

public class Sensor {

    private String id;
    private long placement;
    private Period currentPeriod;

    public Sensor(){

    }

    public Sensor(String id, long placement){
        this.id = id;
        this.placement = placement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPlacement() {
        return placement;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }

    public Period getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(Period currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public void populate(JSONObject json) {
        Period period = new Period(1);
        period.populate(json);
        currentPeriod = period;

    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id: "+id +
                ", placement: "+placement+
                ", currentPeriod: "+currentPeriod+
                '}';
    }
}