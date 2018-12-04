package e.android.sensmotion.controller;

import e.android.sensmotion.controller.impl.UserController;
import e.android.sensmotion.controller.impl.DataController;
import e.android.sensmotion.controller.impl.SensorController;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.controller.interfaces.ISensorController;

public class ControllerRegistry {

    private static IUserController brugerController;
    private static IDataController dataController;
    private static ISensorController sensorController;

    public ControllerRegistry() {
        // Needs to be here to prevent instantiation.
    }

    public static IUserController getBrugerController(){
        if(brugerController == null) brugerController = new UserController();
        return brugerController;
    }

    public static IDataController getDataController(){
        if(dataController == null) dataController = new DataController();
        return dataController;
    }

    public static ISensorController getSensorController(){
        if(sensorController == null) sensorController = new SensorController();
        return sensorController;
    }

}
