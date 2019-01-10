package e.android.sensmotion.entities.sensor;

import org.json.JSONArray;
import org.json.JSONException;
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

        ////////////////////////////////////////////////////////////////////////////////////////
        //Elendig kode!

        JSONObject jsonVALUE = null;
        int length=1;
        try {
            jsonVALUE = json.getJSONObject("value");
            JSONArray jsonDATA = jsonVALUE.getJSONArray("data");
            length = jsonDATA.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////////////////////////////

        Period period = new Period(length);
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