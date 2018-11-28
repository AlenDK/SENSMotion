package e.android.sensmotion.entities;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Value {

    private List<Values> valuesList;
    private Values values;
    private int day_count;


    public Value(int day_count){
        this.day_count = day_count;

        valuesList = new ArrayList<Values>();
    }

    public List<Values> getValuesList() {
        return valuesList;
    }

    public void populate(JSONObject data) {

        System.out.println("data;");
        System.out.println(data);

        for(int i = 0; i<day_count; i++){
            values = new Values();
            values.populate(data, i);
            //System.out.println(values);

            valuesList.add(values);
        }
    }
}
