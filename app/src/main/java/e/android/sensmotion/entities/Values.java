package e.android.sensmotion.entities;

import org.json.JSONObject;

public class Values {

    private double rest;
    private double stand;
    private double walk;
    private double exercise;
    private double other;
    private double steps;

    public double getRest() {
        return rest;
    }

    public void setRest(double rest) {
        this.rest = rest;
    }

    public double getStand() {
        return stand;
    }

    public void setStand(double stand) {
        this.stand = stand;
    }

    public double getWalk() {
        return walk;
    }

    public void setWalk(double walk) {
        this.walk = walk;
    }

    public double getExercise() {
        return exercise;
    }

    public void setExercise(double exercise) {
        this.exercise = exercise;
    }

    public double getOther() {
        return other;
    }

    public void setOther(double other) {
        this.other = other;
    }

    public double getSteps() {
        return steps;
    }

    public void setSteps(double steps) {
        this.steps = steps;
    }

    public void populate(JSONObject data) {

        rest = data.optDouble("rest");
        stand = data.optDouble("stand");
        walk = data.optDouble("walk");
        exercise = data.optDouble("exercise");
        other = data.optDouble("other");
        steps = data.optDouble("steps");

    }
}
