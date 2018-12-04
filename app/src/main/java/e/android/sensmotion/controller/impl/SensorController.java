package e.android.sensmotion.controller.impl;

import java.util.ArrayList;
import java.util.List;

import e.android.sensmotion.controller.interfaces.ISensorController;
import e.android.sensmotion.entities.sensor.Sensor;

public class SensorController implements ISensorController {

    private List<Sensor> sensorList = new ArrayList<>();

    public SensorController(){

        Sensor sensor1 = new Sensor("s1", 0);

        sensorList.add(sensor1);

    }

    public Sensor getSensor(String id){

        for(Sensor s : sensorList){
            if(s.getId().equals(id)){
                return s;
            }
        }
        return null;
    }



}
