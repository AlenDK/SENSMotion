package e.android.sensmotion.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmData extends RealmObject {

    @PrimaryKey
    private Integer day_no;

    private String rest;
    private String stand;
    private String walk;
    private String exercise;
    private String other;
    private String steps;
    private String cycling;

    public Integer getDay_no() {
        return day_no;
    }

    public void setDay_no(Integer day_no) {
        this.day_no = day_no;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public String getStand() {
        return stand;
    }

    public void setStand(String stand) {
        this.stand = stand;
    }

    public String getWalk() {
        return walk;
    }

    public void setWalk(String walk) {
        this.walk = walk;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getCycling() {
        return cycling;
    }

    public void setCycling(String cycling) {
        this.cycling = cycling;
    }
}
