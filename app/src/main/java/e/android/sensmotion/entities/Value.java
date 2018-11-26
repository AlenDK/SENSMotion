package e.android.sensmotion.entities;

import org.json.JSONObject;

import e.android.sensmotion.entities.Values;

public class Value {

    private Values values;

    public Values getValues() {
        return values;
    }

    public void populate(JSONObject data) {

        values = new Values();
        values.populate(data.optJSONObject("values"));

    }
}
