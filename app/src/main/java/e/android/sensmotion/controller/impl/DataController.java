package e.android.sensmotion.controller.impl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.entities.Sensor;
import e.android.sensmotion.entities.Period;
import e.android.sensmotion.entities.bruger.Patient;



public class DataController implements IDataController {

    private String project_key, patient_key;
    private Exception error;
    private String period_name;
    private Sensor sensor;
    private IUserController uc;

    public DataController() {

    }

    public String getDataString(Patient patient){
        project_key = patient.getProject_key();
        patient_key = patient.getPatient_key();

        String endpoint = String.format("https://beta.sens.dk/exapi/1.0/patients/data/external/overview?" +
                "project_key="+ project_key +
                "&patient_key="+ patient_key +
                "&day_count=" + 13);

        try{
            URL url = new URL(endpoint);
            URLConnection connection = url.openConnection();

            InputStream stream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder result = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null){
                result.append(line);
            }

            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveData(String s, Sensor sensor){
        if(s == null){
            serviceFailure();
            return;
        }

        try{
            JSONObject data = new JSONObject(s);

            uc = ControllerRegistry.getBrugerController();

            sensor.populate(data);

            serviceSuccess(sensor.getCurrentPeriod());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void serviceSuccess (Period period){

        System.out.println("serviceSuccess!");
    }

    @Override
    public void serviceFailure (){
        System.out.println("serviceFailure!");
    }

    public int checkPeriode (String periode){
        if (periode.length() > 2) {
            System.out.println("---\n" + periode + "\n---");
            period_name = "&date=";
            return 1;
        }
        System.out.println("---\nLængden af perioden: " + periode + " er: " + Integer.parseInt(periode) + "\n---");
        period_name = "&day_count=";
        return Integer.parseInt(periode);
    }
}

