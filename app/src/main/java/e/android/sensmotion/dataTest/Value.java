package e.android.sensmotion.dataTest;

import org.json.JSONObject;

public class Value implements JSONPopulator {
    private Values values;

    public Values getValues() {
        return values;
    }

    @Override
    public void populate(JSONObject data) {

        System.out.println("data");
        System.out.println(data);

        values = new Values();
        values.populate(data);

        System.out.println("values R: " + values.getRest());
        System.out.println("values C: " + values.getCycling());
        System.out.println("values O: " + values.getOther());
        System.out.println("values S: " + values.getSteps());
        System.out.println("values E: " + values.getExercise());
        System.out.println("valuesStand: " + values.getStand());
        System.out.println("values W: " + values.getWalk());


    }
}
