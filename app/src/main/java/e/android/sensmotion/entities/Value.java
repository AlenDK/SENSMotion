package e.android.sensmotion.entities;

import org.json.JSONObject;

public class Value implements JSONPopulator {
    private Values values;

    public String getValues() {
        return values.toString();
    }

    @Override
    public void populate(JSONObject data) {

        System.out.println("data");
        System.out.println(data);

        values = new Values();
        values.populate(data);
        System.out.println(values);
    }
}
