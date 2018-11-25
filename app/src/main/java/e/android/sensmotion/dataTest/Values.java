package e.android.sensmotion.dataTest;

import org.json.JSONObject;

public class Values implements JSONPopulator {
    private double rest;
    private double stand;
    private double walk;
    private double exercise;
    private double other;
    private double steps;
    //private double nodata;

    public double getRest() {
        return rest;
    }

    public double getStand() {
        return stand;
    }

    public double getWalk() {
        return walk;
    }

    public double getExercise() {
        return exercise;
    }

    public double getOther() {
        return other;
    }

    public double getSteps() {
        return steps;
    }

    @Override
    public void populate(JSONObject data) {

        rest = data.optDouble("rest");
        stand = data.optDouble("stand");
        walk = data.optDouble("walk");
        exercise = data.optDouble("exercise");
        other = data.optDouble("other");
        steps = data.optDouble("steps");

    }
}
