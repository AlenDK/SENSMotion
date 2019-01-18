package e.android.sensmotion.entities.sensor;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Period {

    private List<Values> valuesList;
    private Values values;
    private int day_count;

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

    @Override
    public String toString() {
        return "Period{"+
                "valuesList: "+valuesList+
                ", values: "+values+
                ", day_count: "+day_count+
                '}';
    }
}
