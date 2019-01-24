package e.android.sensmotion.entities.sensor;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Period {

    private List<Values> valuesList;
    private Values values;
    private int day_count;
    private String startingDate;

    public Period(){

    }

    public Period(int day_count){
        this.day_count = day_count;

        valuesList = new ArrayList<Values>();
    }



    public List<Values> getValuesList() { return valuesList; }

    public void populate(JSONObject data) {

        System.out.println("data: "+data);
        for(int i = 0; i<day_count; i++){
            values = new Values();
            values.populate(data, i);
            valuesList.add(values);
        }
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    @Override
    public String toString() {
        return "Period{"+
                "valuesList: "+valuesList+
                ", values: "+values+
                ", day_count: "+day_count+
                '}';
    }
}
