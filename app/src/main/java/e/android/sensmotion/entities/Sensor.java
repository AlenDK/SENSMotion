package e.android.sensmotion.entities;

import org.json.JSONObject;

public class Sensor {

    private String id;
    private int placering;
    private Value currentValue;

    public Sensor(String id, int placering){
        this.id = id;
        this.placering = placering;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPlacering() {
        return placering;
    }

    public void setPlacering(int placering) {
        this.placering = placering;
    }

    public Value getCurrentValue() {
        return currentValue;
    }

    public void populate(JSONObject json) {
        Value value = new Value(13);
        value.populate(json);
        currentValue = value;

    }
}