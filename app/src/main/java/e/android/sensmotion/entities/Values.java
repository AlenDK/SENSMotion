package e.android.sensmotion.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Values {
    private String rest;
    private String stand;
    private String walk;
    private String exercise;
    private String other;
    private String steps;
    private String cycling;
    private String nodata;


    public String getRest() {
        return rest;
    }

    public String getStand() {
        return stand;
    }

    public String getWalk() {
        return walk;
    }

    public String getCycling() {
        return cycling;
    }

    public String getExercise() {
        return exercise;
    }

    public String getOther() {
        return other;
    }

    public String getSteps() {
        return steps;
    }


    public void populate(JSONObject data1) {

        try {

            JSONObject jsonVALUE = data1.getJSONObject("value");
            System.out.println("////////  \n"+jsonVALUE);
            JSONArray jsonDATA = jsonVALUE.getJSONArray("data");
            System.out.println("////\n"+jsonDATA);
            JSONObject jsonVALUES = (JSONObject) jsonDATA.getJSONObject(0);
            //Mangler for loop
            System.out.println("////\n"+jsonVALUES);
            JSONObject jsonVALUES1 = jsonVALUES.getJSONObject("values");
            System.out.println("////\n"+jsonVALUES1);


            rest = jsonVALUES1.getString("activity/resting/time");
            stand = jsonVALUES1.getString("activity/standing/time");
            walk = jsonVALUES1.getString("activity/walking/time");
            cycling = jsonVALUES1.getString("activity/cycling/time");
            exercise = jsonVALUES1.getString("activity/exercise/time");
            other = jsonVALUES1.getString("activity/other/time");
            nodata = jsonVALUES1.getString("general/nodata/time");
            steps = jsonVALUES1.getString("activity/steps/count");

            System.out.println("rest:");
            System.out.println(rest);
            System.out.println("stand");
            System.out.println(stand);
            System.out.println("walk:");
            System.out.println(walk);
            System.out.println("cycling:");
            System.out.println(cycling);
            System.out.println("exercise");
            System.out.println(exercise);
            System.out.println("other:");
            System.out.println(other);
            System.out.println("nodata:");
            System.out.println(nodata);
            System.out.println("steps");
            System.out.println(steps);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
