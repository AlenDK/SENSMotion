package e.android.sensmotion.dataTest;

import org.json.JSONObject;

public class Value implements JSONPopulator {
    private Values values;

    public Values getValues() {
        return values;
    }

    @Override
    public void populate(JSONObject data) {

        values = new Values();
        values.populate(data.optJSONObject("values"));

    }
}
