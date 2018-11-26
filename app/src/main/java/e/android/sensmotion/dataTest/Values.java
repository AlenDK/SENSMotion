package e.android.sensmotion.dataTest;

import org.json.JSONException;
import org.json.JSONObject;

public class Values implements JSONPopulator {
    private JSONObject rest;
    private JSONObject stand;
    private double walk;
    private JSONObject exercise;
    private String other;
    private String steps;
    private String cycling;
    private String nodata;


    public JSONObject getRest() {
        return rest;
    }

    public JSONObject getStand() {
        return stand;
    }

    public double getWalk() {
        return walk;
    }

    public String getCycling() {
        return cycling;
    }

    public JSONObject getExercise() {
        return exercise;
    }

    public String getOther() {
        return other;
    }

    public String getSteps() {
        return steps;
    }


    @Override
    public void populate(JSONObject data) {


        try {
            System.out.println(data.getString("values"));
            //JSONObject penis = data.getJSONObject("values");
            // System.out.println(penis.get("1   "+"activity\\/resting\\/time"));
            // System.out.println(penis.get("2   "+"activity/resting/time"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("////////");
        System.out.println(data);
        System.out.println("------");

            rest = data.optJSONObject("activity\\/resting\\/time");
            stand = data.optJSONObject("activity\\/standing\\/time");
            walk = data.optDouble("activity/walking/time");
            cycling = data.optString("activity/cycling/time");
            exercise = data.optJSONObject("activity/exercise/time");
        try {
            other = data.getString("activity/other/time");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        nodata = data.optString("general/nodata/time");
            steps = data.optString("activity/steps/count");


    }
}
