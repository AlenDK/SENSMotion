package e.android.sensmotion.entities;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.DynamicRealmObject;
import io.realm.Realm;
import io.realm.RealmResults;

public class Value {

    private List<Values> valuesList;
    private Realm realm;
    private Values values;
    private int day_count;


    public Value(int day_count){
        this.day_count = day_count;

        valuesList = new ArrayList<Values>();
    }

    public List<Values> getValuesList() { return valuesList; }

    public void populate(JSONObject data) {

        System.out.println("data;");
        System.out.println(data);

        for(int i = 0; i<day_count; i++){
            values = new Values();
            values.populate(data, i);
            //System.out.println(values);

            realm.beginTransaction();
            RealmData rd = realm.createObject(RealmData.class, i);
            rd.setDay_no(i);
            rd.setCycling(values.getCycling());
            rd.setExercise(values.getExercise());
            rd.setRest(values.getRest());
            rd.setOther(values.getOther());
            rd.setStand(values.getStand());
            rd.setSteps(values.getSteps());
            rd.setWalk(values.getWalk());
            realm.commitTransaction();

            //valuesList.add(values);
        }
    }
}
