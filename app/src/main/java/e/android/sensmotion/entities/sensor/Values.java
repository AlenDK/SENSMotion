package e.android.sensmotion.entities.sensor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Values {
    private String date;
    private String mobility;
    private String rest;
    private String stand;
    private String walk;
    private String exercise;
    private String other;
    private String steps;
    private String status;
    private String cycling;

    public Values() {

    }

    public Values(String date, String status, String mobility, String cycling, String exercise, String other, String rest, String stand, String steps, String walk){
        this.date     = date;
        this.mobility = mobility;
        this.cycling  = cycling;
        this.exercise = exercise;
        this.other    = other;
        this.rest     = rest;
        this.stand    = stand;
        this.steps    = steps;
        this.status   = status;
        this.walk     = walk;
    }

    public String getDate(){ return date;}

    public String getMobility(){ return mobility; }

    public String getStatus(){ return  status; }

    public String getRest() {
        return rest;
    }

    public String getStand() {
        return stand;
    }

    public String getWalk() {
        return walk;
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

    public String getCycling() {
        return cycling;
    }

    public void setMobility(String level) { mobility = level; }

    public void setStatus(String level) { status = level; }

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


            rest     = jsonVALUES1.getString("activity/resting/time");
            stand    = jsonVALUES1.getString("activity/standing/time");
            walk     = jsonVALUES1.getString("activity/walking/time");
            cycling  = jsonVALUES1.getString("activity/cycling/time");
            exercise = jsonVALUES1.getString("activity/exercise/time");
            other    = jsonVALUES1.getString("activity/other/time");
            steps    = jsonVALUES1.getString("activity/steps/count");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public Values getAPIdata(JSONObject data1){
        Values values = null;
        mobility = "0";
        status = "0";
        try {
            JSONObject jsonVALUE   = data1.getJSONObject("value");
            JSONArray jsonDATA     = jsonVALUE.getJSONArray("data");
            JSONObject jsonVALUES  = jsonDATA.getJSONObject(0);

            //Get formated date
            date = (jsonVALUES.getString("end_time")).substring(8, 10);
            date += "-"+(jsonVALUES.getString("end_time")).substring(5, 7);

            //Get Values
            JSONObject jsonVALUES1 = jsonVALUES.getJSONObject("values");

            rest     = jsonVALUES1.getString("activity/resting/time");
            stand    = jsonVALUES1.getString("activity/standing/time");
            walk     = jsonVALUES1.getString("activity/walking/time");
            cycling  = jsonVALUES1.getString("activity/cycling/time");
            exercise = jsonVALUES1.getString("activity/exercise/time");
            other    = jsonVALUES1.getString("activity/other/time");
            steps    = jsonVALUES1.getString("activity/steps/count");

            values = new Values(date, status, mobility, cycling, exercise, other, rest, stand, steps, walk);

            System.out.println("Values from API: "+values);

            return values;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String toString() {
        return "Values{" +
                "date: "+date+
                "rest: "+rest+
                ", stand: "+stand+
                ", walk: "+walk+
                ", exercise: "+exercise+
                ", other: "+other+
                ", steps: "+steps+
                ", cycling: "+cycling+
                '}';
    }
}
