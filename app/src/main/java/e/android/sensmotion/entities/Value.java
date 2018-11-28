package e.android.sensmotion.entities;

import org.json.JSONObject;

public class Value {

    private Values values;

    public String getValues() {
        return values.toString();
    }

    public void populate(JSONObject data) {

        System.out.println("data;");
        System.out.println(data);

        values = new Values();
        values.populate(data.optJSONObject("values"));
        System.out.println(values);

    }
}
