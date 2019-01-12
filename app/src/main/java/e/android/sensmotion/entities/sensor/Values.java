package e.android.sensmotion.entities.sensor;

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

    public Values() {

    }

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


    public void populate(JSONObject data1, int count) {

        try {

            JSONObject jsonVALUE = data1.getJSONObject("value");
            //System.out.println("////////  \n"+jsonVALUE);
            JSONArray jsonDATA = jsonVALUE.getJSONArray("data");
            //System.out.println("////\n"+jsonDATA);
            JSONObject jsonVALUES = (JSONObject) jsonDATA.getJSONObject(count);
            //System.out.println("////\n"+jsonVALUES);
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

            /*
            System.out.println("Resting time: " + rest);
            System.out.println("Standing time: " + stand);
            System.out.println("Walking time: " + walk);
            System.out.println("Cycling time: " + cycling);
            System.out.println("Exercise time: " + exercise);
            System.out.println("Other: " + other);
            */


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public String toString() {
        return "Values{" +
                "rest: "+rest+
                ", stand: "+stand+
                ", walk: "+walk+
                ", exercise: "+exercise+
                ", other: "+other+
                ", steps: "+steps+
                ", cycling: "+cycling+
                ", nodata: "+nodata+
                '}';
    }
}