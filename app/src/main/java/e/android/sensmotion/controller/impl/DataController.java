package e.android.sensmotion.controller.impl;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IBrugerController;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.entities.Sensor;
import e.android.sensmotion.entities.Value;
import e.android.sensmotion.entities.Values;
import e.android.sensmotion.entities.bruger.Patient;



public class DataController implements IDataController {

    private String project_key, patient_key;
    private Exception error;
    private String period_name;
    private Sensor sensor;
    private IBrugerController bc;

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

            System.out.println("----");
            System.out.println(data.toString(2));
            System.out.println("----");

            bc = ControllerRegistry.getBrugerController();

            sensor.populate(data);

            System.out.println("////////////////////////");
            System.out.println(sensor.getCurrentValue().toString());

            serviceSuccess(sensor.getCurrentValue());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }





    @SuppressLint("StaticFieldLeak")
    public void refreshPatient(final Patient patient, Sensor sensor, final String DAY_COUNT) {

        this.sensor = sensor;

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {

                project_key = patient.getProject_key();
                patient_key = patient.getPatient_key();


                checkPeriode(DAY_COUNT);
                String endpoint = String.format("https://beta.sens.dk/exapi/1.0/patients/data/external/overview?" +
                        "project_key="+ project_key +
                        "&patient_key="+ patient_key +
                        period_name + DAY_COUNT);

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

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if(s == null && error != null){
                    serviceFailure();
                    return;
                }

                try{
                    JSONObject data = new JSONObject(s);

                    System.out.println("----");
                    System.out.println(data.toString(2));
                    System.out.println("----");

                    Value value = new Value(checkPeriode(DAY_COUNT));
                    value.populate(data);



                    serviceSuccess(value);

                } catch (JSONException e) {
                    serviceFailure();
                }
            }

        }.execute(patient_key);


    }




    @Override
    public void serviceSuccess (Value value){

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

