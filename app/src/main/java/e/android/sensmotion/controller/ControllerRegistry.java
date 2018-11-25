package e.android.sensmotion.controller;

import e.android.sensmotion.controller.impl.BrugerController;
import e.android.sensmotion.controller.impl.DataController;
import e.android.sensmotion.controller.impl.SensorController;
import e.android.sensmotion.controller.interfaces.IBrugerController;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.controller.interfaces.ISensorController;

public class ControllerRegistry {

    private static IBrugerController brugerController;
    private static IDataController dataController;
    private static ISensorController sensorController;

    protected ControllerRegistry() {
        // Needs to be here to prevent instantiation.
    }

    public static synchronized IBrugerController getBrugerController(){
        if(brugerController == null) brugerController = new BrugerController();
        return brugerController;
    }

    public static synchronized IDataController getDataController(){
        if(dataController == null) dataController = new DataController();
        return dataController;
    }

    public static synchronized ISensorController getSensorController(){
        if(sensorController == null) sensorController = new SensorController();
        return sensorController;
    }

}
